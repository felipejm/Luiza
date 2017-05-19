package br.com.luizalabs.luizalabs.weather.views.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import br.com.luizalabs.luizalabs.App;
import br.com.luizalabs.luizalabs.R;
import br.com.luizalabs.luizalabs.utils.GoogleMapHelper;
import br.com.luizalabs.luizalabs.utils.LocationHelper;
import br.com.luizalabs.luizalabs.weather.views.SwitchTemperatureUnitEvent;
import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoWindowViewHolder  {

    @BindView(R.id.temperature)
    TextView temperature;

    @BindView(R.id.weather_image)
    ImageView image;

    public InfoWindowViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
