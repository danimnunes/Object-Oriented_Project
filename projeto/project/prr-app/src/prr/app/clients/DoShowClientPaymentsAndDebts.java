package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

/**
 * Show the payments and debts of a client.
 */
class DoShowClientPaymentsAndDebts extends Command<Network> {

	DoShowClientPaymentsAndDebts(Network receiver) {
		super(Label.SHOW_CLIENT_BALANCE, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		String clientKey = Form.requestString(Prompt.key());
		if (!_receiver.searchClient(clientKey))
			throw new UnknownClientKeyException(clientKey);
		long payments = _receiver.getClientPayments(clientKey);
		long debts = _receiver.getClientDebts(clientKey);
		_display.popup(Message.clientPaymentsAndDebts(clientKey, payments, debts));
	}
}
