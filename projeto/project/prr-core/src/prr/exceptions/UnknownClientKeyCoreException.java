package prr.exceptions;


/**
 * Exception for unknown clients.
 */
public class UnknownClientKeyCoreException extends Exception {

	/** Serial number (serialization) */
	private static final long serialVersionUID = 202208091753L;

	/** @param key Unknown client to report. */
	public UnknownClientKeyCoreException(String key) {
		super("empty message");
	}

}
