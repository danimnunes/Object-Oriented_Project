package prr.app.lookups;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

/**
 * Show communications from a client.
 */
class DoShowCommunicationsFromClient extends Command<Network> {

	DoShowCommunicationsFromClient(Network receiver) {
		super(Label.SHOW_COMMUNICATIONS_FROM_CLIENT, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		String clientkey = Form.requestString(Prompt.clientKey());
		if (!_receiver.searchClient(clientkey))
			throw new UnknownClientKeyException(clientkey);
		_display.addAll(_receiver.getCommunicationsFromClient(clientkey));
		_display.display();
	}
}
