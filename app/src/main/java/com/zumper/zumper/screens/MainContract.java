package com.zumper.zumper.screens;

import android.location.Location;
import android.support.annotation.NonNull;

class MainContract {
    interface View {
        void requestCurrentLocation();

        void showCurrentLocation(@NonNull Location location);

        void showPermissionDeniedToast();
    }

    interface Presenter {
        void onMapReady();

        void onLocationReady(@NonNull Location location);

        void onRequestPermissionDenied();
    }
}
