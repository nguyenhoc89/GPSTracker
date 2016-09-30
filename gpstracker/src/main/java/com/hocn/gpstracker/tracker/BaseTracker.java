package com.hocn.gpstracker.tracker;

import android.content.Context;
import android.location.Location;

import com.hocn.gpstracker.LocationTrackerManager;

public abstract class BaseTracker {
    protected int MIN_DISTANCE_CHANGE_FOR_UPDATE = 100;
    protected int MIN_TIME_FOR_UPDATE = 100;

    protected Context mContext;
    protected LocationTrackerManager mTrackerManager;

    public void init(Context context, LocationTrackerManager trackerManager) {
        this.mContext = context;
        this.mTrackerManager = trackerManager;
    }

    public abstract void startTracker();
    public abstract void stopTracker();
    public abstract Location getLastKnowLocation();
}
