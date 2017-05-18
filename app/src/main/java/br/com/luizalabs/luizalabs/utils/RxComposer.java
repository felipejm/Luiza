package br.com.luizalabs.luizalabs.utils;

import javax.xml.transform.Transformer;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxComposer {

    public static <T> ObservableTransformer<T, T> newThread() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
