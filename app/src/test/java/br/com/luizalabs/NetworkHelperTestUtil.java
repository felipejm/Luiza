package br.com.luizalabs;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.mockito.Mockito;

public class NetworkHelperTestUtil {

    public static void mockNetworkAvailability(Context context, boolean isAvailable) {
        ConnectivityManager connectivityManager = Mockito.mock(ConnectivityManager.class);
        NetworkInfo activeNetworkInfo = Mockito.mock(NetworkInfo.class);
        Mockito.doReturn(connectivityManager).when(context).getSystemService(Context.CONNECTIVITY_SERVICE);
        Mockito.doReturn(activeNetworkInfo).when(connectivityManager).getActiveNetworkInfo();
        Mockito.doReturn(activeNetworkInfo).when(connectivityManager).getActiveNetworkInfo();
        Mockito.doReturn(isAvailable).when(activeNetworkInfo).isConnected();
    }
}
