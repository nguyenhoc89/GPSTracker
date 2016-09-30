package com.hocn.gpstracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.util.Log;

import com.hocn.gpstracker.services.LocationTrackerService;

import java.util.ArrayList;
import java.util.List;

public class LocationTrackerServiceManager {
    private static LocationTrackerServiceManager instances;
    private BroadcastReceiver mReceiver;

    public interface ILocationTracker {
        void onLocationChanged(Location location);
    }
    private List<ILocationTracker> listLocationTrackerListener = new ArrayList<>();

    public static LocationTrackerServiceManager getInstances() {
        if (instances == null) {
            instances = new LocationTrackerServiceManager();
        }
        return instances;
    }

    public void startLocationService(Context context) {
        context.startService(new Intent(context, LocationTrackerService.class));

        // Register broadcast receive
        if (mReceiver == null) {
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    double lat = intent.getDoubleExtra("lat", 0);
                    double lon = intent.getDoubleExtra("lon", 0);
                    Location location = new Location("");
                    location.setLatitude(lat);
                    location.setLongitude(lon);

                    for (ILocationTracker locationTrackerListener: listLocationTrackerListener) {
                        if (locationTrackerListener != null) {
                            locationTrackerListener.onLocationChanged(location);
                        }
                    }
                }
            };
        }

        IntentFilter intentFilter = new IntentFilter(
                "com.hocn.gpstracker.LOCATION_RECEIVE");
        context.registerReceiver(mReceiver, intentFilter);
    }

    public void stopLocationService(Context context) {
        context.stopService(new Intent(context, LocationTrackerService.class));

        // Unregister broadcast receive
        if (mReceiver != null) {
            try {
                context.unregisterReceiver(mReceiver);
            } catch (IllegalArgumentException e) {
                Log.e("iii", e.getMessage());
            } finally {
                mReceiver = null;
            }
        }
    }

    public void addLocationTrackerListener(ILocationTracker locationTrackerListener) {
        listLocationTrackerListener.add(locationTrackerListener);
    }

    public void remoteLocationTrackerListener(ILocationTracker locationTrackerListener) {
        listLocationTrackerListener.remove(locationTrackerListener);
        locationTrackerListener = null;
    }
}
