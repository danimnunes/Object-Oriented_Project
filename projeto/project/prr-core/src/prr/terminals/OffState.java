package prr.terminals;

public class OffState extends TerminalState {

    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
    
    public OffState (Terminal terminal) {
        super(terminal);
    }

    @Override
    public String toString(){
        return "OFF";
    }
}