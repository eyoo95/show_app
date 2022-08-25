package com.luvris2.publicperfomancedisplayapp.fragment;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.widget.Toast;

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
import com.luvris2.publicperfomancedisplayapp.api.GoogleMapApi;
import com.luvris2.publicperfomancedisplayapp.api.KopisPerformanceApi;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.config.Config;
import com.luvris2.publicperfomancedisplayapp.model.GoogleMapPlaceSearch;
import com.luvris2.publicperfomancedisplayapp.model.KopisApiPerformance;
import com.luvris2.publicperfomancedisplayapp.ui.MainActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

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

    // 공연 시설의 위도 경도를 저장 할 배열
    ArrayList<LatLng> nearByPlaceList = new ArrayList<>();

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

        // todo : (Kopis API) 내 부근 진행중인 공연 검색
        nearByPerformanceData(prfName, prfPlace, prfGenreCode, mySidoCode, prfState);

        // todo : (Kopis API) 내 부근 공연 시설 표시
        nearByPerformancePlaceSearch();

        if (performanceList.size() > 0) {
            Log.i("MyTest onMapReady", "진행중인 공연수 " + performanceList.size());
            for (int i=0; i<performanceList.size(); i++){
                nearByPerformance = performanceList.get(i);
                Log.i("MyTest onMapReady", "시설명 " + performanceList.get(i).getPrfPlace());

                IconGenerator iconFactory = new IconGenerator(getActivity());
                Marker marker1 = googleMap.addMarker(new MarkerOptions().position(nearByPlaceList.get(i))
                        .title(nearByPerformance.getPrfPlace())
                        .snippet(nearByPerformance.getPrfName()));
                marker1.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(nearByPerformance.getPrfName())));
            }

        } else {
            Toast.makeText(getActivity(), "내 위치 근처에 진행중인 공연이 없습니다.", Toast.LENGTH_LONG).show();
        }

        // todo : 플로팅 액션 바 클릭시 내 위치 정보를 찾고 해당 위치로 이동
        fab.setOnClickListener(view -> {
            showProgressBar("내 위치를 확인중입니다. 잠시만 기다려주세요.");
            myPosition = ((MainActivity)getActivity()).getLocation();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 12));
            googleMap.addMarker(new MarkerOptions().position(myPosition).title("내 위치"));

            mySidoCode = ((MainActivity) getActivity()).getMySidoLocation(myPosition);
            nearByPerformanceData(prfName, prfPlace, prfGenreCode, mySidoCode, prfState);
            dismissProgressBar();
        });
    }

    // todo : 내 주위 공연 목록 확인
    void nearByPerformanceData(String prfName, String prfPlace, String prfGenreCode, String prfSidoCode, int prfState) {
        // 데이터 초기화
        performanceList.clear();

        // 1페이지, 20개씩 보여주기
        int cpage = 1;
        int rows = 5;

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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // API 응답에 따른 약간의 대기 시간 설정
        try {
            Thread.sleep(5000);
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