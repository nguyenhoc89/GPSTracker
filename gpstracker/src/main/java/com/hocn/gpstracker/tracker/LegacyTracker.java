package com.hocn.gpstracker.tracker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.hocn.gpstracker.util.LogUtil;

public class LegacyTracker extends BaseTracker implements LocationListener {
    private LocationManager locationManager;

    @Override
    public void startTracker() {
        locationManager = (LocationManager) mContext
                .getSystemService(Context.LOCATION_SERVICE);

        // FIXME: 9/19/2016 
        if (locationManager == null) {
            LogUtil.logError("Can't get locationManager");
        }

        // getting GPS status
        boolean isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        boolean isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        // FIXME: 9/19/2016
        if (!isGPSEnabled && !isNetworkEnabled) {
            // no network provider is enabled
            LogUtil.logError("GPS and Network provider unavailable");
        }

        // FIXME: 9/19/2016
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                MIN_TIME_FOR_UPDATE,
                MIN_DISTANCE_CHANGE_FOR_UPDATE, this);
    }

    @Override
    public void stopTracker() {
        // FIXME: 9/19/2016
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public Location getLastKnowLocation() {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        LogUtil.logDebug("Location: " + location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        LogUtil.logDebug("On Provider enable: " + s);
    }

    @Override
    public void onProviderDisabled(String s) {
        LogUtil.logDebug("On Provider disable: " + s);
    }


    private void turnGPSOn(){
        Log.d("iii", "Check GPS On");
        String provider = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(!provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            mContext.sendBroadcast(poke);
        }
    }

    private void turnGPSOff(){
        String provider = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(provider.contains("gps")){ //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            mContext.sendBroadcast(poke);
        }
    }
}
