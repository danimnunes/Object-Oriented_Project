package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.forms.Form;
import prr.exceptions.UnknownTerminalKeyCoreException;

import prr.app.exceptions.UnknownTerminalKeyException;

/**
 * Add a friend.
 */
class DoAddFriend extends TerminalCommand {


	DoAddFriend(Network context, Terminal terminal) {
		super(Label.ADD_FRIEND, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {

		String[] fields = new String[3];
		fields[0] = "FRIENDS";
		fields[1] = _receiver.getTerminalID();
		fields[2] = Form.requestString(Prompt.terminalKey());
		try {
			if(_receiver.searchTerminal(_network, fields[2])) {
				_receiver.AddFriendToTerminal(_network, fields);
			}
			else
				throw new UnknownTerminalKeyException(fields[2]);
		
		} catch (UnknownTerminalKeyCoreException e) {
			e.printStackTrace();
		}
	}
}
