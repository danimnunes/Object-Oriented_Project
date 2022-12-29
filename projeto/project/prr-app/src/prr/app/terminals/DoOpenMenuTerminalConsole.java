package prr.app.terminals;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import prr.app.terminal.Menu;

/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {

	DoOpenMenuTerminalConsole(Network receiver) {
		super(Label.OPEN_MENU_TERMINAL, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		String terminalKey = Form.requestString(Prompt.terminalKey());
		/*Verify if the terminal exists. */
		if (!(_receiver.searchTerminalNetwork(terminalKey)))
			throw new UnknownTerminalKeyException(terminalKey);
		(new prr.app.terminal.Menu(_receiver, _receiver.getTerminalNetwork(terminalKey))).open();
	}
}
