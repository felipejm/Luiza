package br.com.luizalabs.utils;


import android.content.IntentSender;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;

public class LocationHelper {

    public static final int LOCATION_SETTING_REQUEST_CODE = 1032;
    private static final int REQUEST_LOCATION_INTERVAL = 1000 * 300;
    private static final int REQUEST_LOCATION_FAST_INTERVAL = 1000 * 200;

    public static void removeLocationRequestUpdate(GoogleApiClient googleApiClient, LocationListener locationListener){
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, locationListener);
    }

    public static void requestLocation(GoogleApiClient googleApiClient, FragmentActivity fragmentActivity, LocationListener locationListener){
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(REQUEST_LOCATION_INTERVAL);
        locationRequest.setFastestInterval(REQUEST_LOCATION_FAST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallbacks() {
            @Override
            public void onSuccess(@NonNull Result result) {
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, locationListener);
            }

            @Override
            public void onFailure(@NonNull Status status) {
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(fragmentActivity, LOCATION_SETTING_REQUEST_CODE);
                        } catch (IntentSender.SendIntentException e) {
                        }
                        break;
                }
            }
        });
    }

    public static boolean distanceBetweenLessThen(LatLng origin, LatLng destine, int maxDistance) {
        float[] floats = new float[1];
        Location.distanceBetween(origin.latitude, origin.longitude, destine.latitude, destine.longitude, floats);
        return floats[0] <= maxDistance;
    }

    public static float distanceBetween(LatLng origin, LatLng destine) {
        float[] floats = new float[1];
        Location.distanceBetween(origin.latitude, origin.longitude, destine.latitude, destine.longitude, floats);
        return floats[0];
    }

    public static boolean isLocationAvailable(GoogleApiClient googleApiClient) {
        LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(googleApiClient);
        return locationAvailability == null || locationAvailability.isLocationAvailable();
    }

    public static LatLng getLastLocation(GoogleApiClient googleApiClient) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        LatLng lastLocation = null;
        if (location != null) {
            lastLocation = new LatLng(location.getLatitude(), location.getLongitude());
        }
        return lastLocation;
    }
}
