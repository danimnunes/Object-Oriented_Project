package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

/**
 * Silence the terminal.
 */
class DoSilenceTerminal extends TerminalCommand {


	DoSilenceTerminal(Network context, Terminal terminal) {
		super(Label.MUTE_TERMINAL, context, terminal);

	}

	@Override
	protected final void execute() throws CommandException {
			if (String.valueOf(_receiver.getTerminalState()).equals("SILENCE"))
				_display.popup(Message.alreadySilent());
			else if (String.valueOf(_receiver.getTerminalState()).equals("OFF")) {
				_receiver.OffToSilenceTerminal();
			}
			else if (String.valueOf(_receiver.getTerminalState()).equals("IDLE"))
				_receiver.IdleToSilenceTerminal();
	}
}



