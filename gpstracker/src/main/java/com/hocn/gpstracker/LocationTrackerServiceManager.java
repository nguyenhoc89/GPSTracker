package com.hocn.gpstracker;

import android.content.Context;
import android.content.Intent;

import com.hocn.gpstracker.services.LocationTrackerService;

public class LocationTrackerServiceManager {
    public static void startLocationService(Context context) {
        context.startService(new Intent(context, LocationTrackerService.class));
    }

    public static void stopLocationService(Context context) {
        context.stopService(new Intent(context, LocationTrackerService.class));
    }
}
