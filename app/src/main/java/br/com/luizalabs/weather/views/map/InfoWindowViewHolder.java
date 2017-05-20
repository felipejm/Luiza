package br.com.luizalabs.weather.views.map;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.luizalabs.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoWindowViewHolder {

    @BindView(R.id.temperature)
    TextView temperature;

    @BindView(R.id.weather_image)
    ImageView image;

    public InfoWindowViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
