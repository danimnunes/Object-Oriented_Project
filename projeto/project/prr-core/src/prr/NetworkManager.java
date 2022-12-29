package prr;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import java.lang.ClassNotFoundException;

import prr.exceptions.ImportFileException;
import prr.exceptions.MissingFileAssociationException;
import prr.exceptions.UnavailableFileException;
import prr.exceptions.UnknownClientKeyCoreException;
import prr.exceptions.UnknownTerminalKeyCoreException;
import prr.exceptions.UnrecognizedEntryException;
import prr.exceptions.DuplicateClientKeyCoreException;
import prr.exceptions.InvalidTerminalKeyCoreException;





/**
 * Manage access to network and implement load/save operations.
 */
public class NetworkManager {

	/** The network itself. */

	private String _filename = "";

	private Network _network = new Network();

    public Network getNetwork() {
		return _network;
	}

	/**
	 * @return changed?
	 */
	public boolean changed() {
		return _network.hasChanged();
	}


  /**
   * @param filename
   * @throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException {
    try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
      _network = (Network) ois.readObject();
      _network.setChanged(false);
	  _filename = filename;
    } catch (IOException | ClassNotFoundException e) {
		throw new UnavailableFileException(filename);
	}
  }


	/**
         * Saves the serialized application's state into the file associated to
		 *  the current network.
         *
	 * @throws FileNotFoundException if for some reason the file cannot be 
	 * created or opened. 
	 * @throws MissingFileAssociationException if the current network does not
	 *  have a file.
	 * @throws IOException if there is some error while serializing the state of
	 *  the network to disk.
	 */
	public void save() throws FileNotFoundException, MissingFileAssociationException {
		if (!_network.hasChanged()) {
			return;
		}
		if (_filename == null || _filename.equals("")) {
			throw new MissingFileAssociationException();
		}
	  	try {
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)));
			oos.writeObject(_network);
			oos.close();
			_network.setChanged(false);
	  	}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
         * Saves the serialized application's state into the specified file. 
		 * The current network is
         * associated to this file.
         *
	 * @param filename the name of the file.
	 * @throws FileNotFoundException if for some reason the file cannot be 
	 * created or opened.
	 * @throws MissingFileAssociationException if the current network does not 
	 * have a file.
	 * @throws IOException if there is some error while serializing the state 
	 * of the network to disk.
	 */
	public void saveAs(String filename) throws FileNotFoundException, 
	 MissingFileAssociationException, IOException {
		_filename = filename;
		save();
	}

	/**
	 * Read text input file and create domain entities..
	 * 
	 * @param filename name of the text input file
	 * @throws ImportFileException
	 */
	public void importFile(String filename) throws ImportFileException{
		try {
			_network.importFile(filename);
		} catch(UnrecognizedEntryException | IOException e) {
			throw new ImportFileException(filename);
		} catch (DuplicateClientKeyCoreException | UnknownClientKeyCoreException
		 | InvalidTerminalKeyCoreException | UnknownTerminalKeyCoreException e) {
			throw new ImportFileException(filename);
		}
	}

}
