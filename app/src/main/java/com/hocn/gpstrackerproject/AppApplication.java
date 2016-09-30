package com.hocn.gpstrackerproject;

import android.app.Application;

import com.hocn.gpstracker.LocationTrackerServiceManager;

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Start service
        LocationTrackerServiceManager.getInstances().startLocationService(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        // Stop service
        LocationTrackerServiceManager.getInstances().stopLocationService(this);
    }
}
