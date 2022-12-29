package prr.Clients;

import java.io.Serializable;


public abstract class TariffPlan implements Serializable {

    /*Voice communication prices. */
    private int _normalStateVoicePrice;
    private int _goldStateVoicePrice;
    private int _platinumStateVoicePrice;

    /*Video communication prices. */
    private int _normalStateVideoPrice;
    private int _goldStateVideoPrice;
    private int _platinumStateVideoPrice;

    /*Small text communication prices. */
    private int _normalStateSmallTextPrice;
    private int _goldStateSmallTextPrice;
    private int _platinumStateSmallTextPrice;
    
    /*Medium text communication prices. */
    private int _normalStateMediumTextPrice;
    private int _goldStateMediumTextPrice;
    private int _platinumStateMediumTextPrice;

    /*Big text communication prices. */
    private int _normalStateBigTextMultiplier;
    private int _goldStateBigTextMultiplier;
    private int _platinumStateBigTextPrice;
    

    public TariffPlan(int normalVoicePrice,int goldVoicePrice,int platinumVoicePrice,
    int normalVideoPrice,int goldVideoPrice,int platinumVideoPrice,int normalSmallTextPrice,
    int goldSmallTextPrice,int platinumSmallTextPrice,int normalMediumTextPrice,
    int goldMediumTextPrice,int platinumMediumTextPrice,int normalBigTextMultiplier,
    int goldBigTextMultiplier,int platinumBigTextPrice) {
        _normalStateVoicePrice = normalVoicePrice;
        _goldStateVoicePrice = goldVoicePrice;
        _platinumStateVoicePrice = platinumVoicePrice;

        _normalStateVideoPrice = normalVideoPrice;
        _goldStateVideoPrice = goldVideoPrice;
        _platinumStateVideoPrice = platinumVideoPrice;

        _normalStateSmallTextPrice = normalSmallTextPrice;
        _goldStateSmallTextPrice = goldSmallTextPrice;
        _platinumStateSmallTextPrice = platinumSmallTextPrice;

        _normalStateMediumTextPrice = normalMediumTextPrice;
        _goldStateMediumTextPrice = goldMediumTextPrice;
        _platinumStateMediumTextPrice = platinumMediumTextPrice;

        _normalStateBigTextMultiplier = normalBigTextMultiplier;
        _goldStateBigTextMultiplier = goldBigTextMultiplier;
        _platinumStateBigTextPrice = platinumBigTextPrice;
    }

    public int getNormalStateVoicePrice() {
        return this._normalStateVoicePrice;
    }

    public int getGoldStateVoicePrice() {
        return this._goldStateVoicePrice;
    }

    public int getPlatinumStateVoicePrice() {
        return this._platinumStateVoicePrice;
    }


    public int getNormalStateVideoPrice() {
        return this._normalStateVideoPrice;
    }

    public int getGoldStateVideoPrice() {
        return this._goldStateVideoPrice;
    }

    public int getPlatinumStateVideoPrice() {
        return this._platinumStateVideoPrice;
    }


    public int getNormalStateSmallTextPrice() {
        return this._normalStateSmallTextPrice;
    }

    public int getGoldStateSmallTextPrice() {
        return this._goldStateSmallTextPrice;
    }

    public int getPlatinumStateSmallTextPrice() {
        return this._platinumStateSmallTextPrice;
    }


    public int getNormalStateMediumTextPrice() {
        return this._normalStateMediumTextPrice;
    }

    public int getGoldStateMediumTextPrice() {
        return this._goldStateMediumTextPrice;
    }

    public int getPlatinumStateMediumTextPrice() {
        return this._platinumStateMediumTextPrice;
    }


    public int getNormalStateBigTextMultiplier() {
        return this._normalStateBigTextMultiplier;
    }

    public int getGoldStateBigTextMultiplier() {
        return this._goldStateBigTextMultiplier;
    }

    public int getPlatinumStateBigTextPrice() {
        return this._platinumStateBigTextPrice;
    }
}