package prr.terminals;

public class BasicTerminal extends Terminal{

    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    public BasicTerminal(boolean canVideoCommunicate, String terminalID, String clientID,
     String terminalStatus) {
        super(canVideoCommunicate, terminalID, clientID, terminalStatus);
    }
    
}
