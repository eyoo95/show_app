package com.luvris2.publicperfomancedisplayapp.fragment;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.luvris2.publicperfomancedisplayapp.MainActivity;
import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.model.Place;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private MarkerOptions markerOptions;

    LocationManager locationManager;
    LocationListener locationListener;

    String strGpsX;
    String strGpsY;

    double gpsX;
    double gpsY;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        //ui find
        findViewByidFunction(rootView);

        //mapview 설정
        initMapview(savedInstanceState);

//        locationManager = (LocationManager)getActivity().getSystemService(LOCATION_SERVICE);
//
//        locationListener = new LocationListener() {
//            // GPS의 정보를 얻어 올 수 있는 메소드
//            @Override
//            public void onLocationChanged(@NonNull Location location) {
//                gpsX = location.getLatitude();
//                gpsY = location.getLongitude();
//                Log.i("myLocation create", "위도 : " + gpsX);
//                Log.i("myLocation create", "경도 : " + gpsY);
//            }
//        };
//        // 권한 Permission 체크
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(getActivity(),
//                    new String[]{ Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.ACCESS_COARSE_LOCATION} ,
//                    100);
//
//        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
//                3000, -1,
//                locationListener);

        // check Condition
        // 현재 데이터를 가져오고 있지 않음.
        openFragment();

        if (getArguments() != null){
            String strGpsX = getArguments().getString("gpsX");
            String strGpsY = getArguments().getString("gpsY");

            gpsX = new Double(strGpsX);
            gpsY = new Double(strGpsY);

            Log.i("myLocation send", "위도 : " + gpsX);
            Log.i("myLocation send", "경도 : " + gpsY);
        }


        //이벤트 등록
        return rootView;
    }

    private void openFragment(){

        Bundle bundleX = new Bundle();
        Bundle bundleY = new Bundle();

        bundleX.putString("gpsX", strGpsX);
        bundleY.putString("gpsY", strGpsY);

        Fragment fragment = new MapFragment();

        fragment.setArguments(bundleX);
        fragment.setArguments(bundleY);
    }

    //mapview 설정
    private void initMapview(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

    }

    //ui find
    private void findViewByidFunction(View rootView) {
        mapView = rootView.findViewById(R.id.fragmentMap);

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        // todo : 이 코드에서 좌표가 먼저 찍히고 onCreateView에서 좌표가 나중에 찍히는 것은
        // todo : 실행 순서의 문제일까? 아니면 불러오는 속도가 차이가 나서 일까?
        // todo : 만약 그렇다면 다시 불러올 때마다 좌표가 업데이트 되야하는것은 아닌지 알아보자.

        LatLng position = new LatLng(gpsX, gpsY);
        Log.i("onmapready test", ""+gpsX);
        markerOptions = new MarkerOptions();
        markerOptions.position(position);
        markerOptions.title("현재 위치");
        markerOptions.snippet("내 위치");
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16));
        Log.i("myLocation check 2", "위도 : " + gpsX);
        Log.i("myLocation check 2", "경도 : " + gpsY);

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //마커 지우고 클릭한 위치로 마커 옮기기 위한 문장
                //googleMap.clear();
                markerOptions.position(latLng);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                googleMap.addMarker(markerOptions);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {

            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        100);
                return;
            }
        }
    }
}