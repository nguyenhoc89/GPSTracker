package com.hocn.gpstracker.services;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hocn.gpstracker.LocationTrackerManager;

public class LocationTrackerService extends Service {
    private boolean running = false;
    private LocationTrackerManager trackerManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (running) {
            return START_STICKY;
        }
        running = true;

        trackerManager = new LocationTrackerManager(this);
        trackerManager.initTracker();
        trackerManager.startTracker();
        trackerManager.setLocationTrackerListener(new LocationTrackerManager.ILocationTracker() {
            @Override
            public void onLocationChanged(Location location) {
            }

            @Override
            public void onLocationSettingChangeUnavailable() {

            }
        });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop location tracker
        trackerManager.stopTracker();
        running = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
