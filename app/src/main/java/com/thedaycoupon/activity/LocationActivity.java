package com.thedaycoupon.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thedaycoupon.R;
import com.thedaycoupon.util.Const;

public class LocationActivity extends AppCompatActivity
            implements OnMapReadyCallback {

    MapView locationMapView;
    double longitude, latitude;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaction);

        longitude = getIntent().getDoubleExtra(Const.GOUTIL_EXTRA_1, 0);
        latitude = getIntent().getDoubleExtra(Const.GOUTIL_EXTRA_2, 0);

        locationMapView = findViewById(R.id.locationMapView);
        locationMapView.onCreate(savedInstanceState);
        locationMapView.getMapAsync(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        locationMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        locationMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationMapView.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng latLng = new LatLng(longitude, latitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        placeMarker(latLng);
    }

    private void placeMarker(LatLng latLng) {
        MarkerOptions options = new MarkerOptions();
        options.position(latLng);
        googleMap.addMarker(options);
    }
}
