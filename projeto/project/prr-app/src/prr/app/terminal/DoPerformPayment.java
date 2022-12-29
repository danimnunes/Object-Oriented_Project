package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {


	DoPerformPayment(Network context, Terminal terminal) {
		super(Label.PERFORM_PAYMENT, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
        int communicationID = Form.requestInteger(Prompt.commKey());
		
		if(!_receiver.searchTerminalMadeCommunication(_network, communicationID) || 
			_receiver.getStatus(_network, communicationID) || _receiver.alreadyPaid(_network, communicationID)) 
			_display.popup(Message.invalidCommunication());
		else
			_receiver.payCommunication(_network, communicationID);
	}
}
