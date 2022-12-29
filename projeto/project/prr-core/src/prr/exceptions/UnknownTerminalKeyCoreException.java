

package prr.exceptions;


/** Exception thrown when a terminal key is unknown. */
public class UnknownTerminalKeyCoreException extends Exception {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private final String _key;

	/** @param key the duplicated key */
	public UnknownTerminalKeyCoreException(String key) {
		_key = key;

	}

	/** @return the key */
	public String getKey() {
		return _key;
	}
}