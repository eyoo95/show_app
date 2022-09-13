package com.luvris2.publicperfomancedisplayapp.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.ui.IconGenerator;
import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.api.KopisPerformanceApi;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.model.KopisApiPerformance;
import com.luvris2.publicperfomancedisplayapp.ui.MainActivity;
import com.luvris2.publicperfomancedisplayapp.ui.PerformanceInfoActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    // GPS 사용을 위한 멤버 변수 선언
    LatLng myPosition = null;
    String mySidoSubCode;

    // 플로팅 액션바
    FloatingActionButton fab, fabZoomIn, fabZoomOut;
    // 지도 확대/축소를 위한 변수
    int zoomIndex = 16;

    // GPS 위치 정보 수신의 알림을 위한 프로그레스 바
    ProgressDialog progressDialog;

    // 내 위치 주변 공연 정보
    ArrayList<KopisApiPerformance> nearByPerformanceList;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_map, container, false);



        fab = rootView.findViewById(R.id.floatingActionButton);
        fabZoomIn = rootView.findViewById(R.id.float_zoom_in);
        fabZoomOut = rootView.findViewById(R.id.float_zoom_out);

        showProgressBar("내 위치 정보를 받는 중입니다. 조금만 기다려주세요...");

        // todo : (GoogleMaps API) 현재 나의 위치 정보 호출
        // 가장 최근에 사용하였던 위치 정보 이용하여 우선적으로 비교적 덜 정확한 위치 표시
        myPosition = ((MainActivity) getActivity()).getLastLocation();

        if (myPosition != null) {
            // todo : (Kopis API) 내 부근 공연 검색을 위한 지역 코드로 변환
            mySidoSubCode = ((MainActivity) getActivity()).getMySidoLocation(myPosition);
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            Log.i("MyTest onMapReady", "mySidoSubCode " + mySidoSubCode);
        }

        return rootView;
    }

    @Override
    public void onMapReady(@NonNull final GoogleMap googleMap) {
        dismissProgressBar();

        // 내 위치 지도에 표시
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 16));
        googleMap.addMarker(new MarkerOptions().position(myPosition).title("내 위치")).showInfoWindow();

        // todo : (Kopis API) 내 지역 구 진행중인 공연 검색 후 지도에 표시
        nearByPerformanceSearch(mySidoSubCode, googleMap);

        // 특정 마커 클릭시 해당 공연의 자세한 정보 간이 뷰에 표시
        googleMap.setOnMarkerClickListener(this);

        // todo : 플로팅 액션 바 클릭시 내 위치 정보를 찾고 해당 위치로 이동
        fab.setOnClickListener(view -> {
            myPosition = ((MainActivity)getActivity()).getLocation();
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition, zoomIndex));
            googleMap.addMarker(new MarkerOptions().position(myPosition).title("내 위치"));
            // todo : (Kopis API) 내 지역 구 진행중인 공연 검색 후 지도에 표시
            nearByPerformanceSearch(mySidoSubCode, googleMap);
        });

        // todo : 지도 확대
        fabZoomIn.setOnClickListener(view -> {
            zoomIndex += 1;
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition, zoomIndex));
            googleMap.addMarker(new MarkerOptions().position(myPosition).title("내 위치"));
        });

        // todo : 지도 축소
        fabZoomOut.setOnClickListener(view -> {
            zoomIndex -= 1;
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition, zoomIndex));
            googleMap.addMarker(new MarkerOptions().position(myPosition).title("내 위치"));
        });
    }

    // 내 지역(구) 공연 찾기
    public void nearByPerformanceSearch(String sidoCodeSub, GoogleMap googleMap) {
        Log.i("MyTest nearByPlace sido", ""+ sidoCodeSub);
        showProgressBar("내 지역(구군)의 진행중인 공연 찾는 중...");
        // 네트워크로 데이터 전송, Retrofit 객체 생성
        Retrofit retrofit = NetworkClient.getRetrofitClient(getActivity());
        KopisPerformanceApi api = retrofit.create(KopisPerformanceApi.class);
        Call<KopisApiPerformance> call = (Call<KopisApiPerformance>) api.nearByPlaceSearch(sidoCodeSub, getCurrentTime(), getCurrentTime(), 1, 999, 2);
        call.enqueue(new Callback<KopisApiPerformance>() {
            @Override // 성공했을 때
            public void onResponse(Call<KopisApiPerformance> call, Response<KopisApiPerformance> response) {
                // 200 OK 일 때,
                if (response.isSuccessful()) {
                    nearByPerformanceList = response.body().getResultList();

                    if (nearByPerformanceList != null) {
                        Toast.makeText(getActivity(), "내 지역(구군)의 진행중인 공연 수 : " +nearByPerformanceList.size(), Toast.LENGTH_LONG).show();
                        Log.i("MyTest onMapReady", "시설명 " + nearByPerformanceList.get(0).getPrfName());
                        // todo : (Kopis API) 내 부근 공연 시설 표시
                        nearbyPerformanceMap(googleMap);
                    } else {
                        Toast.makeText(getActivity(), "현재 내 지역(구)에 진행중인 공연이 없습니다.", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "에러 발생 : " + response.code(), Toast.LENGTH_SHORT).show();
                }
                dismissProgressBar();
            }
            @Override // 실패했을 때
            public void onFailure(Call<KopisApiPerformance> call, Throwable t) {
                // 네트워크 자체 문제로 실패!
                dismissProgressBar();
            }
        });
    }

    // 내 지역(구) 공연중인 시설 지도에 표시
    public void nearbyPerformanceMap(GoogleMap googleMap) {
        if (nearByPerformanceList.size() > 0) {
            Log.i("MyTest onMapReady", "진행중인 공연수 " + nearByPerformanceList.size());
            for (int i=0; i<nearByPerformanceList.size(); i++){
                LatLng nearByPlacePosition = new LatLng(Double.parseDouble(nearByPerformanceList.get(i).getLatitude()), Double.parseDouble(nearByPerformanceList.get(i).getLongitude()));
                IconGenerator iconFactory = new IconGenerator(getActivity());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(nearByPlacePosition)
                        .title(nearByPerformanceList.get(i).getPrfName())
                        .snippet(nearByPerformanceList.get(i).getPrfPlace());
                googleMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(nearByPerformanceList.get(i).getPrfName())));
            }
        }
    }

    // todo : 현재 시간을 구하는 메소드
    public String getCurrentTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(date);
    }

    // todo : 위치 정보 수신 대기를 위한 프로그레스 다이얼로그
    public void showProgressBar(String message) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("정보 수신 중...");
        progressDialog.setMessage(message);
        progressDialog.show();
    }
    public void dismissProgressBar() {
        progressDialog.dismiss();
    }

    // 마커 클릭시 해당 공연의 상세 정보 간이 뷰에 출력
    @SuppressLint("SetTextI18n")
    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        // 특정 마커의 공연 ID 저장
        String markerPerformanceId = null;

        // 해당 마커의 공연 ID 확인
        for (int i=0; i<nearByPerformanceList.size(); i++) {
            if ( nearByPerformanceList.get(i).getPrfName().equals(marker.getTitle()) ) {
                // 인덱스의 저장된 데이터 호출
                markerPerformanceId = nearByPerformanceList.get(i).getPrfId();
                Log.i("MyTest Map MarkerId", "" + markerPerformanceId );

                // 액티비티로 데이터 전달
                Intent intent = new Intent(getActivity(), PerformanceInfoActivity.class);
                intent.putExtra("mt20id",markerPerformanceId);
                getActivity().startActivity(intent);
            }
        }
        return false;
    }
}