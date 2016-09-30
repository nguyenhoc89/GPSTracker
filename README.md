# GPSTracker
Simple android library for gps tracking

## Installation
Add this to your build.gradle file
```
compile 'com.hocn.gpstracker:gpstracker:0.0.1'
```

## Use

In your activity

```java
private void initLocationTracker() {
        trackerManager = new LocationTrackerManager(this);
        trackerManager.initTracker();
        trackerManager.setLocationTrackerListener(new LocationTrackerManager.ILocationTracker() {
            @Override
            public void onLocationChanged(Location location) {
                // Your logic
            }

            @Override
            public void onLocationSettingChangeUnavailable() {
                // Your logic
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
protected void onPause() {
	super.onPause();
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
```

## You can also use as service

Add to application class

```java
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
```

Add event listener code to your activity

```java
LocationTrackerServiceManager.getInstances().addLocationTrackerListener(new LocationTrackerServiceManager.ILocationTracker() {
	@Override
	public void onLocationChanged(Location location) {
		Log.d("iii", location.toString());
	}
});
```
