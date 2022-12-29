package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.forms.Form;

import prr.app.exceptions.UnknownTerminalKeyException;

/**
 * Remove friend.
 */
class DoRemoveFriend extends TerminalCommand {


	DoRemoveFriend(Network context, Terminal terminal) {
		super(Label.REMOVE_FRIEND, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
		String[] fields = new String[2];
		fields[1] = Form.requestString(Prompt.terminalKey());
		String friendTerminalKey = String.valueOf(fields[1]);
		if(_receiver.searchTerminal(_network, friendTerminalKey)) {
			Terminal terminalFriend = _receiver.getTerminalFriend(friendTerminalKey);
			_receiver.removeFriend(friendTerminalKey,terminalFriend);
		}
		else
			throw new UnknownTerminalKeyException(friendTerminalKey);
	}
}





