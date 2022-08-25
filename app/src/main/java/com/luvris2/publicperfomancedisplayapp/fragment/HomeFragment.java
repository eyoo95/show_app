package com.luvris2.publicperfomancedisplayapp.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.adapter.MyAdapter;
import com.luvris2.publicperfomancedisplayapp.adapter.PerformanceSearchAdapter;
import com.luvris2.publicperfomancedisplayapp.api.KopisPerformanceApi;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.model.KopisApiPerformance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    TextView txtPlace, txtType;


    // 뷰페이저2 관련 변수
    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 4;
    private CircleIndicator3 mIndicator;

    // 리사이클러 뷰 관련 변수
    RecyclerView recyclerView;
    PerformanceSearchAdapter adapter;
    ArrayList<KopisApiPerformance> performanceList = new ArrayList<>();

    // 프로그레스 다이얼로그
    private ProgressDialog dialog;

    // 공연 검색 키워드
    String prfName="", prfPlace="", prfGenre="", signgucode="";
    int prfState=2; // 2=공연중
    String[] signguList = {"서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종", "경기",
            "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주"};
    String[] genreList = {"연극", "뮤지컬" , "무용", "클래식", "오페라", "국악", "복합"};

    // 공연 검색 아이콘
    ImageView imgSearch;

    // 공연 검색을 위한 스피너
    Spinner spinner;
    int spinnerNumber = 0; // 0=지역별, 1=유형별

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 뷰 화면 설정
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        // UI 객체 생성
        txtPlace = rootView.findViewById(R.id.txtHomeSortRegion);
        txtType = rootView.findViewById(R.id.txtHomeSortType);
        txtPlace.setBackgroundColor(Color.parseColor("#DAFBFF"));
        imgSearch = rootView.findViewById(R.id.imgHomeSearch);

        // 리사이클러뷰 화면 설정
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        // ViewPager2 구현
        //ViewPager2 연결
        mPager = rootView.findViewById(R.id.viewpager);
        //Adapter 연결
        pagerAdapter = new MyAdapter(getActivity(), num_page);
        mPager.setAdapter(pagerAdapter);
        //Indicator 연결
        mIndicator = rootView.findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.createIndicators(num_page,0);
        //ViewPager Setting
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mPager.setCurrentItem(1000);
        mPager.setOffscreenPageLimit(3);


        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager.setCurrentItem(position);
                }
            }

            // 페이지 클릭했을 때
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mIndicator.animatePageSelected(position%num_page);
            }

        });

        final float pageMargin= getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);

        // 슬라이드 반복되도록
        mPager.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float myOffset = position * -(2 * pageOffset + pageMargin);
                if (mPager.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(mPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.setTranslationX(-myOffset);
                    } else {
                        page.setTranslationX(myOffset);
                    }
                } else {
                    page.setTranslationY(myOffset);
                }
            }
        });

        // 지역 선택을 위한 스피너 설정
        spinner = rootView.findViewById(R.id.spinner);

        // 지역별 스피너 설정
        ArrayAdapter<String> placeArrayAdapter = new ArrayAdapter<>
                (getActivity(), android.R.layout.simple_spinner_dropdown_item, signguList);
        placeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 유형별 스피너 설정
        ArrayAdapter<String> typeArrayAdapter = new ArrayAdapter<>
                (getActivity(), android.R.layout.simple_spinner_dropdown_item, genreList);
        typeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 스피너 화면 설정
        spinner.setAdapter(placeArrayAdapter);

        // 지역 선택에 따른 지역코드 입력
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinnerNumber == 0) { selectedPlaceData(i);
                } else if (spinnerNumber == 1 ) { selectedTypeData(i); }

                // 조건에 따른 공연 검색
                getPerformanceData( prfName, prfPlace, prfGenre, signgucode, 2);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        // todo : 지역별 공연 검색
        txtPlace.setOnClickListener(view -> {
            spinnerNumber = 0 ;
            txtPlace.setBackgroundColor(Color.parseColor("#DAFBFF"));
            txtType.setBackgroundColor(Color.parseColor("#ffffff"));
            spinner.setAdapter(placeArrayAdapter);
        });

        // todo : 유형별 공연 검색
        txtType.setOnClickListener(view -> {
            spinnerNumber = 1 ;
            txtType.setBackgroundColor(Color.parseColor("#DAFBFF"));
            txtPlace.setBackgroundColor(Color.parseColor("#ffffff"));
            spinner.setAdapter(typeArrayAdapter);
        });


        imgSearch.setOnClickListener(view -> {
            AlertDialog.Builder dialogAddRating = new AlertDialog.Builder(getActivity());

            // 다이얼로그 제목 설정
            dialogAddRating.setTitle("공연 상세 검색");

            // 레이아웃 xml 뷰와 연결 설정
            View performanceSearchView = (View) View.inflate(getActivity(), R.layout.dialog_search_layout, null);
            dialogAddRating.setView(performanceSearchView);

            // 분류별 스피너 설정, 지역별/장르별
            Spinner spinnerType = performanceSearchView.findViewById(R.id.spinnerType);
            Spinner spinnerRegion = performanceSearchView.findViewById(R.id.spinnerRegion);
            ArrayAdapter<String> searchTypeArrayAdapter = new ArrayAdapter<>
                    (getActivity(), android.R.layout.simple_spinner_dropdown_item, genreList);
            ArrayAdapter<String> searchPlaceArrayAdapter = new ArrayAdapter<>
                    (getActivity(), android.R.layout.simple_spinner_dropdown_item, signguList);
            searchTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            searchPlaceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // 스피너 화면 설정
            spinnerType.setAdapter(searchTypeArrayAdapter);
            spinnerRegion.setAdapter(searchPlaceArrayAdapter);

            // 지역 선택에 따른 지역코드 입력
            spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedTypeData(i);
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) { }
            });

            // 지역 선택에 따른 지역코드 입력
            spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedPlaceData(i);
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) { }
            });

            // 확인을 누르면 실행 될 코드 작성
            dialogAddRating.setPositiveButton("확인", (dialogInterface, i) -> {
                getPerformanceData( prfName, prfPlace, prfGenre, signgucode, 2);
            });

            // 취소를 누르면 실행 될 코드 작성
            dialogAddRating.setNegativeButton("취소", (dialogInterface, i) -> {
                // 취소시 실행 코드 작성
            });

            // 다이얼로그 유저에게 출력
            dialogAddRating.show();
        });
        return rootView;
    }

    // 스피너를 통한 지역 선택시 지역 코드 저장 메소드
    void selectedPlaceData(int i) {
        switch (signguList[i]) {
            case "서울":
                signgucode = "11";
                break;
            case "부산":
                signgucode = "26";
                break;
            case "대구":
                signgucode = "27";
                break;
            case "인천":
                signgucode = "28";
                break;
            case "광주":
                signgucode = "29";
                break;
            case "대전":
                signgucode = "30";
                break;
            case "울산":
                signgucode = "31";
                break;
            case "세종":
                signgucode = "36";
                break;
            case "경기":
                signgucode = "41";
                break;
            case "강원":
                signgucode = "42";
                break;
            case "충북":
                signgucode = "43";
                break;
            case "충남":
                signgucode = "44";
                break;
            case "전북":
                signgucode = "45";
                break;
            case "전남":
                signgucode = "46";
                break;
            case "경북":
                signgucode = "47";
                break;
            case "경남":
                signgucode = "48";
                break;
            case "제주":
                signgucode = "50";
                break;
        }
    }

    // 스피너를 통한 장르 선택시 장르 코드 저장 메소드
    void selectedTypeData(int i) {
        switch (genreList[i]) {
            case "연극":
                prfGenre = "AAAA";
                break;
            case "뮤지컬":
                prfGenre = "AAAB";
                break;
            case "무용":
                prfGenre = "BBBA";
                break;
            case "클래식":
                prfGenre = "CCCA";
                break;
            case "오페라":
                prfGenre = "CCCB";
                break;
            case "국악":
                prfGenre = "CCCC";
                break;
            case "복합":
                prfGenre = "EEEA";
                break;
        }
    }

    // todo : 공연 목록 확인
    void getPerformanceData(String prfName, String prfPlace, String prfGenre, String signgucode, int prfState) {
        // 데이터 초기화
        performanceList.clear();

        // 1페이지, 6개씩 보여주기
        int cpage = 1;
        int rows = 6;

        // 현재 시간 불러오기
        String currentTime = getCurrentTime();

        showProgress("공연 목록 불러오는 중...");

        // 네트워크로 데이터 전송, Retrofit 객체 생성
        Retrofit retrofit = NetworkClient.getRetrofitClient(getActivity());
        KopisPerformanceApi api = retrofit.create(KopisPerformanceApi.class);

        // 헤더에 설정 할 데이터 확인, 공유 저장소에 저장되어있는 토큰 호출
        // API 요청
        Call<KopisApiPerformance> call = api.getPlaceSearch(currentTime, currentTime, cpage, rows, prfName, prfPlace, prfGenre, signgucode, prfState);

        call.enqueue(new Callback<KopisApiPerformance>() {
            @Override
            public void onResponse(@NonNull Call<KopisApiPerformance> call, @NonNull Response<KopisApiPerformance> response) {
                dismissProgress();

                // 200 OK, 네트워크 정상 응답
                if(response.isSuccessful()) {
                    KopisApiPerformance data = response.body();

                    // 기존의 데이터에서 추가
                    if (data != null) { performanceList.addAll(data.getResultList()); }
                    else { performanceList.clear(); }
                    adapter = new PerformanceSearchAdapter(getActivity(), performanceList);
                    recyclerView.setAdapter(adapter);
                }
                // 진행중인 공연이 없을 경우 메시지 출력
                else if(response.code() == 500) {
                    Toast.makeText(getActivity(), "현재 진행중인 공연이 없습니다.", Toast.LENGTH_LONG).show();
                    performanceList.clear();
                    adapter = new PerformanceSearchAdapter(getActivity(), performanceList);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(@NonNull Call<KopisApiPerformance> call, @NonNull Throwable t) {
                dismissProgress();
            }
        });
        initKeyword(); // 검색 조건 초기화
    }

    // 현재 시간을 구하는 메소드
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
        prfGenre="";
        signgucode="";
        prfState=2;
    }

    // 프로그레스 다이얼로그
    void showProgress(String message) {
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
        dialog.show();
    }
    void dismissProgress() {
        dialog.dismiss();
    }
}