package prr.terminals;

public class SilenceState extends TerminalState {

    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
    
    public SilenceState (Terminal terminal) {
        super(terminal);
    }

    @Override
    public String toString(){
        return "SILENCE";
    }
}
