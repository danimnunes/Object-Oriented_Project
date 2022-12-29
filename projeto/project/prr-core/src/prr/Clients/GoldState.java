package prr.Clients;

public class GoldState extends ClientState {

    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
    
    public GoldState (Client client) {
        super(client);
    }

    public int calculatePrice (int communicationDuration, int communicationType, TariffPlan plan) {
        if (communicationType == 1)
            return communicationDuration*plan.getGoldStateVideoPrice();
        if (communicationType == 2)
            return communicationDuration*plan.getGoldStateVoicePrice();
        return 0;
    }

    public int calculateTextPrice (int communicationSize, int communicationType, TariffPlan plan) {
        if (communicationType == 3)
            if(communicationSize >= 100)
            return communicationSize*plan.getGoldStateBigTextMultiplier();
            else if(communicationSize >= 50)
            return plan.getGoldStateMediumTextPrice();
            else
            return plan.getGoldStateSmallTextPrice();
        return 0;
    }

    @Override
    public String toString(){
        return "GOLD";
    }
}