package com.luvris2.publicperfomancedisplayapp.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.ui.IconGenerator;
import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.api.KopisPerformanceApi;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.model.KopisApiPerformance;
import com.luvris2.publicperfomancedisplayapp.ui.MainActivity;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    // GPS 사용을 위한 멤버 변수 선언
    LatLng myPosition;
    String mySidoSubCode;

    // 플로팅 액션바
    FloatingActionButton fab;

    // GPS 위치 정보 수신의 알림을 위한 프로그레스 바
    ProgressDialog progressDialog;

    // 내 위치 주변 공연 정보
    ArrayList<KopisApiPerformance> nearByPerformanceList;
    // 내 위치 주변 공연 상세 정보
    KopisApiPerformance nearByPerformanceDetail;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_map, container, false);

        fab = rootView.findViewById(R.id.floatingActionButton);

        showProgressBar("위치 정보를 확인중입니다. 잠시만 기다려주세요.");

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(@NonNull final GoogleMap googleMap) {
        // todo : (GoogleMaps API) 현재 나의 위치 정보 호출
        myPosition = ((MainActivity) getActivity()).getLocation();

        dismissProgressBar();

        // 내 위치 지도에 표시
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 16));
        googleMap.addMarker(new MarkerOptions().position(myPosition).title("내 위치")).showInfoWindow();

        // todo : (Kopis API) 내 부근 공연 검색을 위한 지역 코드로 변환
        mySidoSubCode = ((MainActivity) getActivity()).getMySidoLocation(myPosition);
        Log.i("MyTest onMapReady", "mySidoSubCode " + mySidoSubCode);

        // todo : (Kopis API) 내 지역 구 진행중인 공연 검색
        nearByPerformanceList = ((MainActivity)getActivity()).nearByPerformanceSearch(mySidoSubCode);
        Log.i("MyTest onMapReady", "시설명 " + nearByPerformanceList.get(0).getPrfName());

        // todo : (Kopis API) 내 부근 공연 시설 표시
        nearbyPerformanceMap(googleMap);

        // 특정 마커 클릭시 해당 공연의 자세한 정보 간이 뷰에 표시
        googleMap.setOnMarkerClickListener(this);

        // todo : 플로팅 액션 바 클릭시 내 위치 정보를 찾고 해당 위치로 이동
        fab.setOnClickListener(view -> {
            showProgressBar("내 위치를 확인중입니다. 잠시만 기다려주세요.");
            myPosition = ((MainActivity)getActivity()).getLocation();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 16));
            googleMap.addMarker(new MarkerOptions().position(myPosition).title("내 위치"));
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
        } else {
            Toast.makeText(getActivity(), "현재 내 지역(구)에 진행중인 공연이 없습니다.", Toast.LENGTH_LONG).show();
        }
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
        Toast.makeText(getActivity(), ""+marker.getTitle(), Toast.LENGTH_LONG).show();

        // 특정 마커의 공연 ID 저장
        String markerPerformanceId = null;

        // 다이얼로그 객체 생성
        AlertDialog.Builder dialogAddRating = new AlertDialog.Builder(getActivity());

        // 레이아웃 xml 뷰와 연결 설정
        View performanceDetailView = View.inflate(getActivity(), R.layout.map_detail_performance, null);
        dialogAddRating.setView(performanceDetailView);

        // 각각의 UI를 객체화하고 연결
        ImageView imgMapPerformancePoster = performanceDetailView.findViewById(R.id.imgMapPerformancePoster);
        TextView txtMapPerformanceTitle = performanceDetailView.findViewById(R.id.txtMapPerformanceTitle);
        TextView txtMapPerformanceDate = performanceDetailView.findViewById(R.id.txtMapPerformanceDate);
        TextView txtMapPerformancePlace = performanceDetailView.findViewById(R.id.txtMapPerformancePlace);
        TextView txtMapPerformanceCast = performanceDetailView.findViewById(R.id.txtMapPerformanceCast);
        TextView txtMapPerformanceCrew = performanceDetailView.findViewById(R.id.txtMapPerformanceCrew);
        TextView txtMapPerformanceRuntime = performanceDetailView.findViewById(R.id.txtMapPerformanceRuntime);
        TextView txtMapPerformanceAge = performanceDetailView.findViewById(R.id.txtMapPerformanceAge);
        TextView txtMapPerformanceEnter = performanceDetailView.findViewById(R.id.txtMapPerformanceEnter);
        TextView txtMapPerformanceGenre = performanceDetailView.findViewById(R.id.txtMapPerformanceGenre);
        TextView txtMapPerformancTime = performanceDetailView.findViewById(R.id.txtMapPerformancTime);

        // 해당 마커의 공연 ID 확인
        for (int i=0; i<nearByPerformanceList.size(); i++) {
            if ( nearByPerformanceList.get(i).getPrfName().equals(marker.getTitle()) ) {
                markerPerformanceId = nearByPerformanceList.get(i).getPrfId();
                Log.i("MyTest Map MarkerId", "" + markerPerformanceId );
            }
        }

        // retrofit 설정
        Retrofit retrofit = NetworkClient.getRetrofitClient(getActivity());
        KopisPerformanceApi api = retrofit.create(KopisPerformanceApi.class);
        Call<KopisApiPerformance> call = api.getPerformanceDetail(markerPerformanceId);

        // Retrofit 값을 바로 저장하기 위한 동기 처리
        new Thread(() -> {
            try {
                nearByPerformanceDetail = call.execute().body().getResult();
                Log.i("MyTest Map ClickMarker", "" + nearByPerformanceDetail );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // API 응답에 따른 약간의 대기 시간 설정
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 해당 공연 정보 표시
        Glide.with(getActivity()).load(nearByPerformanceDetail.getPosterUrl())
                .placeholder(R.drawable.ic_image_not_supported).fitCenter().into(imgMapPerformancePoster);
        txtMapPerformanceTitle.setText(nearByPerformanceDetail.getPrfName());
        txtMapPerformanceDate.setText(nearByPerformanceDetail.getPrfpdfrom() + " ~ " + nearByPerformanceDetail.getPrfpdto());
        txtMapPerformancePlace.setText("공연시설:"+nearByPerformanceDetail.getPrfPlace());
        txtMapPerformanceCast.setText("출연진:"+nearByPerformanceDetail.getPrfcast());
        txtMapPerformanceCrew.setText("제작진:"+nearByPerformanceDetail.getPrfcrew());
        txtMapPerformanceRuntime.setText("공연런타임:"+nearByPerformanceDetail.getPrfruntime());
        txtMapPerformanceAge.setText("공연관람연령:"+nearByPerformanceDetail.getPrfage());
        txtMapPerformanceEnter.setText("제작사:"+nearByPerformanceDetail.getEntrpsnm());
        txtMapPerformanceGenre.setText("장르:"+nearByPerformanceDetail.getPrfGenre());
        txtMapPerformancTime.setText("공연시간:"+nearByPerformanceDetail.getDtguidance());

        // 확인을 누르면 실행 될 코드 작성
        dialogAddRating.setPositiveButton("확인", (dialogInterface, i) -> {
            // 확인시 실행 코드 작성
        });
        // 다이얼로그 유저에게 출력
        dialogAddRating.show();
        return false;
    }
}