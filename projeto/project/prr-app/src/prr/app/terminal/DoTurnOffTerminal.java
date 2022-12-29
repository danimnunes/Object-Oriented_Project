package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;


/**
 * Turn off the terminal.
 */
class DoTurnOffTerminal extends TerminalCommand {


	DoTurnOffTerminal(Network context, Terminal terminal) {
		super(Label.POWER_OFF, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
		if (String.valueOf(_receiver.getTerminalState()).equals("OFF"))
			_display.popup(Message.alreadyOff());
		else if (String.valueOf(_receiver.getTerminalState()).equals("IDLE"))
			_receiver.IdleToOffTerminal();
		else if (String.valueOf(_receiver.getTerminalState()).equals("SILENCE"))
			_receiver.SilenceToOffTerminal();
	}
}
