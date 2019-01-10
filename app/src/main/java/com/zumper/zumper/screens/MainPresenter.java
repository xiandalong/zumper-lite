package com.zumper.zumper.screens;

import android.location.Location;
import android.support.annotation.NonNull;

import com.zumper.Constant;
import com.zumper.zumper.model.Restaurant;
import com.zumper.zumper.model.RestaurantResponse;
import com.zumper.zumper.network.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainPresenter implements MainContract.Presenter {
    @NonNull
    private final MainContract.View view;
    private CompositeDisposable disposables = new CompositeDisposable();
    private RestaurantResponse restaurantResponse;

    MainPresenter(@NonNull MainContract.View view) {
        this.view = view;
    }

    @Override
    public void onMapReady() {
        view.requestCurrentLocation();
    }

    @Override
    public void onLocationReady(@NonNull Location location) {
        fetchNearbyRestaurants(location.getLatitude(), location.getLongitude());
        view.showCurrentLocation(location);
    }

    private void fetchNearbyRestaurants(double latitude, double longitude) {
        String location = String.valueOf(latitude) + "," + String.valueOf(longitude);
        Disposable disposable = RetrofitClient.getZumperApi()
                .getRestaurantsByLocation(location, Constant.RADIUS, Constant.TYPE, Constant.PLACE_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restaurantResponse -> {
                               this.restaurantResponse = restaurantResponse;

                               for (Restaurant restaurant : restaurantResponse.restaurants) {
                                   view.showNearbyRestaurants(restaurant.geometry.location, restaurant.name);
                               }
                           },
                           throwable -> Timber.e(throwable.toString()));
        disposables.add(disposable);
    }

    @Override
    public void onRequestPermissionDenied() {
        view.showPermissionDeniedToast();
    }

    @Override
    public void onDestroy() {
        disposables.clear();
    }

    @Override
    public void onListButtonClicked() {
        view.gotoListFragment(restaurantResponse);
    }
}
