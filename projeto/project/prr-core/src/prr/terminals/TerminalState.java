package prr.terminals;

import java.io.Serializable;

public abstract class TerminalState implements Serializable{

    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    protected Terminal terminal;

    public TerminalState(Terminal terminal) {
        this.terminal = terminal;
    }
}
