package prr.app.lookups;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;


/**
 * Show communications to a client.
 */
class DoShowCommunicationsToClient extends Command<Network> {

	DoShowCommunicationsToClient(Network receiver) {
		super(Label.SHOW_COMMUNICATIONS_TO_CLIENT, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		String clientkey = Form.requestString(Prompt.clientKey());
		if (!_receiver.searchClient(clientkey))
			throw new UnknownClientKeyException(clientkey);
		_display.addAll(_receiver.getCommunicationsToClient(clientkey));
		_display.display();
	}
}
