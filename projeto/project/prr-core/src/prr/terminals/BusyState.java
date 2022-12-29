package prr.terminals;

public class BusyState extends TerminalState {

    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
    
    public BusyState (Terminal terminal) {
        super(terminal);
    }

    @Override
    public String toString(){
        return "BUSY";
    }
}
