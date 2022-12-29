package prr.Clients;

public class PlatinumState extends ClientState {

    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
    
    public PlatinumState (Client client) {
        super(client);
    }

    public int calculatePrice (int communicationDuration, int communicationType, TariffPlan plan) {
        if (communicationType == 1)
            return communicationDuration*plan.getPlatinumStateVideoPrice();
        if (communicationType == 2)
            return communicationDuration*plan.getPlatinumStateVoicePrice();
        return 0;
    }

    public int calculateTextPrice (int communicationSize, int communicationType, TariffPlan plan) {
        if (communicationType == 3)
            if(communicationSize >= 100)
            return plan.getPlatinumStateBigTextPrice();
            else if(communicationSize >= 50)
            return plan.getPlatinumStateMediumTextPrice();
            else
            return plan.getPlatinumStateSmallTextPrice();
        return 0;
    }

    @Override
    public String toString(){
        return "PLATINUM";
    }
}