package br.com.luizalabs.luizalabs.weather.views.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import br.com.luizalabs.luizalabs.R;
import br.com.luizalabs.luizalabs.utils.DrawableHelper;
import br.com.luizalabs.luizalabs.weather.model.Weather;


public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public InfoWindowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        Weather weather = (Weather) marker.getTag();
        View view = LayoutInflater.from(context).inflate(R.layout.view_marker_info_window, null);

        InfoWindowViewHolder viewHolder = new InfoWindowViewHolder(view);
        viewHolder.temperature.setText(context.getString(R.string.temperature_format, weather.getTemperature()));
        viewHolder.image.setImageDrawable(DrawableHelper.fromName(context, weather.getIcon(), R.drawable.ic_01d));
        return view;
    }
}
