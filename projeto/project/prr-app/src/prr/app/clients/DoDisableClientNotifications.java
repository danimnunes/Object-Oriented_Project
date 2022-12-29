package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

/**
 * Disable client notifications.
 */
class DoDisableClientNotifications extends Command<Network> {

	DoDisableClientNotifications(Network receiver) {
		super(Label.DISABLE_CLIENT_NOTIFICATIONS, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		String clientKey = Form.requestString(Prompt.key());
		/*Check if client already exists. */
		if (!_receiver.searchClient(clientKey))
			throw new UnknownClientKeyException(clientKey);
		/*Check if client notifications were already disabled. */
		if (_receiver.CanReceiveNotifications(_receiver.getClient(clientKey)) == false)
			_display.popup(Message.clientNotificationsAlreadyDisabled());
		/*If notifications were on, disable them. */
		if (_receiver.CanReceiveNotifications(_receiver.getClient(clientKey)) == true)
			_receiver.setClientNotification(_receiver.getClient(clientKey), false);
	}
}
