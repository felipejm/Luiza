package br.com.luizalabs.utils;


import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

public class LocationHelper {

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
