package prr.Communications;

import prr.Clients.Client;

public class NotificationType_1 extends Notification {
    
    public NotificationType_1(Client client, String senderTerminalID) {
        super(client, senderTerminalID);
    }

    public boolean equals(Notification notification) {
        return (this.getNotificationType() == notification.getNotificationType() &&
                this.getSenderTerminalID().equals(notification.getSenderTerminalID()));
    }


    @Override
    public boolean equals(Object o) {
        Notification notification =(Notification) o;
        return this.getNotificationType() == notification.getNotificationType() &&
                this.getSenderTerminalID().equals(notification.getSenderTerminalID());
    }


    @Override
    public String toString() {
        int _notificationType = this.getNotificationType();
        String _senderTerminalID = this.getSenderTerminalID();
        switch(_notificationType) {
            case 1 : return "O2S" + "|" + _senderTerminalID;
            case 2 : return "O2I" + "|" + _senderTerminalID;
            case 3 : return "B2I" + "|" + _senderTerminalID;
            case 4 : return "S2I" + "|" + _senderTerminalID;
            default : return "";
        }
    }
}
