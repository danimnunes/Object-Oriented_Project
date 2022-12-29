package prr.Communications;

import java.io.Serializable;
import prr.terminals.Terminal;
import prr.Clients.Client;

public abstract class Notification implements Serializable {

    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    /*0-no type defined yet, 1-O2S (off-to-silent), 2-O2I (off-to-idle), 3-B2I (busy-to-idle) or 4-S2I (silent-to-idle). */
    private int _notificationType = 0;

    /*Id of terminal that sends the notification. */
    private String _senderTerminalID;

    /*Client that receives the notification. */
    private Client _client;

    public Notification(Client client, String senderTerminalID) {
        _client = client;
        _senderTerminalID = senderTerminalID;
    }

    public Client getClient() {
        return this._client;
    }

    public int getNotificationType() {
        return this._notificationType;
    }

    public String getSenderTerminalID() {
        return this._senderTerminalID;
    }

    public void setNotifcationType(int type) {
        _notificationType = type;
    }
}