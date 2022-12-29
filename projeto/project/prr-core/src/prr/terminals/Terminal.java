package prr.terminals;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Collection;

import prr.Network;
import prr.Communications.Communication;
import prr.Clients.Client;
import prr.Communications.Notification;
import prr.Communications.NotificationType_1;
import prr.exceptions.UnknownTerminalKeyCoreException;


/**
 * Abstract terminal.
 */
public abstract class Terminal implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

        /*true if terminal can do video communications. */
        private boolean _canVideoCommunicate;

        /*Terminal ID. */
        private String _terminalID;

        /*Terminal status. */
        private TerminalState _terminalStatus;

        /*Terminal previous status. */
        private TerminalState _previouStatus;

        /*Client ID associated to terminal. */
        private String _clientID;

        /*Terminal payments balance. */
        private long _balancePaid = 0;

        /*Terminal debts balance. */
        private long _balanceDebts = 0;

        /*Terminal's friend Map. */
	private Map<String, Terminal> _terminalFriends = new TreeMap<String, Terminal>(String.CASE_INSENSITIVE_ORDER);

        /*Terminal's iniciated communication Map. */
        private Map<Integer, Communication> _terminalMadeCommunications = new TreeMap<Integer, Communication>();

        /*Terminal's received communication Map. */
        private Map<Integer, Communication> _terminalReceivedCommunications = new TreeMap<Integer, Communication>();

        /*Terminal notification list. */
        private List<Notification> _terminalNotifications = new ArrayList<Notification>();


        public Terminal(boolean canVideoCommunicate, String terminalID,
        String clientID, String terminalStatus) {
                _canVideoCommunicate = canVideoCommunicate;
                _terminalID = terminalID;
                _clientID = clientID;
                switch (terminalStatus) {
			case "OFF" -> _terminalStatus = new OffState(this);
			case "BUSY" -> _terminalStatus = new BusyState(this);
			case "SILENCE" -> _terminalStatus = new SilenceState(this);
			default -> _terminalStatus = new IdleState(this);
		}
                        
        }

        public String getClientID() {
                return this._clientID;
        }

        public String getTerminalID() {
                return this._terminalID;
        }

        public Collection<Communication> getTerminalMadeCommunications() {
                return this._terminalMadeCommunications.values();
        }

        public Collection<Communication> getTerminalReceivedCommunications() {
                return this._terminalReceivedCommunications.values();
        }



	public boolean searchMadeCommunication(int key) {
		return _terminalMadeCommunications.containsKey(key);

	}

        public long getBalance() {
                return this._balancePaid - this._balanceDebts;
        }

        public long getPayments() {
                return this._balancePaid;
        }

        public long getDebts() {
                return this._balanceDebts;
        }

        public Terminal getTerminalFriend(String terminalKey) {
		return _terminalFriends.get(terminalKey);
	}

        public Communication getCommunication(int communicationNumber) {
                if (_terminalMadeCommunications.containsKey(communicationNumber))
                        return _terminalMadeCommunications.get(communicationNumber);
                if (_terminalReceivedCommunications.containsKey(communicationNumber))
                        return _terminalReceivedCommunications.get(communicationNumber);
                return null;
        }

        public Communication getOngoingCommunication() {
                for (Communication communication : this._terminalMadeCommunications.values())
                        if (communication.getCommunicationStatus() == true)
                                return communication;
                return null;
        }

        public boolean isFinished(int communicationNumber) {
                if(getCommunication(communicationNumber).getCommunicationStatus()) 
                        return false;
                return true;
                
        }

        public Communication getTextCommunication() {
                for (Communication communication : this._terminalMadeCommunications.values())
                        if (communication.getCommunicationStatus() == false)
                                return communication;
                return null;
        }

        public TerminalState getTerminalState() {
                return this._terminalStatus;
        }

        public TerminalState getPreviouState() {
                return this._previouStatus;
        }

        public boolean canVideoCommunicate() {
                return this._canVideoCommunicate;
        }

        public void setTerminalState(TerminalState state) {
                this._terminalStatus = state;
        }

        public void setPreviousState(TerminalState state) {
                this._previouStatus = state;
        }

        public void AddToReceivedCommunicationsTerminal(Communication communication, int communicationNumber) {
                _terminalReceivedCommunications.put(communicationNumber, communication);
        }

        public void AddToMadeCommunicationsTerminal(Communication communication, int communicationNumber) {
                _terminalMadeCommunications.put(communicationNumber, communication);
        }

        public void addFriend(String terminalFriendID, Terminal terminal) {
                if(!(this._terminalFriends.containsKey(terminalFriendID)) && this._terminalID != terminalFriendID) {
                        this._terminalFriends.put(terminalFriendID, terminal);
                }
        }

        public void removeFriend(String terminalFriendID, Terminal terminal) {
                if(this._terminalFriends.containsKey(terminalFriendID) && this._terminalID != terminalFriendID) {
                        this._terminalFriends.remove(terminalFriendID, terminal);
                }
        }

        public boolean hasDiscount(Terminal receiverTerminal) {
                if (this._terminalFriends.containsKey(receiverTerminal.getTerminalID()))
                        return true;
                else
                        return false;
        }

        public void addToTerminalDebts(long debt) {
                _balanceDebts += debt;
        }

        public void removeFromTerminalDebts(long debt) {
                _balanceDebts -= debt;
        }

        public void addToTerminalPayments(long debt) {
                _balancePaid += debt;
        }

        public boolean searchTerminal(Network network, String terminalID) {
                return network.searchTerminalNetwork(terminalID);
        }

        public void AddFriendToTerminal(Network network, String[] fields) throws UnknownTerminalKeyCoreException {
                network.AddFriendToTerminalNetwork(fields);
        }

        public long EndInteractiveCommunication(Network network, int communicationTime) {
                return network.EndInteractiveCommunicationNetwork(this, communicationTime);
        }

        public boolean searchTerminalMadeCommunication(Network network, int communicationID) {
                return network.searchTerminalMadeCommunicationNetwork(this, communicationID);
        }

        public boolean getStatus(Network network, int communicationNumber) {
		return network.getStatusNetwork(this, communicationNumber);
	}

        public boolean alreadyPaid(Network network, int communicationNumber) {
		return network.alreadyPaidNetwork(this, communicationNumber);
	}

        public void payCommunication(Network network, int communicationNumber) {
		network.payCommunicationNetwork(this, communicationNumber);
	}

        public void SendTextCommunication(Network network, Terminal destinationTerminal, String textMessage) {
                network.SendTextCommunicationNetwork(this, destinationTerminal, textMessage);
        }

        public void StartInteractiveCommunication(Network network, Terminal receiverTerminal, String communicationType) {
                network.StartInteractiveCommunicationNetwork(this, receiverTerminal, communicationType);
        }


        public Terminal getTerminal(Network network, String terminalKey) {
		return network.getTerminalNetwork(terminalKey);
	}

        public boolean CanReceiveNotifications(Network network) {
                Client client = network.getClient(this.getClientID());
                return client.ClientNotifications();
        }

        public void OffToSilenceTerminal() {
                this._terminalStatus = new SilenceState(this);
                Iterator<Notification> iterate = _terminalNotifications.iterator();
                while (iterate.hasNext()) {
                        Notification notification = iterate.next();
                        Client client = notification.getClient();
                        notification.setNotifcationType(1);
                        if (!client.searchNotification(notification))
                                client.addNotificationToClient(notification);
                        iterate.remove();
                }
        }

        public void IdleToSilenceTerminal() {
                this._terminalStatus = new SilenceState(this);
        }

        public void BusyToSilenceTerminal() {
                this._terminalStatus = new SilenceState(this);
        }

        public void IdleToOffTerminal() {
                this._terminalStatus = new OffState(this);
        }

        public void SilenceToOffTerminal() {
                this._terminalStatus = new OffState(this);
        }

        public void OffToIdleTerminal() {
                this._terminalStatus = new IdleState(this);
                Iterator<Notification> iterate = _terminalNotifications.iterator();
                while (iterate.hasNext()) {
                        Notification notification = iterate.next();
                        Client client = notification.getClient();
                        notification.setNotifcationType(2);
                        if (!client.searchNotification(notification))
                                client.addNotificationToClient(notification);
                        iterate.remove();
                }
        }

        public void SilenceToIdleTerminal() {
                this._terminalStatus = new IdleState(this);
                Iterator<Notification> iterate = _terminalNotifications.iterator();
                while (iterate.hasNext()) {
                        Notification notification = iterate.next();
                        Client client = notification.getClient();
                        notification.setNotifcationType(4);
                        if (!client.searchNotification(notification))
                                client.addNotificationToClient(notification);
                        iterate.remove();
                }
        }

        public void BusyToIdleTerminal() {
                this._terminalStatus = new IdleState(this);
                Iterator<Notification> iterate = _terminalNotifications.iterator();
                while (iterate.hasNext()) {
                        Notification notification = iterate.next();
                        Client client = notification.getClient();
                        notification.setNotifcationType(3);
                        if (!client.searchNotification(notification))
                                client.addNotificationToClient(notification);
                        iterate.remove();
                }
        }


        public void newNotification(Network network, String clientID, String senderTerminalID) {
                Client client = network.getClient(clientID);
                Notification notification = new NotificationType_1(client, senderTerminalID);
                _terminalNotifications.add(notification);
        }
        


        public String toString() {
                return (this._canVideoCommunicate == true ? "FANCY" : "BASIC") + "|" + this._terminalID
                + "|" + this._clientID + "|" + String.valueOf(this._terminalStatus) + "|" + this._balancePaid
                + "|" + this._balanceDebts + (this._terminalFriends.isEmpty() ? "" : "|"
                + this._terminalFriends.keySet().toString().replace("[","").replace("]",""));
        }




        /**
         * Checks if this terminal can end the current interactive communication.
         *
         * @return true if this terminal is busy (i.e., it has an active interactive communication) and
         *          it was the originator of this communication.
         **/
        public boolean canEndCurrentCommunication() {
                if (!String.valueOf(_terminalStatus).equals("BUSY"))
                        return false;
                for (Communication communication : _terminalMadeCommunications.values()) {
                        if (communication.getCommunicationStatus() == true)
                                        return true;
                        }
                return false;
        }

        /**
         * Checks if this terminal can start a new communication.
         *
         * @return true if this terminal is neither off neither busy, false otherwise.
         **/
        public boolean canStartCommunication() {
                if (String.valueOf(_terminalStatus).equals("OFF") || String.valueOf(_terminalStatus).equals("BUSY"))
                        return false;
                else
                        return true;
        }
}
