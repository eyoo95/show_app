package com.luvris2.publicperfomancedisplayapp.fragment;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.luvris2.publicperfomancedisplayapp.MainActivity;
import com.luvris2.publicperfomancedisplayapp.R;

import java.lang.reflect.Array;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    // 지도와 마커를 위한 변수 선언
    private MarkerOptions markerOptions;

    // GPS 사용을 위한 멤버 변수 선언
    double gpsX, gpsY;

    // GPS 위치 정보 수신의 알림을 위한 프로그레스 바
    ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        showProgressBar();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        // GPS 현재 위치 정보 호출
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                // 번들 키 값 입력
                gpsX = bundle.getDouble("gpsLat");
                gpsY = bundle.getDouble("gpsLng");
                dismissProgressBar();
            }
        });

        LatLng position = new LatLng(gpsX, gpsY);
        Log.i("MyTestonMapReady", ""+gpsX);
        markerOptions = new MarkerOptions();
        markerOptions.position(position);
        markerOptions.title("현재 위치");
        markerOptions.snippet("내 위치");
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 18));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //마커 지우고 클릭한 위치로 마커 옮기기 위한 문장
                //googleMap.clear();
                markerOptions.position(latLng);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19));
                googleMap.addMarker(markerOptions);
            }
        });
    }

    // 위치 정보 수신 대기를 위한 프로그레스 바
    public void showProgressBar() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("GPS 정보 수신 중...");
        progressDialog.setMessage("GPS 정보를 수신중입니다. 잠시만 기다려주세요.");
        progressDialog.show();
    }

    public void dismissProgressBar() {
        progressDialog.dismiss();
    }

    // GPS 사용자 권한 설정
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                return;
            }
        }
    }
}