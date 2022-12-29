package prr.app.clients;

import prr.Network;
import prr.app.exceptions.DuplicateClientKeyException;
import prr.exceptions.DuplicateClientKeyCoreException;
import prr.exceptions.UnrecognizedEntryException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import prr.exceptions.DuplicateClientKeyCoreException;


/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

	DoRegisterClient(Network receiver) {
		super(Label.REGISTER_CLIENT, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
	
		/*Put the arguments recieved according with RegisterClient method. */
		String[] fields = new String[4];
		fields[0] = "CLIENT";
		fields[1] = Form.requestString(Prompt.key());
		fields[2] = Form.requestString(Prompt.name());
		int taxID = Form.requestInteger(Prompt.taxId());
		String client_taxID = String.valueOf(taxID);
		fields[3] = client_taxID;
		try {
			if (_receiver.searchClient(fields[1]))
				throw new DuplicateClientKeyException(fields[1]);
			else
 				_receiver.RegisterClient(fields);
		} catch (UnrecognizedEntryException e) {
			e.printStackTrace();
		} catch (DuplicateClientKeyCoreException e) {
			throw new DuplicateClientKeyException(fields[1]);
		}
	}
}
