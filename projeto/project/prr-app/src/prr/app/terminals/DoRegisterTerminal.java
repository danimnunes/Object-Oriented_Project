package prr.app.terminals;

import prr.Network;
import prr.app.exceptions.DuplicateTerminalKeyException;
import prr.app.exceptions.InvalidTerminalKeyException;
import prr.app.exceptions.UnknownClientKeyException;
import prr.exceptions.UnknownClientKeyCoreException;
import prr.exceptions.UnrecognizedEntryException;
import prr.exceptions.InvalidTerminalKeyCoreException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

	DoRegisterTerminal(Network receiver) {
		super(Label.REGISTER_TERMINAL, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		Form input = new Form();
		/*Ask user for input. */
		String key = Form.requestString(Prompt.terminalKey());
		String terminalType = Form.requestOption(Prompt.terminalType(), "BASIC", "FANCY");
		String ClientKey = Form.requestString(Prompt.clientKey());
		
		/*Put the arguments recieved according with RegisterTerminal method. */
		String[] fields = new String[4];
		fields[0] = terminalType;
		fields[1] = key;
		fields[2] = ClientKey;
		fields[3] = "ON";
		try {
			/*Verify input fields */
			if (!fields[1].matches("(\\d{6})"))
				throw new InvalidTerminalKeyException(fields[1]);
			else if (_receiver.searchTerminalNetwork(fields[1]))
				throw new DuplicateTerminalKeyException(fields[1]);
			else if (!(_receiver.searchClient(fields[2])))
				throw new UnknownClientKeyException(fields[2]);
			else
				_receiver.RegisterTerminal(fields);
		} catch (UnrecognizedEntryException e) {
			e.printStackTrace();
		} catch (UnknownClientKeyCoreException e) {
			e.printStackTrace();
		} catch (InvalidTerminalKeyCoreException e) {
			e.printStackTrace();
		}
	}
}
