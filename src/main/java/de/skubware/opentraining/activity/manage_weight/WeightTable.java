package de.skubware.opentraining.activity.manage_weight;

import java.util.Date;

/**
 * Created by k.akhiyarov on 11/16/2016.
 */

public class WeightTable {

    private int ID;
    private float WEIGHT;
    private Date DATE;

    public WeightTable(int ID, float WEIGHT, Date DATE) {
        this.ID = ID;
        this.WEIGHT = WEIGHT;
        this.DATE = DATE;
    }


    public int getID() {
        return ID;
    }

    public float getWEIGHT() {
        return WEIGHT;
    }

    public Date getDATE() {
        return DATE;
    }


}
