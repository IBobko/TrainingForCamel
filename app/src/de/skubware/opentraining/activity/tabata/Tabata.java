package de.skubware.opentraining.activity.tabata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ildarworld on 09/11/2016.
 */

public class Tabata {

    private ArrayList<TabataItem> tabataItemList = new ArrayList<TabataItem>();

    Tabata(){
        TabataItem item = new TabataItem("Prepare", 5, true, 0, 59);
        TabataItem item1 = new TabataItem("Work", 40, true, 0, 3659);
        TabataItem item2 = new TabataItem("Rest", 5, true, 0, 3659);
        TabataItem item3 = new TabataItem("Rounds", 4, false, 1, 90);
        TabataItem item4 = new TabataItem("Cycles", 3, false, 1, 90);
        TabataItem item5 = new TabataItem("Rest between cycles", 0, true, 0, 3659);

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

}
