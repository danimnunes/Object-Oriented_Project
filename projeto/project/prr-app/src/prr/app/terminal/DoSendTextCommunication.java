package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.app.exceptions.UnknownTerminalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {


        DoSendTextCommunication(Network context, Terminal terminal) {
                super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());

        }

        @Override
        protected final void execute() throws CommandException {
                String terminalID = Form.requestString(Prompt.terminalKey());
                String textMessage = Form.requestString(Prompt.textMessage());

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
                                default -> _receiver.SendTextCommunication(_network, destinationTerminal, textMessage);
                        }
                }
        }
} 
