package com.hocn.gpstrackerproject;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hocn.gpstracker.LocationTrackerManager;

public class MainActivity extends AppCompatActivity {
    private LocationTrackerManager trackerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLocationTracker();
    }

    private void initLocationTracker() {
        trackerManager = new LocationTrackerManager(this);
        trackerManager.initTracker();
        trackerManager.setLocationTrackerListener(new LocationTrackerManager.ILocationTracker() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("iii", "Location update: " + location);
            }

            @Override
            public void onLocationSettingChangeUnavailable() {
                Log.d("iii", "onLocationSettingChangeUnavailable");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (trackerManager != null) {
            trackerManager.startTracker();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (trackerManager != null) {
            trackerManager.stopTracker();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (trackerManager != null) {
            trackerManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}
