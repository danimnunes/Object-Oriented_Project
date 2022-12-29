package prr.app.terminal;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {


	DoStartInteractiveCommunication(Network context, Terminal terminal) {
		super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
	}

	@Override
	protected final void execute() throws CommandException {
        String terminalID = Form.requestString(Prompt.terminalKey());
		String communicationTypeString = Form.requestOption(Prompt.commType(), "VOICE", "VIDEO");

		if(!_receiver.searchTerminal(_network, terminalID))
			throw new UnknownTerminalKeyException(terminalID);

		Terminal destinationTerminal = _receiver.getTerminal(_network, terminalID);
		/*The destination terminal must be different from the origin terminal. */
		if (!_receiver.getTerminalID().equals(terminalID)) {
			switch(destinationTerminal.getTerminalState().toString()) { 
				case "OFF" -> {
					_display.popup(Message.destinationIsOff(terminalID));
					if (_receiver.CanReceiveNotifications(_network))
						destinationTerminal.newNotification(_network, _receiver.getClientID(), terminalID);
				}
				case "BUSY" -> {
					_display.popup(Message.destinationIsBusy(terminalID));
					if (_receiver.CanReceiveNotifications(_network))
						destinationTerminal.newNotification(_network, _receiver.getClientID(), terminalID);
				}
				case "SILENCE" -> {
					_display.popup(Message.destinationIsSilent(terminalID));
					if (_receiver.CanReceiveNotifications(_network))
						destinationTerminal.newNotification(_network, _receiver.getClientID(), terminalID);
				}
				default -> {
				/*Verify that the destination terminal suports the type of communication. */
				if (destinationTerminal.canVideoCommunicate() == false && communicationTypeString.equals("VIDEO"))
					_display.popup(Message.unsupportedAtDestination(terminalID, communicationTypeString));
				/*Verify that the origin terminal suports the type of communication. */
				else if (_receiver.canVideoCommunicate() == false && communicationTypeString.equals("VIDEO"))
					_display.popup(Message.unsupportedAtOrigin(_receiver.getTerminalID(), communicationTypeString));
				else {
					/*Start the communication. */
					
					_receiver.StartInteractiveCommunication(_network, destinationTerminal, communicationTypeString);
				}
				}
			}
		}
	}
}
