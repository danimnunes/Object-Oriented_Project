package prr.app.main;

import prr.NetworkManager;
import prr.app.exceptions.FileOpenFailedException;
import prr.exceptions.DuplicateClientKeyCoreException;
import prr.exceptions.UnavailableFileException;
import prr.exceptions.DuplicateClientKeyCoreException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.lang.ClassNotFoundException;


/**
 * Command to open a file.
 */
class DoOpenFile extends Command<NetworkManager> {

        DoOpenFile(NetworkManager receiver) {
                super(Label.OPEN_FILE, receiver);
        }

        @Override
        protected final void execute() throws CommandException {
                try {
			_receiver.load(Form.requestString(Prompt.openFile()));
                } catch (UnavailableFileException e) {
                        throw new FileOpenFailedException(e);    
                }        
        }
}
