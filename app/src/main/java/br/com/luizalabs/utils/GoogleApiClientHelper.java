package br.com.luizalabs.utils;


import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class GoogleApiClientHelper {

    public static GoogleApiClient create(Context context, GoogleApiClient.ConnectionCallbacks callbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(callbacks)
                .addOnConnectionFailedListener(onConnectionFailedListener)
                .addApi(LocationServices.API)
                .build();
    }
}
