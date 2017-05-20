package br.com.luizalabs.utils;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;

import java.util.Locale;

public class GoogleMapHelper {

    public static final int ZOOM_LEVEL = 8;

    public static void configureMap(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setTrafficEnabled(false);
        googleMap.setMyLocationEnabled(true);
        googleMap.setMaxZoomPreference(ZOOM_LEVEL);
        googleMap.setMinZoomPreference(ZOOM_LEVEL);
        googleMap.setIndoorEnabled(false);
        googleMap.setBuildingsEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
    }

    public static String calculateBoundingBox(LatLng origin, int distance) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        LatLngBounds initialBounds = builder.include(origin).build();

        LatLng bottomRight = SphericalUtil.computeOffset(initialBounds.northeast, distance * Math.sqrt(2), 135);
        LatLng topLeft = SphericalUtil.computeOffset(initialBounds.southwest, distance * Math.sqrt(2), 315);

        return String.format(Locale.getDefault(), "%f,%f,%f,%f", topLeft.longitude, topLeft.latitude, bottomRight.longitude, bottomRight.latitude);
    }
}
