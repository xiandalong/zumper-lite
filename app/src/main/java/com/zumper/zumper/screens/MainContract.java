package com.zumper.zumper.screens;

import android.location.Location;
import android.support.annotation.NonNull;

import com.zumper.zumper.model.RestaurantLocation;
import com.zumper.zumper.model.RestaurantResponse;

class MainContract {
    interface View {
        void requestCurrentLocation();

        void showCurrentLocation(@NonNull Location location);

        void showPermissionDeniedToast();

        void showNearbyRestaurants(@NonNull RestaurantLocation GLocation, @NonNull String name);

        void gotoListFragment(@NonNull RestaurantResponse response);
    }

    interface Presenter {
        void onMapReady();

        void onLocationReady(@NonNull Location location);

        void onRequestPermissionDenied();

        void onDestroy();

        void onListButtonClicked();
    }
}
