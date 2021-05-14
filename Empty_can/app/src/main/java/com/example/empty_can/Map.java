package com.example.empty_can;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.daum.mf.map.api.MapView;

public class Map extends Fragment {
    MainActivity activity;
    Button btnmap;
    private MapView mapView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup View = (ViewGroup) inflater.inflate(R.layout.map, container,false);

        activity = (MainActivity) getActivity();

        /*지도 보이기*/
       /* mapView = new MapView(activity);
        ViewGroup mapViewContainer = View.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);*/

        btnmap = View.findViewById(R.id.btnmap);

        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
            }
        });

        return View;
    }


    public void setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode trackingMode) {

    }


}
