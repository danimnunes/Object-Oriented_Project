package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for showing the ongoing communication.
 */
class DoShowOngoingCommunication extends TerminalCommand {

	
	DoShowOngoingCommunication(Network context, Terminal terminal) {
		super(Label.SHOW_ONGOING_COMMUNICATION, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
		if (_receiver.getOngoingCommunication() == null) {
			_display.popup(Message.noOngoingCommunication());
			
		}
		else {
			_display.addLine(_receiver.getOngoingCommunication());
			_display.display();
		}
	}
}



