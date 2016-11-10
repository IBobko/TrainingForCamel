package de.skubware.opentraining.activity.tabata;

/**
 * Created by ildarworld on 10/11/2016.
 */

public class TabataItem {
    private String discription;
    private int value;
    private boolean isTime = false;

    public TabataItem(String discription, int value, boolean isTime) {
        this.discription = discription;
        this.value = value;
        this.isTime = isTime;
    }

    public TabataItem(String discription, int value) {
        this.discription = discription;
        this.value = value;

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
            return ":" + Integer.toString( value);
        } else {
            return Integer.toString(value);
        }

    }

    public String getDiscription() {
        return discription;
    }
}
