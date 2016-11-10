package de.skubware.opentraining.activity.tabata;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ildarworld on 09/11/2016.
 */

public class Tabata {


    //    private int prepare = 5;
//    private int work = 40;
//    private int rest = 40;
//    private int rounds = 5;
//    private int cycles = 4;
//    private int restBCycles = 0;
    ArrayList<TabataItem> tabataItemList = new ArrayList<TabataItem>();

    Tabata(){
        TabataItem item = new TabataItem("Prepare", 5, true);
        TabataItem item1 = new TabataItem("Work", 40, true);
        TabataItem item2 = new TabataItem("Rest", 5, true);
        TabataItem item3 = new TabataItem("Rounds", 4);
        TabataItem item4 = new TabataItem("Cycles", 3);
        TabataItem item5 = new TabataItem("Rest between cycles", 0, true);

        tabataItemList.add(item);
        tabataItemList.add(item1);
        tabataItemList.add(item2);
        tabataItemList.add(item3);
        tabataItemList.add(item4);
        tabataItemList.add(item5);

    }

    public ArrayList<TabataItem> getTabataItemList() {
        return tabataItemList;
    }
//    public Tabata(int prepare, int work, int rest, int rounds, int cycles, int restBCycles) {
//        this.prepare = prepare;
//        this.work = work;
//        this.rest = rest;
//        this.rounds = rounds;
//        this.cycles = cycles;
//        this.restBCycles = restBCycles;
//    }
//
//
//    public void setPrepare(int prepare) {
//        this.prepare = prepare;
//    }
//
//    public void setWork(int work) {
//        this.work = work;
//    }
//
//    public void setRest(int rest) {
//        this.rest = rest;
//    }
//
//    public void setRounds(int rounds) {
//        this.rounds = rounds;
//    }
//
//    public void setCucles(int cucles) {
//        this.cycles = cucles;
//    }
//
//    public int getPrepare() {
//
//        return prepare;
//    }
//
//    public int getWork() {
//        return work;
//    }
//
//    public int getRest() {
//        return rest;
//    }
//
//    public int getRounds() {
//        return rounds;
//    }
//
//    public int getCycles() {
//        return cycles;
//    }
//
//    public int getRestBCycles() {
//        return restBCycles;
//    }
//
//    public String[] getTabataElements(){
//        String[] names = {"Prepare", "Work", "Rest", "Rounds", "Cycles", "Rest between cycles"};
//        return names;
//    }
}
