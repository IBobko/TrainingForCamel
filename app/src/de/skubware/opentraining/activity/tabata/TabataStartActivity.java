package de.skubware.opentraining.activity.tabata;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import de.skubware.opentraining.R;

public class TabataStartActivity extends ActionBarActivity {

    private Tabata tabata;
    private TextView roundsLeftValue;
    private TextView cyclesLeftValue;
    private TextView timerView;

    private Timer timer = new Timer();
    private TimerTask timerTask;
    private Handler mHandler = new Handler();
    private int currentPosition = 0;
    private int currentTime = 0;
    private int currentRaund = 0;
    private int currentCycle = 0;
    private int timeRemain = 0;
    private boolean worK = true;
    private boolean pause = false;
    private TabataItem currentTabata;
    private Button startButton;
    private Button resetButton;
    private TextView curActionView;
    private TextView nextActView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabata_start);
        Intent i = getIntent();
        tabata = (Tabata) i.getSerializableExtra("Tabata");
        timerView = (TextView) findViewById(R.id.tvTime);
        nextActView = (TextView) findViewById(R.id.nextAction);
        curActionView = (TextView) findViewById(R.id.curAction);
        cyclesLeftValue  = (TextView) findViewById(R.id.cyclesLeftValue);
        roundsLeftValue  = (TextView) findViewById(R.id.roundsLeftValue);

        createButtons();
        tabata.setTimerView(timerView);
        tabata.setNextActView(nextActView);
        tabata.setCurActionView(curActionView);
        currentTabata = tabata.getTabataItemList().get(0);
        initData();
        startTabata();
    }

    private void initData(){
        currentPosition = 0;
        currentTime = 0;
        currentRaund = 0;
        currentCycle = 0;
        timeRemain = 0;
        worK = true;
        pause = false;
        nextActView.setText(getNextaction());
        curActionView.setText(tabata.getTabataItemList().get(currentPosition).getDiscription());
        cyclesLeftValue.setText(Integer.toString(getCyclesRemain()));
        roundsLeftValue.setText(Integer.toString(getRoundRemain()));
    }

    private void createButtons(){
        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause = !pause;

                if (pause){
                    startButton.setBackgroundResource(R.drawable.icon_start_button);
                    resetButton.setVisibility(View.VISIBLE);
                }else {
                    startButton.setBackgroundResource(R.drawable.icon_pause_button);
                    resetButton.setVisibility(View.GONE);
                }
            }
        });

        resetButton = (Button)findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

    private void startTabata() {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            private long startTime = System.currentTimeMillis();

            public void run() {

                //TabataItem item = getPosition();

                while (worK) {
                    try {
                        Thread.sleep(1000);


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        public void run() {

                            if (!pause) {
                                int time = currentTabata.getValue();
                                timeRemain = time - currentTime;

                                if (timeRemain > 0) {
                                    currentTime++;
                                    timerView.setText(Integer.toString(timeRemain));

                                } else {
                                    nextAction();
                                    timerView.setText(Integer.toString(0));
                                }
                                String nextAction = getNextaction();
                                nextActView.setText(nextAction);
                                if (nextAction.equals(R.string.doneText)) {
                                    curActionView.setText(nextAction);
                                }else {
                                    curActionView.setText(tabata.getTabataItemList().get(currentPosition).getDiscription());
                                }
                            }
                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }

    private void nextAction() {

        switch (currentPosition) {
            case 0:
                currentPosition++;
                break;
            case 1:
                currentPosition++;
                break;
            case 2:
                if (getRoundRemain() != 0) {
                    currentRaund++;
                    currentPosition = 1;//Work
                    cyclesLeftValue.setText(Integer.toString(getCyclesRemain()));
                    roundsLeftValue.setText(Integer.toString(getRoundRemain()));

                } else if (getCyclesRemain() != 0) {
                    currentPosition = 5;
                    currentRaund = 0;
                }
                break;
            case 5:
                currentPosition = 1;
                currentCycle++;
                break;
        }
        currentTime = 0;
        currentTabata = tabata.getTabataItemList().get(currentPosition);
    }

    private int getRoundRemain() {
        return tabata.getTabataItemList().get(3).getValue() - currentRaund;
    }

    private int getCyclesRemain() {
        return tabata.getTabataItemList().get(4).getValue() - currentCycle;
    }


    private String getNextaction() {

        String result = getString(R.string.nextActionText);
        if (currentPosition < 2) {
            result += tabata.getTabataItemList().get(currentPosition + 1).getDiscription();
        } else if (getRoundRemain() != 0 || getCyclesRemain() != 0) {
            result += tabata.getTabataItemList().get(1).getDiscription();
        } else {
            result = getString(R.string.doneText);
            worK = false;
        }
        return result;

    }




}
