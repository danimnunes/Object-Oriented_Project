package prr.app.main;

import java.util.Collection;

import prr.Network;
import prr.Clients.Client;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show global balance.
 */
class DoShowGlobalBalance extends Command<Network> {

	DoShowGlobalBalance(Network receiver) {
		super(Label.SHOW_GLOBAL_BALANCE, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		
		_display.popup(Message.globalPaymentsAndDebts(_receiver.getAllPayments(), _receiver.getAllDebts()));
	}
}
