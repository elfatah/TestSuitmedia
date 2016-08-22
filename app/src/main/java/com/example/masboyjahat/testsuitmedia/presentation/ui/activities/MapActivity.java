package com.example.masboyjahat.testsuitmedia.presentation.ui.activities;

import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;


import com.example.masboyjahat.testsuitmedia.R;
import com.example.masboyjahat.testsuitmedia.domain.executor.impl.ThreadExecutor;
import com.example.masboyjahat.testsuitmedia.domain.model.EventModel;
import com.example.masboyjahat.testsuitmedia.presentation.presenters.EventPresenter;
import com.example.masboyjahat.testsuitmedia.presentation.presenters.impl.EventPresenterImpl;
import com.example.masboyjahat.testsuitmedia.presentation.ui.adapters.MapPagerAdapter;
import com.example.masboyjahat.testsuitmedia.threading.MainThreadImpl;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapActivity extends AppCompatActivity implements EventPresenter.View, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private EventPresenter eventPresenter;
    private MapPagerAdapter mapPagerAdapter;
    private ProgressDialog progress;
    private GoogleMap mGoogleMap;
    private List<EventModel> eventModelList;
    private SupportMapFragment mapFragment;
    private List<Marker> markerList = new ArrayList<>();
    private List<LatLng> latLngList = new ArrayList<>();

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.fabListEvent)
    FloatingActionButton fabListEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_map);
        ButterKnife.bind(MapActivity.this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        progress = new ProgressDialog(this);
        mapPagerAdapter = new MapPagerAdapter(this);
        viewPager.setAdapter(mapPagerAdapter);
        viewPager.setPageMargin(5);
        eventPresenter = new EventPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this);
        showProgress();
        eventPresenter.getAllEvents();
        fabListEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                zoomCamera(latLngList.get(position), markerList.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });


    }


    @Override
    public void showAllEvent(List<EventModel> eventModelList) {
        mapPagerAdapter.fillEventList(eventModelList);
        this.eventModelList = eventModelList;
        mapFragment.getMapAsync(this);

        hideProgress();
    }

    @Override
    public void noData() {

    }

    @Override
    public void showProgress() {
        progress.setMessage(getString(R.string.loading_message));
        progress.show();
    }

    @Override
    public void hideProgress() {
        progress.dismiss();

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        LatLng latLng = new LatLng(-6.9186151, 107.6158859);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        initMarker(eventModelList);
        mGoogleMap.setOnMarkerClickListener(this);


    }

    private void zoomCamera(LatLng latLng, Marker marker) {
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        marker.showInfoWindow();

    }

    private void initMarker(List<EventModel> eventModelList) {

        for (int i = 0; i < eventModelList.size(); i++) {
            EventModel eventModel = eventModelList.get(i);

            LatLng latLng = new LatLng(eventModel.getLatitude(), eventModel.getLongitude());
            Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(eventModel.getNama()));
            marker.setTag(i);
            markerList.add(marker);
            latLngList.add(latLng);


        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.e("Marker", marker.getTag().toString());
        viewPager.setCurrentItem(Integer.parseInt(marker.getTag().toString()), true);
        return false;
    }
}
