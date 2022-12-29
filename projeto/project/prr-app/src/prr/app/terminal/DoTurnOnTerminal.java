package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.forms.Form;

/**
 * Turn on the terminal.
 */
class DoTurnOnTerminal extends TerminalCommand {

	DoTurnOnTerminal(Network context, Terminal terminal) {
		super(Label.POWER_ON, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
		
		if (String.valueOf(_receiver.getTerminalState()).equals("IDLE"))
			_display.popup(Message.alreadyOn());
		else if (String.valueOf(_receiver.getTerminalState()).equals("OFF"))
			_receiver.OffToIdleTerminal();
		else if (String.valueOf(_receiver.getTerminalState()).equals("SILENCE"))
			_receiver.SilenceToIdleTerminal();
	}
}
