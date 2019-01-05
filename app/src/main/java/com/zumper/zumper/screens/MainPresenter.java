package com.zumper.zumper.screens;

import android.location.Location;
import android.support.annotation.NonNull;

public class MainPresenter implements MainContract.Presenter {
    @NonNull
    private final MainContract.View view;

    MainPresenter(@NonNull MainContract.View view) {
        this.view = view;
    }

    @Override
    public void onMapReady() {
        view.requestCurrentLocation();
    }

    @Override
    public void onLocationReady(@NonNull Location location) {
        view.showCurrentLocation(location);
    }

    @Override
    public void onRequestPermissionDenied() {
        view.showPermissionDeniedToast();
    }
}
