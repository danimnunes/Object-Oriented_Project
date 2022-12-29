package prr.Clients;

public class NormalState extends ClientState {

    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
    
    public NormalState (Client client) {
        super(client);
    }

    public int calculatePrice (int communicationDuration, int communicationType, TariffPlan plan) {
        if (communicationType == 1)
            return communicationDuration*plan.getNormalStateVideoPrice();
        if (communicationType == 2)
            return communicationDuration*plan.getNormalStateVoicePrice();
        return 0;
    }

    public int calculateTextPrice (int communicationSize, int communicationType, TariffPlan plan) {
        if (communicationType == 3)
            if(communicationSize >= 100)
            return communicationSize*plan.getNormalStateBigTextMultiplier();
            else if(communicationSize >= 50)
            return plan.getNormalStateMediumTextPrice();
            else
            return plan.getNormalStateSmallTextPrice();
        return 0;
    }

    @Override
    public String toString(){
        return "NORMAL";
    }
}