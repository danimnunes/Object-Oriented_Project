package prr.Clients;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import prr.terminals.Terminal;
import prr.Communications.Notification;
import prr.Communications.Communication;


public class Client implements Serializable, Comparable<Client> {
    
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    /*Client's Key */
    private String _clientKey;

    /*Client's name*/
    private String _clientName;

    /*Client's NIF*/
    private String _clienttaxID;

    /*Client's state*/
    private ClientState _clientType;

    /*Client's notifications*/
    private boolean _canReceiveNotifications;

    /*Client's number of associated terminals*/
    private int _clientNumberTerminals = 0;

    /*Client's payments*/
    private long _clientPayments = 0;

    /*Client's debts*/
    private long _clientDebts = 0;

    /*Number of consecutive video communications. */
    private int _consecutiveVideoCommunications = 0;

    /*Number of consecutive text communications. */
    private int _consecutiveTextCommunications = 0;

    /*Client's tariff plan. */
    TariffPlan _clientPlan = new Plan_1();

    /*Client's terminals*/
    private Map<String,Terminal> _clientTerminals = new TreeMap<String, Terminal>();

    /*List of Client's notifications */
    private List<Notification> _clientNotifications = new ArrayList<Notification>();


    public Client(String clientKey, String clientName, String clienttaxID) {
        
        _clientKey = clientKey;
        _clientName = clientName;
        _clienttaxID = clienttaxID;
        _clientType = new NormalState(this);
        _canReceiveNotifications = true;
    }

    public int getClientNumberTerminals() {
        return this._clientNumberTerminals;
    }

    public TariffPlan getTariffPlan() {
        return this._clientPlan;
    }

    public String getClientID() {
        return this._clientKey;
    }
  
    public boolean ClientNotifications() {
        return this._canReceiveNotifications;
    }

    public long getPayments() {
        return this._clientPayments;
    }

    public long getDebts() {
        return this._clientDebts;
    }

    public List<Notification> getNotifications() {
        return this._clientNotifications;
    }

    public Collection<Terminal> getClientTerminals() {
        return this._clientTerminals.values();
    }

    public ClientState getClientType() {
        return this._clientType;
    }

    public boolean searchNotification(Notification notification) {
        return _clientNotifications.contains(notification);
    }

    public int getNumberOfConsecutiveVideoCommunications() {
        return this._consecutiveVideoCommunications;
    }

    public int getNumberOfConsecutiveTextCommunications() {
        return this._consecutiveTextCommunications;
    }

    public void setClientNumberTerminals(int number) {
        _clientNumberTerminals = number;
    }

    public void setClientType(ClientState state) {
        this._clientType = state;
    }

    public void setNotification(boolean clientNotifications) {
        _canReceiveNotifications = clientNotifications;
    }

    public void setNumberOfConsecutiveVideoCommunications(int number) {
        this._consecutiveVideoCommunications = number;
    }

    public void setNumberOfConsecutiveTextCommunications(int number) {
        this._consecutiveTextCommunications = number;
    }

    public void addTerminalToClient(String terminalId, Terminal terminal) {
        _clientTerminals.put(terminalId, terminal);
    }

    public void addNotificationToClient(Notification notification) {
        _clientNotifications.add(notification);
    }

    public void addToDebts(long debt) {
        _clientDebts += debt;
    }

    public void removeDebts(long debt) {
        _clientDebts -= debt;
    }

    public void addToPayments(long debt) {
        _clientPayments += debt;
    }

    public int CalculateCommunicationPrice (int communicationDuration, int communicationType, TariffPlan plan) {
        int price = _clientType.calculatePrice(communicationDuration, communicationType, plan);
        return price;
    }

    public int CalculateTextCommunicationPrice (int communicationSize, int communicationType, TariffPlan plan) {
        int price = _clientType.calculateTextPrice(communicationSize, communicationType, plan);
        return price;
    }

    public void ClearNotifications() {
        this._clientNotifications.clear();
    }

    @Override
    public int compareTo(Client client) {
        return Long.compare(client._clientDebts, this._clientDebts);
    }

    public String toString() {
        return "CLIENT|" + this._clientKey + "|" + this._clientName + "|" +
        this._clienttaxID + "|" + this._clientType + "|" + 
        (this._canReceiveNotifications == true ? "YES" : "NO") + "|" + 
        (this._clientNumberTerminals == 0 ? "0|0|0" : this._clientNumberTerminals
        + "|" + this._clientPayments + "|" + this._clientDebts);
    }
}