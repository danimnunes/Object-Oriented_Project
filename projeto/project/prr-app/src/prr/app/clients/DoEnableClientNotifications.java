package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

/**
 * Enable client notifications.
 */
class DoEnableClientNotifications extends Command<Network> {

	DoEnableClientNotifications(Network receiver) {
		super(Label.ENABLE_CLIENT_NOTIFICATIONS, receiver);
		//FIXME add command fields
	}

	@Override
	protected final void execute() throws CommandException {
		String clientKey = Form.requestString(Prompt.key());
		/*Check if client already exists. */
		if (!_receiver.searchClient(clientKey))
			throw new UnknownClientKeyException(clientKey);
		/*Check if client notifications were already enabled. */
		if (_receiver.CanReceiveNotifications(_receiver.getClient(clientKey)) == true)
			_display.popup(Message.clientNotificationsAlreadyEnabled());
		/*If notifications were off, enable them. */
		if (_receiver.CanReceiveNotifications(_receiver.getClient(clientKey)) == false)
			_receiver.setClientNotification(_receiver.getClient(clientKey), true);
	}
}
