/**
 * This is OpenTraining, an Android application for planning your your fitness training.
 * Copyright (C) 2012-2014 Christian Skubich
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.skubware.opentraining.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.examples.youtubeapidemo.VideoListDemoActivity;
import com.idescout.sql.SqlScoutServer;

import java.util.List;

import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import de.skubware.opentraining.R;
import de.skubware.opentraining.activity.create_workout.ExerciseTypeListActivity;
import de.skubware.opentraining.activity.manage_workouts.WorkoutListActivity;
import de.skubware.opentraining.activity.settings.SettingsActivity;
import de.skubware.opentraining.basic.Workout;
import de.skubware.opentraining.database.DatabaseForCamel;
import de.skubware.opentraining.database.WeightEntity;
import de.skubware.opentraining.db.Cache;
import de.skubware.opentraining.db.DataProvider;
import de.skubware.opentraining.db.IDataProvider;


public class MainActivity extends ActionBarActivity {
    /**
     * Tag for logging
     */
    public static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SqlScoutServer.create(this, getPackageName());

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);


        }

        setContentView(R.layout.activity_navigation_layout);
        getSupportActionBar().setIcon(R.drawable.icon_dumbbell);

        setUpNavigation();

        // load data/parse .xml files in background
        final Context mContext = this.getApplicationContext();
        new Thread() {
            @Override
            public void run() {
                Cache.INSTANCE.updateCache(mContext);
            }
        }.start();


        //Launch what's new dialog
        final WhatsNewDialog whatsNewDialog = new WhatsNewDialog(this);
        whatsNewDialog.show(); // (will only be shown if started the first time since last change)

        // show disclaimer
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean showDisclaimer = settings.getBoolean(DisclaimerDialog.PREFERENCE_SHOW_DISCLAIMER, true);
        if (showDisclaimer) {
            new DisclaimerDialog(this);
        }

        final DatabaseForCamel databaseForCamel = new DatabaseForCamel(getApplicationContext());
        SQLiteDatabase db = databaseForCamel.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("d", "1");
        values.put("w", 100);



        long newRowId = db.insert(WeightEntity.TABLE_NAME,null,values);





        //databaseForCamel.
        //databaseForCamel.getWritableDatabase().execSQL("INSERT INTO (id,d,w) values('1','1','1')");


    }

    private void setUpNavigation() {
        FancyCoverFlow mFancyCoverFlow = (FancyCoverFlow) this.findViewById(R.id.fancyCoverFlow);

        mFancyCoverFlow.setAdapter(new NavigationGalleryAdapter(this.getApplicationContext()));
        mFancyCoverFlow.setUnselectedAlpha(0.5f);
        mFancyCoverFlow.setUnselectedSaturation(0.0f);
        mFancyCoverFlow.setUnselectedScale(0.2f);
        mFancyCoverFlow.setMaxRotation(35);
        mFancyCoverFlow.setScaleDownGravity(0.0f);
        mFancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);

        mFancyCoverFlow.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // start training
                        showSelectWorkoutDialog();
                        break;
                    case 1: // create workout
                        startActivity(new Intent(MainActivity.this.getApplicationContext(), ExerciseTypeListActivity.class));
                        break;
                    case 2: // manage workout
                        startActivity(new Intent(MainActivity.this.getApplicationContext(), WorkoutListActivity.class));
                        break;
                    case 3: // settings
                        startActivity(new Intent(MainActivity.this.getApplicationContext(), SettingsActivity.class));
                        break;
                    case 4: // Video1
                        startActivity(new Intent(MainActivity.this.getApplicationContext(), VideoListDemoActivity.class));
                        break;
//                    case 5: // Video2
//                        startActivity(new Intent(MainActivity.this.getApplicationContext(), ActionBarDemoActivity.class));
//                        break;
//                    case 6: // Video3
//                        startActivity(new Intent(MainActivity.this.getApplicationContext(), FragmentDemoActivity.class));
//                        break;
//                    case 7: // Video4
//                        startActivity(new Intent(MainActivity.this.getApplicationContext(), FullscreenDemoActivity.class));
//
//
//
//                    case 8: // Video5
//                        startActivity(new Intent(MainActivity.this.getApplicationContext(), IntentsDemoActivity.class));
//                        break;
//
//                    case 9: // Video6
//                        startActivity(new Intent(MainActivity.this.getApplicationContext(), PlayerViewDemoActivity.class));
//                        break;
//
//                    case 10: // Video7
//                        startActivity(new Intent(MainActivity.this.getApplicationContext(), StandalonePlayerDemoActivity.class));
//                        break;
//
//                    case 11: // Video8
//                        startActivity(new Intent(MainActivity.this.getApplicationContext(), VideoListDemoActivity.class));
//                        break;
//
//                    case 12: // Video9
//                        startActivity(new Intent(MainActivity.this.getApplicationContext(), VideoWallDemoActivity.class));
//                        break;
//
//                    case 13: // Video10
//                        startActivity(new Intent(MainActivity.this.getApplicationContext(), YouTubeAPIDemoActivity.class));
//                        break;
//
//                    case 14: // Video11
//                        startActivity(new Intent(MainActivity.this.getApplicationContext(), YouTubeFailureRecoveryActivity.class));
//                        break;
//
//                    case 15: // Video12
//                        startActivity(new Intent(MainActivity.this.getApplicationContext(), SearchingActivity.class));
//                        break;


                    default:
                        Log.wtf(TAG, "This item should not exist.");
                }
            }
        });


    }


    /**
     * Shows a dialog for choosing a {@link Workout}
     */
    private void showSelectWorkoutDialog() {
        IDataProvider dataProvider = new DataProvider(this);

        // get all Workouts
        final List<Workout> workoutList = dataProvider.getWorkouts();

        Log.d(TAG, "Number of Workouts: " + workoutList.size());
        switch (workoutList.size()) {
            // show error message, if there is no Workout
            case 0:
                Toast.makeText(MainActivity.this, getString(R.string.no_workout), Toast.LENGTH_LONG).show();
                break;
            // choose Workout, if there is/are one or more Workout(s)
            default:
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                // Create and show the dialog.
                DialogFragment newFragment = SelectWorkoutDialogFragment.newInstance();
                newFragment.show(ft, "dialog");
        }

    }

}
