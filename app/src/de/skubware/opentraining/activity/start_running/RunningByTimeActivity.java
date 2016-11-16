/**
 * 
 * This is OpenTraining, an Android application for planning your your fitness training.
 * Copyright (C) 2012 Christian Skubich
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package de.skubware.opentraining.activity.start_running;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import de.skubware.opentraining.R;


/**
 * An activity representing running menu.
 */
public class RunningByTimeActivity extends ActionBarActivity {
	NumberPicker numberPicker;
	boolean isTimerStarted=false;
	CountDownTimer countDownTimer;
	GpsTracker gps;
	Button btnStart;
	String mode;
	Double lastLatitude = 0.0;
	Double lastLongitude = 0.0;
	Timer myTimer = new Timer();
	final Handler uiHandler = new Handler();
	Double newLatitude;
	Double newLongitude;
	Integer lastDistance=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_run_by_time);

		// Show the Up button in the action bar.
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
		numberPicker.setWrapSelectorWheel(false);
		Intent intent = getIntent();
		mode = intent.getStringExtra("mode");
		if (mode.equals("time")){
			numberPicker.setMaxValue(30);
			numberPicker.setMinValue(0);
			numberPicker.setValue(15);
		}
		else if ((mode.equals("distance"))){
			numberPicker.setMaxValue(10000);
			numberPicker.setMinValue(0);
			numberPicker.setValue(3000);
			gps = new GpsTracker(this);

		}
		btnStart = (Button) findViewById(R.id.btnStart);
		btnStart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (isTimerStarted){
					if (mode.equals("time")) {
						countDownTimer.cancel();
					}
					else if (mode.equals("distance")){
						gps.stopUsingGPS();
					}
					isTimerStarted=false;
					btnStart.setText("START");
					numberPicker.setEnabled(true);
				}
				else {
					if (mode.equals("time")){
						newTimer(numberPicker.getValue());
						countDownTimer.start();
					}
					else if(mode.equals("distance")){
						lastDistance=numberPicker.getValue();
						startRunByDistance();
					}


					isTimerStarted=true;
					btnStart.setText("STOP");
					numberPicker.setEnabled(false);
				}
			}
		});
	}

	private void newTimer(int i){
		countDownTimer = new CountDownTimer(i*60000, 60000) {
			public void onTick(long millisUntilFinished) {
				numberPicker.setValue((int)millisUntilFinished / 60000+1);
			}
			public void onFinish() {
				ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
				toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 5000);
				btnStart.setText("START");
				numberPicker.setValue(0);
				numberPicker.setEnabled(true);
			}
		};
	}
	private void startRunByDistance(){
		myTimer.schedule(new TimerTask() { // Определяем задачу
			@Override
			public void run() {
				//final Double newLatitude = 0.0;
				//final Double newLongitude = 0.0;

				if (!gps.canGetLocation()) {
					gps.showSettingsAlert();
				} else {
					Location newlocation = new Location("");
//					final Double newLatitude = gps.getLatitude();
//					final Double newLongitude = gps.getLongitude();
					newLatitude = gps.getLatitude();
					newLongitude = gps.getLongitude();
				}
				uiHandler.post(new Runnable() {
					@Override
					public void run() {
						if (lastLatitude > 0) {
							float[] results = new float[10];
							Location.distanceBetween(lastLatitude,lastLongitude,newLatitude,newLongitude,results);
							lastDistance=lastDistance-(int)results[0];
							numberPicker.setValue(lastDistance);
							Toast.makeText(getApplicationContext(), lastDistance.toString(),Toast.LENGTH_LONG);
						}
						else {

							lastLatitude=newLatitude;
							lastLongitude=newLongitude;
							Toast.makeText(getApplicationContext(), lastLatitude.toString(),Toast.LENGTH_LONG);
						}
						TextView textView=(TextView) findViewById(R.id.textView4);
						textView.setText(lastDistance.toString());
					};
				});
			}
		},0,60000); // интервал - 60000 миллисекунд, 0 миллисекунд до первого запуска.
	}

}