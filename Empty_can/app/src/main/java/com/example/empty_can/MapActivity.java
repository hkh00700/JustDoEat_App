package com.example.empty_can;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.empty_can.ATask.RestrantAddr;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MapActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener {

    private static final String TAG = "MapActivity";

    public double lad;
    public double gt;

    public static MapPoint CUSTOM_MARKER_POINT;     //= MapPoint.mapPointWithGeoCoord(lad, gt);

    private MapView map_view;
    RestrantAddr rsaddr = new RestrantAddr();

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};


    private MapPOIItem CustomMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        String pp = "";

        RestrantAddr rsaddr = new RestrantAddr();
        try {
            pp = rsaddr.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<String> list = new ArrayList<>();

        String[] pps = pp.split("<br/>");




        Log.d(TAG, "onCreate: list" + pps);

/*
        String k = "";
        String j = "";
        String n = "";

        String[] dd = pp.split(",");

        Log.d(TAG, "onCreate: name : " + dd);

        for(int i = 0; i <dd.length; i++){

            Log.d(TAG, "onCreate: ????????? " + dd[i].trim() + i);
        }

        k = dd[0].trim();
        j = dd[1].trim();
        n = dd[2].trim();

        Log.d(TAG, "onCreate: ?????????" + j.trim());

        lad = Double.parseDouble(j);
        gt = Double.parseDouble(n);*/


        map_view = findViewById(R.id.daum_map_view);
        //mMapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
        map_view.setCurrentLocationEventListener(this);

        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        }else {

            checkRunTimePermission();
        }

 /*       createCustomMarker(map_view, lad, gt, k);*/
        createCustomMarker(map_view, pps);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        map_view.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        map_view.setShowCurrentLocationMarker(false);
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {
        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
        Log.i(TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
    }


    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        mapReverseGeoCoder.toString();
        onFinishReverseGeoCoding(s);
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
        onFinishReverseGeoCoding("Fail");
    }

    private void onFinishReverseGeoCoding(String result) {
//        Toast.makeText(LocationDemoActivity.this, "Reverse Geo-coding : " + result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {


            boolean check_result = true;

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {
                Log.d("@@@", "start");
                map_view.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
            }
            else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                    Toast.makeText(MapActivity.this, "???????????? ?????????????????????. ?????? ?????? ???????????? ???????????? ??????????????????.", Toast.LENGTH_LONG).show();
                    finish();


                }else {

                    Toast.makeText(MapActivity.this, "???????????? ?????????????????????. ??????(??? ??????)?????? ???????????? ???????????? ?????????. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission(){

        //????????? ????????? ??????
        // 1. ?????? ???????????? ????????? ????????? ???????????????.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {
            map_view.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapActivity.this, REQUIRED_PERMISSIONS[0])) {
                Toast.makeText(MapActivity.this, "??? ?????? ??????????????? ?????? ?????? ????????? ???????????????.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(MapActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(MapActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
        builder.setTitle("?????? ????????? ????????????");
        builder.setMessage("?????? ???????????? ???????????? ?????? ???????????? ???????????????.\n"
                + "?????? ????????? ???????????????????");
        builder.setCancelable(true);
        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //???????????? GPS ?????? ???????????? ??????
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS ????????? ?????????");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }
    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void createCustomMarker(MapView map_view, String[] pps) {

        for(int i = 0; i < pps.length; i++){
           String[] cusmaker = pps[i].split(",");
           Double lad = Double.parseDouble(cusmaker[1]);
           Double lod = Double.parseDouble(cusmaker[2]);

            Log.d(TAG, "createCustomMarker: ?????? ??????" + cusmaker[0].trim());
            Log.d(TAG, "createCustomMarker: ??????" + lad);
            Log.d(TAG, "createCustomMarker: ??????" + lod);

            CUSTOM_MARKER_POINT = MapPoint.mapPointWithGeoCoord(lad, lod);

            CustomMarker = new MapPOIItem();
            String name = cusmaker[0].trim();
            CustomMarker.setItemName(name);
            CustomMarker.setTag(1);
            CustomMarker.setMapPoint(CUSTOM_MARKER_POINT);

            CustomMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);

            CustomMarker.setCustomImageResourceId(R.drawable.pin);
            CustomMarker.setCustomImageAutoscale(false);
            CustomMarker.setCustomImageAnchor(0.5f, 1.0f);

            map_view.addPOIItem(CustomMarker);
            map_view.selectPOIItem(CustomMarker, true);
            map_view.setMapCenterPoint(CUSTOM_MARKER_POINT, false);
        }

       /* CUSTOM_MARKER_POINT = MapPoint.mapPointWithGeoCoord(lad, gt);

        CustomMarker = new MapPOIItem();
        String name = k;
        CustomMarker.setItemName(name);
        CustomMarker.setTag(1);
        CustomMarker.setMapPoint(CUSTOM_MARKER_POINT);

        CustomMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);

        CustomMarker.setCustomImageResourceId(R.drawable.pin);
        CustomMarker.setCustomImageAutoscale(false);
        CustomMarker.setCustomImageAnchor(0.5f, 1.0f);

        map_view.addPOIItem(CustomMarker);
        map_view.selectPOIItem(CustomMarker, true);
        map_view.setMapCenterPoint(CUSTOM_MARKER_POINT, false);
*/


      /*  CustomMarker = new MapPOIItem();
        String name1 = "?????????????????????";
        CustomMarker.setItemName(name1);
        CustomMarker.setTag(1);
        CustomMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(35.152847,126.88861705708976));

        CustomMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);

        CustomMarker.setCustomImageResourceId(R.drawable.pin);
        CustomMarker.setCustomImageAutoscale(false);
        CustomMarker.setCustomImageAnchor(0.5f, 1.0f);

        map_view.addPOIItem(CustomMarker);
        map_view.selectPOIItem(CustomMarker, true);
        map_view.setMapCenterPoint(CUSTOM_MARKER_POINT, false);*/



    }


}

