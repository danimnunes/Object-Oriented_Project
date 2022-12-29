package prr.app.lookups;


import java.util.Collections;

import prr.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show unused terminals (without communications).
 */
class DoShowUnusedTerminals extends Command<Network> {

	DoShowUnusedTerminals(Network receiver) {
		super(Label.SHOW_UNUSED_TERMINALS, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
        _display.addAll(_receiver.getAllUnusedTerminals());
		_display.display();
	}
}




