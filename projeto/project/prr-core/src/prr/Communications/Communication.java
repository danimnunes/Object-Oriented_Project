package prr.Communications;

import java.io.Serializable;

public abstract class Communication implements Serializable, Comparable<Communication> {

        /** Serial number for serialization. */
	    private static final long serialVersionUID = 202208091753L;

        /*Terminal ID. */
        private int _communicationID;

        /*Communication's Sender ID*/
        private String _senderID;
    
        /*Communication's Receiver ID*/
        private String _receiverID;
    
        /*Communication's Status (ongoing or finished)*/
        private boolean _activecommunication;

        /*true if the communication is already paid. */
        private boolean _communicationPaid;
    
        /*Communication's number of Units*/
        private int _communicationUnits = 0;

        /*Communication's Price*/
        private long _communicationPrice = 0;

        /*0-no type defined yet, 1-VIDEO, 2-VOICE, 3-TEXT */
        private int _communicationType = 0;
    
        public Communication(int communicationID, String senderID, String receiverID) {
            _communicationID = communicationID;
            _senderID = senderID;
            _receiverID = receiverID;
        }

        public Communication(int communicationType, int communicationID, String senderID, String receiverID) {
            _communicationID = communicationID;
            _senderID = senderID;
            _receiverID = receiverID;
            _communicationType = communicationType;
        }
    
        public int getID() {
            return this._communicationID;
        }
    
        public String getsenderID() {
            return this._senderID;
        }
    
        public String getreceiverID() {
            return this._receiverID;
        }
    
        public int getType() {
            return this._communicationType;
        }
    
        public boolean getCommunicationStatus() {
            return this._activecommunication;
        }

        public boolean getCommunicationPaidStatus() {
            return this._communicationPaid;
        }

        public int getUnits() {
            return this._communicationUnits;
        }
    
        public long getPrice() {
            return this._communicationPrice;
        }

        public void setStatus(boolean status) {
            this._activecommunication = status;
        }

        public void setPaidStatus(boolean status) {
            this._communicationPaid = status;
        }

        public void setPrice(long price) {
            _communicationPrice = price;
        }

        public void setUnits(int units) {
            _communicationUnits = units;
        }

        public void setType(int communicationType) {
            _communicationType = communicationType;
        }

        @Override
        public int compareTo(Communication c) {
            return (this._communicationID - c._communicationID);
        }

    
        public String toString() {
            switch(_communicationType) {
                case 1 : return "VIDEO" + "|" + this._communicationID + "|" + this._senderID + "|" + 
                this._receiverID + "|" + this._communicationUnits + "|" + 
                Math.round(this._communicationPrice) + "|" + 
                (this._activecommunication == true ? "ONGOING" : "FINISHED");

                case 2 : return "VOICE" + "|" + this._communicationID + "|" + this._senderID + "|" + 
                this._receiverID + "|" + this._communicationUnits + "|" + 
                Math.round(this._communicationPrice) + "|" + 
                (this._activecommunication == true ? "ONGOING" : "FINISHED");

                case 3 : return "TEXT" + "|" + this._communicationID + "|" + this._senderID + "|" + 
                this._receiverID + "|" + this._communicationUnits + "|" + 
                Math.round(this._communicationPrice) + "|" + 
                (this._activecommunication == true ? "ONGOING" : "FINISHED");
                
                default : return "" ; 
            }
        }
    
}
