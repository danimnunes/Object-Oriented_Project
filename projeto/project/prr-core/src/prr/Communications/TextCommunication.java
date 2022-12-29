package prr.Communications;

public class TextCommunication extends Communication {

    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    public TextCommunication(int communicationID, String senderID, String receiverID) {
        super(communicationID, senderID, receiverID);
    }
    
}
