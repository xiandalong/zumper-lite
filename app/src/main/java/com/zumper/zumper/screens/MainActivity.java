package com.zumper.zumper.screens;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zumper.zumper.R;
import com.zumper.zumper.model.RestaurantLocation;
import com.zumper.zumper.model.RestaurantResponse;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements MainContract.View, OnMapReadyCallback {
    private static final int PERMISSIONS_REQUEST_LOCATION = 99;
    private static final float MIN_ZOOM_PREFERENCE = 6.0f;
    private static final float MAX_ZOOM_PREFERENCE = 14.0f;

    private GoogleMap map;
    private FusedLocationProviderClient providerClient;
    private MainPresenter presenter;
    private SupportMapFragment mapFragment;

    @OnClick(R.id.list_button)
    public void onListButtonClicked() {
        presenter.onListButtonClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        presenter = new MainPresenter(this);

        initMap();

        providerClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMinZoomPreference(MIN_ZOOM_PREFERENCE);
        map.setMaxZoomPreference(MAX_ZOOM_PREFERENCE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            presenter.onMapReady();
        } else {
            ActivityCompat.requestPermissions(this,
                                              new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION },
                                              PERMISSIONS_REQUEST_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
            if (permissions.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.onMapReady();
            } else {
                presenter.onRequestPermissionDenied();
            }
        }
    }

    @Override
    public void showPermissionDeniedToast() {
        Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void requestCurrentLocation() {
        if (map == null) {
            return;
        }
        try {
            providerClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    presenter.onLocationReady(location);
                }
            });
        } catch (SecurityException e) {
            Timber.e(e.toString());
        }
    }

    @Override
    public void showCurrentLocation(@NonNull Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        map.addMarker(new MarkerOptions().position(latLng));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public void showNearbyRestaurants(@NonNull RestaurantLocation location, @NonNull String name) {
        LatLng latLng = new LatLng(location.lat, location.lng);
        map.addMarker(new MarkerOptions().title(name).position(latLng));
    }

    @Override
    public void gotoListFragment(@NonNull RestaurantResponse response) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.map, ListFragment.newInstance(response)).addToBackStack(null);
        transaction.commit();
    }

    private void initMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }
}
