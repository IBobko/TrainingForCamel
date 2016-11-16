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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import android.view.View;
import android.widget.Button;

import de.skubware.opentraining.R;

/**
 * An activity representing running menu.
 */
public class RunningListActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_running_list);

		// Show the Up button in the action bar.
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Button btnRunByTime = (Button) findViewById(R.id.btn_run_by_time);
		Button btnRunByDistance = (Button) findViewById(R.id.btn_run_by_distance);
		btnRunByTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(RunningListActivity.this.getApplicationContext(), RunningByTimeActivity.class);
				intent.putExtra("mode","time");
				startActivity(intent);
			}
		});
		btnRunByDistance.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(RunningListActivity.this.getApplicationContext(), RunningByTimeActivity.class);
				intent.putExtra("mode","distance");
				startActivity(intent);
			}
		});
	}

}
