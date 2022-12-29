package prr.terminals;

public class IdleState extends TerminalState {

    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
    
    public IdleState (Terminal terminal) {
        super(terminal);
    }

    @Override
    public String toString(){
        return "IDLE";
    }
}