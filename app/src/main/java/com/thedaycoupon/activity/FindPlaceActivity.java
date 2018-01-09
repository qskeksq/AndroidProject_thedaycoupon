package com.thedaycoupon.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thedaycoupon.R;
import com.thedaycoupon.util.Const;
import com.thedaycoupon.util.GeoUtil;
import com.thedaycoupon.util.StringUtil;

public class FindPlaceActivity extends BaseActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener, View.OnClickListener {

    private MapView mapView;
    private Button btnFindByName;
    private Button btnFindPlaceComplete;
    private TextView preview;
    private GoogleMap googleMap;
    private Place place;
    private LatLng latLng;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_place);
        init(savedInstanceState);
    }

    private void init(Bundle sis) {
        initView();
        setListener();
        setMapView(sis);
    }

    private void initView() {
        mapView = findViewById(R.id.findLocationMapView);
        btnFindByName = findViewById(R.id.btnFindByName);
        btnFindPlaceComplete = findViewById(R.id.btnFindPlaceComplete);
        preview = findViewById(R.id.preview);
    }

    private void setListener() {
        btnFindByName.setOnClickListener(this);
        btnFindPlaceComplete.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFindByName:
                goPlaceAutoComplete();
                break;
            case R.id.btnFindPlaceComplete:
                setResult();
                onBackPressed();
                break;
        }
    }

    private void goPlaceAutoComplete() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    private void setResult() {
        Intent intent = new Intent();
        if (place == null) {
            String address = GeoUtil.getGeoAddress(FindPlaceActivity.this, latLng);
            intent.putExtra(Const.SET_RESULT_EXTRA1, latLng);
            intent.putExtra(Const.SET_RESULT_EXTRA2, address);
        } else {
            intent.putExtra(Const.SET_RESULT_EXTRA1, place.getLatLng());
            intent.putExtra(Const.SET_RESULT_EXTRA2, place.getAddress().toString());
        }
        setResult(RESULT_OK, intent);
    }

    private void setMapView(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    private void setMapUi() {
        UiSettings settings = googleMap.getUiSettings();
        settings.setMyLocationButtonEnabled(true);
        settings.setZoomControlsEnabled(true);
        settings.setRotateGesturesEnabled(false);
        settings.setMapToolbarEnabled(true);
    }

    private void setMapListener() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setOnMapClickListener(this);
        googleMap.setMyLocationEnabled(true);
    }

    private void animateCamera(LatLng latLng, int zoom) {
        CameraPosition cp = new CameraPosition.Builder()
                .target(latLng)
                .zoom(zoom)
                .tilt(10)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
    }

    private void moveCamera(LatLng latLng, int zoom) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void placeMarker(LatLng latLng) {
        this.latLng = latLng;
        MarkerOptions options = new MarkerOptions();
        options.position(latLng);
        googleMap.clear();
        googleMap.addMarker(options);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        setMapUi();
        setMapListener();
        moveCamera(GeoUtil.getDefaultLatLng(), 15);
        placeMarker(GeoUtil.getDefaultLatLng());
    }

    @Override
    public void onMapClick(LatLng latLng) {
        animateCamera(latLng, 17);
        setPreviewText(GeoUtil.getGeoAddress(this, latLng));
        placeMarker(latLng);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            place = PlaceAutocomplete.getPlace(this, data);
            setAddress();
            animateCamera(place.getLatLng(), 17);
            placeMarker(place.getLatLng());
        }
    }

    private void setPreviewText(String prev){
        preview.setText(prev);
    }

    private void setAddress() {
        String address = StringUtil.substring(place.getAddress().toString(), 5);
        preview.setText(address);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


}
