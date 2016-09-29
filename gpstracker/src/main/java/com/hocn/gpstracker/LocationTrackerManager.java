package com.hocn.gpstracker;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.hocn.gpstracker.tracker.BaseTracker;
import com.hocn.gpstracker.tracker.GGTracker;
import com.hocn.gpstracker.util.TrackerUtil;

public class LocationTrackerManager {
    public static final int RESULT_CODE_SETTING_LOCATION_ACTIVITY = 1000;
    private Context mContext;
    private BaseTracker tracker;

    public interface ILocationTracker {
        void onLocationChanged(Location location);
        void onLocationSettingChangeUnavailable();
    }
    private ILocationTracker locationTrackerListener;

    public LocationTrackerManager(Context context) {
        this.mContext = context;
    }

    public void initTracker() {
        boolean isGoogleServiceAvailable = TrackerUtil.checkPlayServicesAvailable(mContext);

        if (isGoogleServiceAvailable) {
            tracker = new GGTracker();
            tracker.init(mContext, this);
        }
    }

    public void startTracker() {
        tracker.startTracker();
    }

    public void stopTracker() {
        tracker.stopTracker();
    }

    public void onLocationChanged(Location location) {
        locationTrackerListener.onLocationChanged(location);
    }

    public void onLocationSettingChangeUnavailable() {
        locationTrackerListener.onLocationSettingChangeUnavailable();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("iii", "Result code: " + resultCode);
    }

    public void setLocationTrackerListener(ILocationTracker locationTrackerListener) {
        this.locationTrackerListener = locationTrackerListener;
    }
}
