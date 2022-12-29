package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {

	DoShowClient(Network receiver) {
		super(Label.SHOW_CLIENT, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		String clientkey = Form.requestString(Prompt.key());
		if (_receiver.searchClient(clientkey)) {
			_display.addLine(_receiver.getClient(clientkey));
			_display.addAll(_receiver.getClientNotifications(_receiver.getClient(clientkey)));
			_display.display();
			_receiver.clearClientNotifications(_receiver.getClient(clientkey));
		}
		else
			throw new UnknownClientKeyException(clientkey);

	}
}
