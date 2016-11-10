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

import java.io.IOException;
import java.util.List;

import de.skubware.opentraining.R;
import de.skubware.opentraining.activity.create_workout.ExerciseTypeListActivity;
import de.skubware.opentraining.activity.manage_workouts.WorkoutListActivity;
import de.skubware.opentraining.activity.settings.SettingsActivity;
import de.skubware.opentraining.basic.Workout;
import de.skubware.opentraining.db.Cache;
import de.skubware.opentraining.db.DataProvider;
import de.skubware.opentraining.db.IDataProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.examples.youtubeapidemo.ActionBarDemoActivity;
import com.examples.youtubeapidemo.DeveloperKey;
import com.examples.youtubeapidemo.FragmentDemoActivity;
import com.examples.youtubeapidemo.FullscreenDemoActivity;
import com.examples.youtubeapidemo.IntentsDemoActivity;
import com.examples.youtubeapidemo.PlayerControlsDemoActivity;
import com.examples.youtubeapidemo.PlayerViewDemoActivity;
import com.examples.youtubeapidemo.StandalonePlayerDemoActivity;
import com.examples.youtubeapidemo.VideoListDemoActivity;
import com.examples.youtubeapidemo.VideoWallDemoActivity;
import com.examples.youtubeapidemo.YouTubeAPIDemoActivity;
import com.examples.youtubeapidemo.YouTubeFailureRecoveryActivity;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import at.technikum.mti.fancycoverflow.FancyCoverFlow;


public class MainActivity extends ActionBarActivity {
    /** Tag for logging */
    public static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

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
                    case 0:
                        showSelectWorkoutDialog();
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this.getApplicationContext(), ExerciseTypeListActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this.getApplicationContext(), WorkoutListActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this.getApplicationContext(), SettingsActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this.getApplicationContext(), PlayerControlsDemoActivity.class));
                        break;

                    case 5:
                        startActivity(new Intent(MainActivity.this.getApplicationContext(), ActionBarDemoActivity.class));
                        break;

                    case 6:
                        startActivity(new Intent(MainActivity.this.getApplicationContext(), FragmentDemoActivity.class));
                        break;

                    case 7:
                        startActivity(new Intent(MainActivity.this.getApplicationContext(), FullscreenDemoActivity.class));

                        YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                            public void initialize(HttpRequest request) throws IOException {
                            }
                        }).setApplicationName("youtube-cmdline-search-sample").build();


                        // Prompt the user to enter a query term.
                        String queryTerm = "hello";

                        // Define the API request for retrieving search results.
                        YouTube.Search.List search = null;
                        try {
                            search = youtube.search().list("id,snippet");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // Set your developer key from the {{ Google Cloud Console }} for
                        // non-authenticated requests. See:
                        // {{ https://cloud.google.com/console }}
                        String apiKey = DeveloperKey.DEVELOPER_KEY;
                        search.setKey(apiKey);
                        search.setQ(queryTerm);

                        // Restrict the search results to only include videos. See:
                        // https://developers.google.com/youtube/v3/docs/search/list#type
                        search.setType("video");

                        // To increase efficiency, only retrieve the fields that the
                        // application uses.
                        search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
                        search.setMaxResults(10L);

                        // Call the API and print results.
                        SearchListResponse searchResponse = null;
                        try {
                            searchResponse = search.execute();

                            List<SearchResult> searchResultList = searchResponse.getItems();
                            for (SearchResult sr: searchResultList) {
                                System.out.println(sr.getEtag());
                            }
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    case 8:
                        startActivity(new Intent(MainActivity.this.getApplicationContext(), IntentsDemoActivity.class));
                        break;

                    case 9:
                        startActivity(new Intent(MainActivity.this.getApplicationContext(), PlayerViewDemoActivity.class));
                        break;

                    case 10:
                        startActivity(new Intent(MainActivity.this.getApplicationContext(), StandalonePlayerDemoActivity.class));
                        break;

                    case 11:
                        startActivity(new Intent(MainActivity.this.getApplicationContext(), VideoListDemoActivity.class));
                        break;

                    case 12:
                        startActivity(new Intent(MainActivity.this.getApplicationContext(), VideoWallDemoActivity.class));
                        break;

                    case 13:
                        startActivity(new Intent(MainActivity.this.getApplicationContext(), YouTubeAPIDemoActivity.class));
                        break;

                    case 14:
                        startActivity(new Intent(MainActivity.this.getApplicationContext(), YouTubeFailureRecoveryActivity.class));
                        break;

                    case 15:
                        startActivity(new Intent(MainActivity.this.getApplicationContext(), SearchingActivity.class));
                        break;


                    default:
                        Log.wtf(TAG, "This item should not exist.");
                }
            }
        });


    }


    /** Shows a dialog for choosing a {@link Workout} */
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
