package com.luvris2.publicperfomancedisplayapp.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.ui.IconGenerator;
import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.api.GoogleMapApi;
import com.luvris2.publicperfomancedisplayapp.api.KopisPerformanceApi;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.config.Config;
import com.luvris2.publicperfomancedisplayapp.model.GoogleMapPlaceSearch;
import com.luvris2.publicperfomancedisplayapp.model.KopisApiPerformance;
import com.luvris2.publicperfomancedisplayapp.ui.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    // GPS 사용을 위한 멤버 변수 선언
    LatLng myPosition;
    String mySidoCode;

    // 플로팅 액션바
    FloatingActionButton fab;

    // GPS 위치 정보 수신의 알림을 위한 프로그레스 바
    ProgressDialog progressDialog;

    // 공연 검색 키워드
    String prfName="", prfPlace="", prfGenreCode="";
    int prfState=2; // 2=공연중

    // 공연 검색 결과를 저장 할 배열
    ArrayList<KopisApiPerformance> performanceList = new ArrayList<>();
    KopisApiPerformance nearByPerformance;
    Bitmap prfPosterImg;

    // 공연 시설의 위도 경도를 저장 할 배열
    ArrayList<LatLng> nearByPlaceList = new ArrayList<>();

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
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 12));
        googleMap.addMarker(new MarkerOptions().position(myPosition).title("내 위치")).showInfoWindow();

        // todo : (Kopis API) 내 부근 공연 검색을 위한 지역 코드로 변환
        mySidoCode = ((MainActivity) getActivity()).getMySidoLocation(myPosition);
        Log.i("MyTest getSido", ""+mySidoCode);

        // todo : (Kopis API) 내 부근 진행중인 공연 검색
        nearByPerformanceData(prfName, prfPlace, prfGenreCode, mySidoCode, prfState);

        // todo : (Kopis API) 내 부근 공연 시설 표시
        nearByPerformancePlaceSearch();

        // todo : 공연 포스터를 맵에 표시
        nearByPerformanceMarker(googleMap);

        // todo : 플로팅 액션 바 클릭시 내 위치 정보를 찾고 해당 위치로 이동
        fab.setOnClickListener(view -> {
            //showProgressBar("내 위치를 확인중입니다. 잠시만 기다려주세요.");
            myPosition = ((MainActivity)getActivity()).getLocation();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 12));
            googleMap.addMarker(new MarkerOptions().position(myPosition).title("내 위치"));

            mySidoCode = ((MainActivity) getActivity()).getMySidoLocation(myPosition);
            nearByPerformanceData(prfName, prfPlace, prfGenreCode, mySidoCode, prfState);
            //dismissProgressBar();
        });
    }

    // todo : 내 주위 공연 목록 확인
    void nearByPerformanceData(String prfName, String prfPlace, String prfGenreCode, String prfSidoCode, int prfState) {
        // 데이터 초기화
        performanceList.clear();

        // 1페이지, 20개씩 보여주기
        int cpage = 1;
        int rows = 2;

        // 현재 시간 불러오기
        String currentTime = getCurrentTime();

        showProgressBar("공연 목록 불러오는 중...");

        // 네트워크로 데이터 전송, Retrofit 객체 생성
        Retrofit retrofit = NetworkClient.getRetrofitClient(getActivity());
        KopisPerformanceApi api = retrofit.create(KopisPerformanceApi.class);

        // API 요청
        Call<KopisApiPerformance> call = api.getPlaceSearch(currentTime, currentTime, cpage, rows, prfName, prfPlace, prfGenreCode, prfSidoCode, prfState);

        // Retrofit 값을 바로 저장하기 위한 동기 처리
        new Thread(() -> {
            try {
                performanceList = call.execute().body().getResultList();
            } catch (java.lang.IllegalStateException arrayError) {
                Log.i("MyTest Error", ""+arrayError);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // API 응답에 따른 약간의 대기 시간 설정
        try {
            Thread.sleep(2000);
            dismissProgressBar();
        } catch (InterruptedException e) {
            e.printStackTrace();
            dismissProgressBar();
        }
        initKeyword(); // 검색 조건 초기화
    }

    // todo : 내 주위 공연 시설 위치 확인
    void nearByPerformancePlaceSearch() {
        showProgressBar("공연 시설 위치 확인 중...");

        // 네트워크로 데이터 전송, Retrofit 객체 생성
        Retrofit retrofit = NetworkClient.getRetrofitGoogleMaps(getActivity());
        GoogleMapApi api = retrofit.create(GoogleMapApi.class);

        for (int i=0; i< performanceList.size(); i++) {
            int finalI = i;
            // API 요청
            Call<GoogleMapPlaceSearch> call = api.nearByPerformanceLocation(performanceList.get(i).getPrfPlace(), "ko", Config.GOOGLE_MAPS_API_KEY);

            // Retrofit 값을 바로 저장하기 위한 동기 처리
            new Thread(() -> {
                try {
                    List<GoogleMapPlaceSearch> data = (List<GoogleMapPlaceSearch>) call.execute().body().getResults();
                    nearByPlaceList.add( new LatLng(data.get(0).getGeometry().getLocation().getLat(),
                            data.get(0).getGeometry().getLocation().getLng()));
                    Log.i("MyTest nearByPlace", ""+
                            performanceList.get(finalI).getPrfPlace()+ nearByPlaceList.get(finalI));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // API 응답에 따른 약간의 대기 시간 설정
            try {
                do {
                    Thread.sleep(1000);
                } while ((nearByPlaceList.get(finalI) == null));
            } catch (InterruptedException e) {
                e.printStackTrace();
                dismissProgressBar();
            }
        }
        dismissProgressBar();
        initKeyword(); // 검색 조건 초기화
    }

    // todo : 내 주변의 공연을 맵에 공연 포스터로 표시
    public void nearByPerformanceMarker(GoogleMap googleMap) {
        if (performanceList.size() > 0) {
            Log.i("MyTest onMapReady", "진행중인 공연수 " + performanceList.size());
            for (int i=0; i<performanceList.size(); i++){
                nearByPerformance = performanceList.get(i);
                Log.i("MyTest onMapReady", "시설명 " + performanceList.get(i).getPrfPlace());
                Log.i("MyTest", "poster"+nearByPerformance.getPosterUrl());

                // 공연 포스터 URL을 비트맵으로 변경
                new Thread(() -> {
                    try {
                        URL url_value = new URL(nearByPerformance.getPosterUrl());
                        prfPosterImg = BitmapFactory.decodeStream(url_value.openConnection().getInputStream());
//                        prfPosterImg.setWidth(100);
//                        prfPosterImg.setHeight(150);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

                // 네트워크 응답에 따른 약간의 대기 시간 설정
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 마커 위치, 제목, 부제목 설정
                Marker marker1 = googleMap.addMarker(new MarkerOptions().position(nearByPlaceList.get(i))
                        .title(nearByPerformance.getPrfPlace())
                        .snippet(nearByPerformance.getPrfName()));
                // 마커의 인덱스 설정
                marker1.setTag(i);
                // 마커를 포스터 이미지로 변경
                marker1.setIcon(BitmapDescriptorFactory.fromBitmap(prfPosterImg));
            }

        } else {
            Toast.makeText(getActivity(), "내 위치 근처에 진행중인 공연이 없습니다.", Toast.LENGTH_LONG).show();
        }
    }

    // todo : 현재 시간을 구하는 메소드
    public String getCurrentTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(date);
    }

    // 공연 조회 변수 초기화
    public void initKeyword() {
        prfName="";
        prfPlace="";
        prfGenreCode="";
        mySidoCode="";
        prfState=2;
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
}