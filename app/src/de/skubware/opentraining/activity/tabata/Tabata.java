package de.skubware.opentraining.activity.tabata;

import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import de.skubware.opentraining.R;

/**
 * Created by ildarworld on 09/11/2016.
 */

public class Tabata  implements Serializable{

    private TabataItem tPrepare;
    private TabataItem tWork;
    private TabataItem tRest;
    private TabataItem tRounds;
    private TabataItem tCycles;
    private TabataItem tRestBCycles;

    private ArrayList<TabataItem> tabataItemList = new ArrayList<TabataItem>();

    private TextView timerView;
    private TextView curActionView;
    private TextView nextActView;

    public TextView getNextActView() {
        return nextActView;
    }

    public void setNextActView(TextView nextActView) {
        this.nextActView = nextActView;
    }

    public TextView getCurActionView() {
        return curActionView;
    }

    public void setCurActionView(TextView curActionView) {
        this.curActionView = curActionView;
    }

    public TextView getTimerView() {
        return timerView;
    }

    public void setTimerView(TextView timerView) {
        this.timerView = timerView;
    }

    Tabata(){

        tPrepare = new TabataItem("Prepare", 3, true, 3, 59);
        tWork = new TabataItem("Work", 10, true, 1, 3659);
        tRest = new TabataItem("Rest", 3, true, 1, 3659);
        tRounds = new TabataItem("Rounds", 1, false, 1, 90);
        tCycles = new TabataItem("Cycles", 1, false, 1, 90);
        tRestBCycles = new TabataItem("Rest between cycles", 0, true, 0, 3659);

        tabataItemList.add(tPrepare);       //0
        tabataItemList.add(tWork);          //1
        tabataItemList.add(tRest);          //2
        tabataItemList.add(tRounds);        //3
        tabataItemList.add(tCycles);        //4
        tabataItemList.add(tRestBCycles);   //5

    }

    public ArrayList<TabataItem> getTabataItemList() {
        return tabataItemList;
    }



}
