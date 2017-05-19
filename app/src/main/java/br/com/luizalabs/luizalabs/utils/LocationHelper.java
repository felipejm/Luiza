package br.com.luizalabs.luizalabs.utils;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class LocationHelper {

    public static boolean isLocationAvailable(GoogleApiClient googleApiClient) {
        LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(googleApiClient);
        return locationAvailability == null || locationAvailability.isLocationAvailable();
    }

    public static LatLng getLastLocation( GoogleApiClient googleApiClient) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        LatLng lastLocation = null;
        if(location != null) {
            lastLocation = new LatLng(location.getLatitude(), location.getLongitude());
        }
        return lastLocation;
    }
}
