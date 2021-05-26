package com.example.empty_can;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.empty_can.ATask.RestrantAddr;
import com.example.empty_can.DTO.RestMenuInfoDTO;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.content.Context.LOCATION_SERVICE;

public class MapFragment extends Fragment implements MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener, MapView.POIItemEventListener {

    private static final String TAG = "main:MapFragment";

    public double lad;
    public double gt;

    public static MapPoint CUSTOM_MARKER_POINT;     //= MapPoint.mapPointWithGeoCoord(lad, gt);

    private MapView map_view;
    RestrantAddr rsaddr = new RestrantAddr();

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};


    private MapPOIItem CustomMarker;

    AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup View = (ViewGroup) inflater.inflate(R.layout.map_fragment, container, false );

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

        map_view = View.findViewById(R.id.daum_map_view);
        //mMapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
        map_view.setCurrentLocationEventListener(this);
        //map_view.setMapViewEventListener(this);
        map_view.setPOIItemEventListener(this);

        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        }else {

            checkRunTimePermission();
        }

        /*       createCustomMarker(map_view, lad, gt, k);*/
        createCustomMarker(map_view, pps);

        return View;
    }

    @Override
    public void onDestroy() {
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
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])) {

                    Toast.makeText(getActivity().getBaseContext(), "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    getActivity().finish();


                }else {

                    Toast.makeText(getActivity().getBaseContext(), "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(getActivity().getBaseContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {
            map_view.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])) {
                Toast.makeText(getActivity().getBaseContext(), "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }
    private void showDialogForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d(TAG, "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }
    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }

    private void createCustomMarker(MapView map_view, String[] pps) {

        for(int i = 0; i < pps.length; i++){
            String[] cusmaker = pps[i].split(",");

            String name = cusmaker[0].trim();
            Double lad = Double.parseDouble(cusmaker[1]);
            Double lod = Double.parseDouble(cusmaker[2]);
            String address = cusmaker[3];
            String tel = cusmaker[4];
            String menu = cusmaker[5];
            String imgpath = cusmaker[6];

            RestMenuInfoDTO restMenuInfoDTO = new RestMenuInfoDTO(imgpath, name, address, tel, menu );

            Log.d(TAG, "createCustomMarker: 가게 이름" + cusmaker[0].trim());
            Log.d(TAG, "createCustomMarker: 위도" + lad);
            Log.d(TAG, "createCustomMarker: 경도" + lod);
            Log.d(TAG, "createCustomMarker: 주소" + address);
            Log.d(TAG, "createCustomMarker: 전번" + tel);
            Log.d(TAG, "createCustomMarker: 메뉴" + menu);
            Log.d(TAG, "createCustomMarker: 이미지경로" + imgpath);

            CUSTOM_MARKER_POINT = MapPoint.mapPointWithGeoCoord(lad, lod);

            CustomMarker = new MapPOIItem();
            CustomMarker.setUserObject(restMenuInfoDTO);
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

    }


    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        Toast.makeText(getActivity(), "Clicked야야야 " + mapPOIItem.getItemName(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCalloutBalloonOfPOIItemTouched 맵뷰: " + mapView + "포인트" + mapPOIItem);

        RestMenuInfoDTO restMenuInfoDTO = (RestMenuInfoDTO) mapPOIItem.getUserObject();
        popupImgXml(restMenuInfoDTO);
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    private void popupImgXml(RestMenuInfoDTO restMenuInfoDTO) {
        // 1. xml 파일 생성
        // 2. 화면을 inflate 시켜 setView 한다.

        String menus[] = restMenuInfoDTO.getMenu().split("/");

        // 팝업창에 xml 붙이기
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.popup, null);
        //view.setBackground(R.drawable.radius);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView tvTitle = view.findViewById(R.id.title);
        TextView tvAddr = view.findViewById(R.id.address);
        TextView tvTel = view.findViewById(R.id.tel);
        TextView tvMenu = view.findViewById(R.id.menu);

        imageView.setPadding(20, 0, 20, 0);
        tvTitle.setPadding(20, 0, 20, 0);
        tvAddr.setPadding(20, 0, 20, 0);
        tvTel.setPadding(20, 0, 20, 0);
        tvMenu.setPadding(20, 0, 20, 0);
        tvTitle.setTextSize(25);


        Log.d(TAG, "popupImgXml: 이미지경로 => " + restMenuInfoDTO.getImgPath());

        URL url = null;
        try {
            url = new URL(restMenuInfoDTO.getImgPath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //imageView.setImageResource(R.drawable.gugbab);   //restMenuInfoDTO.getImgPath()
        Glide.with(this).load(url).into(imageView);
        tvTitle.setText(restMenuInfoDTO.getTitle());
        tvAddr.setText(restMenuInfoDTO.getAddress());
        tvTel.setText(restMenuInfoDTO.getTel());
        for(int i=0; i<menus.length; i++){
            tvMenu.append(menus[i] + "\n");
        }

        //알람창 띄우기
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("음식점 상세정보").setView(view);

        //
        builder.setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog != null){
                    dialog.dismiss();
                }
            }
        });
        dialog = builder.create();
        dialog.show();

        //디바이스 사이즈 구하기
        Point size = getDeviceSize();

        // 디바이스 사이즈를 받아 팝업크기창을 조절
        int sizeX = size.x;
        int sizeY = size.y;

        // AlertDiaLog에서 위치, 크기 조절
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.x = (int) Math.round(sizeX * 0.005);      // 창의 시작 x 위치
        params.y = (int) Math.round(sizeY * 0.01);      // 창의 시작 y 위치
        params.width = (int) Math.round(sizeX * 0.9);   // 창의 넓이
        params.height = (int) Math.round(sizeY * 0.8);  // 창의 높이

        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.radius);
    }

    // 디바이스 가로 세로 사이즈 구하기
    // getRealSize()는 status bar 등 system instet을
    // 포함한 스크린사이즈를 가져오는 방법이고,
    // getSize()는 status bar 등 system insets을
    // 제외한 부분에 대한 사이즈를 가져오는 함수입니다.
    // 단위는 픽셀
    private Point getDeviceSize() {

        // 액티비티에서 가져올 때
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        //프레그먼트에서 가져올 때
        //Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);  //or getSize(size) : API 17 이상부터
        int width = size.x;     // 디바이스 넓이
        int height = size.y;    // 디바이스 높이

        return size;
    }

}
