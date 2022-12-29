package prr.terminals;

public class FancyTerminal extends Terminal{

    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    public FancyTerminal(Boolean canVideoCommunicate, String terminalID, String clientID,
     String terminalStatus) {
        super(canVideoCommunicate, terminalID, clientID, terminalStatus);
    }
    
}
