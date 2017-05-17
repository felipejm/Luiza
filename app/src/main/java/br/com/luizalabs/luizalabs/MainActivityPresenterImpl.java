package br.com.luizalabs.luizalabs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivityPresenterImpl extends AppCompatActivity  implements MainView{

    private WeatherInteractor interactor;
    private MainView mainView;

    public MainActivityPresenterImpl(WeatherInteractor interactor, MainView mainView) {
        this.interactor = interactor;
        this.mainView = mainView;
    }
}
