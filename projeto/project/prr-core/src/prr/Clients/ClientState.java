package prr.Clients;

import java.io.Serializable;

public abstract class ClientState implements Serializable{

    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    protected Client client;

    public ClientState(Client client) {
        this.client = client;
    }

    public abstract int calculatePrice(int communicationDuration, int communicationType, TariffPlan plan);
    public abstract int calculateTextPrice(int communicationSize, int communicationType, TariffPlan plan);
}
