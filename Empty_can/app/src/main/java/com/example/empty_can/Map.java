package com.example.empty_can;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.daum.mf.map.api.MapView;


public class Map extends Fragment {
    MainActivity activity;
    MapFragment mapFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup View = (ViewGroup) inflater.inflate(R.layout.map, container,false);

        activity = (MainActivity) getActivity();

        mapFragment = new MapFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.contain, mapFragment).commit();


        return View;
    }


    public void setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode trackingMode) {

    }


}
