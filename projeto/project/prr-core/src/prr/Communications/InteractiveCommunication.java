package prr.Communications;

public class InteractiveCommunication extends Communication{
    
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    public InteractiveCommunication(int communicationID, String senderID,
    String receiverID, int communicationType) {
        super(communicationType, communicationID, senderID, receiverID);
    }
}
