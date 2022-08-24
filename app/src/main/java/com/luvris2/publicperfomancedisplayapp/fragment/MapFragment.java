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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.ui.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // GPS 사용을 위한 멤버 변수 선언
    LatLng myPosition;

    // 플로팅 액션바
    FloatingActionButton fab;

    // GPS 위치 정보 수신의 알림을 위한 프로그레스 바
    ProgressDialog progressDialog;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_map, container, false);

        fab = rootView.findViewById(R.id.floatingActionButton);

        showProgressBar();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);


        return rootView;
    }

    @Override
    public void onMapReady(@NonNull final GoogleMap googleMap) {
        myPosition = ((MainActivity)getActivity()).getLocation();
        dismissProgressBar();
        Log.i("MyTest onMapReady", "" + myPosition);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 18));
        googleMap.addMarker(new MarkerOptions().position(myPosition).title("내 위치"));

        // 플로팅 액션 바 클릭시 내 위치 정보를 찾고 해당 위치로 이동
        fab.setOnClickListener(view -> {
            showProgressBar();
            myPosition = ((MainActivity)getActivity()).getLocation();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 18));
            googleMap.addMarker(new MarkerOptions().position(myPosition).title("내 위치"));
            dismissProgressBar();
        });



//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(myPosition);
//        markerOptions.title("현재 위치");
//        markerOptions.snippet("내 위치");
//        googleMap.addMarker(markerOptions);
//        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                //마커 지우고 클릭한 위치로 마커 옮기기 위한 문장
//                //googleMap.clear();
//                markerOptions.position(latLng);
//                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19));
//                googleMap.addMarker(markerOptions);
//            }
//        });
    }


//    // [START maps_current_place_get_device_location]
//    private void getDeviceLocation() {
//        myPosition = ((MainActivity)getActivity()).getLocation();
//        locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
//            @Override
//            public void onComplete(@NonNull Task<Location> task) {
//                if (task.isSuccessful()) {
//                    // Set the map's camera position to the current location of the device.
//                    lastKnownLocation = task.getResult();
//                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 18));
//        });
//            }
//    }
//                        }
//    }

    // 위치 정보 수신 대기를 위한 프로그레스 다이얼로그
    public void showProgressBar() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("GPS 정보 수신 중...");
        progressDialog.setMessage("위치 정보를 확인중입니다. 잠시만 기다려주세요.");
        progressDialog.show();
    }
    public void dismissProgressBar() {
        progressDialog.dismiss();
    }
}