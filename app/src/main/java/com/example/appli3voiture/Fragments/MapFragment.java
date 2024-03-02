package com.example.appli3voiture.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.appli3voiture.Interfaces.CallbackMap;
import com.example.appli3voiture.Logic.SharedPreferencesManager;
import com.example.appli3voiture.Model.Score;
import com.example.appli3voiture.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private CallbackMap callbackMap;
    private SupportMapFragment supportMapFragment;
    SharedPreferencesManager spm;
    private SupportMapFragment mapFragment; //google map

    private AppCompatActivity activity;
    private TextView _LBL_details;
    private FrameLayout map;
    private LatLng point;
    private GoogleMap mMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_map, container, false);
        //findViews(view);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            FragmentManager fm = getChildFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }
        callbackMap = new CallbackMap() {
            @Override
            public void changeLocation(double lon, double lat) {

                pointOnMap(lat,lon);
            }
        };
        mapFragment.getMapAsync(this);
        return view;
    }


    public CallbackMap getCallbackMap() {
        return callbackMap;
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.mMap = googleMap;

    }

    public void pointOnMap(double x, double y) {
        if(mMap!= null){
        point = new LatLng(x, y);
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(point).title(""));
        moveToCurrentLocation(point);
        System.out.println( ""+x+","+  y);
        }
    }

    private void moveToCurrentLocation(LatLng currentLocation) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);

    }

}
