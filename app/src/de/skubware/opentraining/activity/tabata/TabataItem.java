package de.skubware.opentraining.activity.tabata;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by ildarworld on 10/11/2016.
 */

public class TabataItem implements Serializable {


    public String getDiscription() {
        return discription;
    }

    private String discription;
    private int value;
    private boolean isTime = false;
    private int minValue;
    private int maxValue;

    public TabataItem(String discription, int value, boolean isTime, int minValue, int maxValue) {
        this.discription = discription;
        this.value = value;
        this.isTime = isTime;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }


    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isTime() {
        return isTime;
    }

    public String getStringValue() {

        if (isTime) {
            if(value >60){
                return Integer.toString(value/60) + ":" + Integer.toString(value%60);
            }else{
                return ":" + Integer.toString(value);
            }
        } else {
            return Integer.toString(value);
        }

    }

}
