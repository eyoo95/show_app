package com.luvris2.publicperfomancedisplayapp.ui;

import static com.luvris2.publicperfomancedisplayapp.BuildConfig.SAMPLE_API_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.luvris2.publicperfomancedisplayapp.BuildConfig;
import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.api.KopisPerformanceApi;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.model.KopisApiPerformance;
import com.luvris2.publicperfomancedisplayapp.model.kopisApiDetail;

import androidx.appcompat.app.AlertDialog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class eventInfoActivity extends AppCompatActivity {

    Context context;

    private ProgressDialog dialog;

    ImageView imgEventPoster;
    Button btnEventUrl;
    Button btnEventEditReview;
    TextView txtEventTitle1;
    TextView txtEventPlace1;
    TextView txtEventDate1;
    TextView txtEventDesc1;

    String mt20id="", service="";

    private String Prfnm;
    private String imgUrl;
    private String Prfpdfrom;
    private String Prfpdto;
    private String fcltynm;
    private String sty;

    String Kopis_API_KEY = SAMPLE_API_KEY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        imgEventPoster = findViewById(R.id.imgEventPoster);
        btnEventUrl = findViewById(R.id.btnEventURL);
        btnEventEditReview = findViewById(R.id.btnEventEditReview);
        txtEventTitle1 = findViewById(R.id.txtEventTitle1);
        txtEventPlace1 = findViewById(R.id.txtEventPlace1);
        txtEventDate1 = findViewById(R.id.txtEventDate1);
        txtEventDesc1 = findViewById(R.id.textEventDesc1);


//         1. 누른 사진의 인덱스를 가져온다.
        mt20id = getIntent().getStringExtra("mt20id");

        Log.i("recyclerView Intent ", " mt20id : " + mt20id, null);

        getPerformanceDetailData(mt20id, service);


        // 글라이드 라이브러리 사용
        context = eventInfoActivity.this;
//        GlideUrl url = new GlideUrl(imgUrl, new LazyHeaders.Builder().addHeader("User-Agent", "Android").build());
//        GlideUrl url = new GlideUrl(imgUrl);
        Glide.with(context).load(imgUrl).override(200, 300).placeholder(R.drawable.ic_image_not_supported).fitCenter().into(imgEventPoster);


        // 티켓 링크 버튼 클릭 시
        btnEventUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent  =  new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.naver.com/"));
                startActivity(browserIntent);
            }
        });

        // 리뷰 작성 하러가기  클릭 시
        btnEventEditReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =  new Intent(eventInfoActivity.this, EditReviewActivity.class);
                startActivity(intent);
            }
        });
    }

    void getPerformanceDetailData(String Id ,String service) {
        showProgress("공연 목록 불러오는 중...");

        // 네트워크로 데이터 전송, Retrofit 객체 생성
        Retrofit retrofit = NetworkClient.getRetrofitClient(eventInfoActivity.this);
        KopisPerformanceApi api = retrofit.create(KopisPerformanceApi.class);

        // 헤더에 설정 할 데이터 확인, 공유 저장소에 저장되어있는 토큰 호출
        // API 요청
        Call<kopisApiDetail> call = api.getDetailSearch(Id, service);

        // Retrofit 값을 바로 저장하기 위한 동기 처리
        new Thread(() -> {
            try {
                kopisApiDetail data = call.execute().body();
                imgUrl = data.getPoster();
                Prfnm = data.getPrfnm();
                Prfpdfrom = data.getPrfpdfrom();
                Prfpdto = data.getPrfpdto();
                fcltynm = data.getFcltynm();
//                sty = data.getSty();

                Log.i("recyclerView imgUrl ", " imgUrl : " + data.getPoster() , null);
                Log.i("recyclerView Prfnm ", " Prfnm : " +  data.getPrfnm() , null);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // API 응답에 따른 약간의 대기 시간 설정
        try {
            Thread.sleep(3000);
            dismissProgress();
        } catch (InterruptedException e) {
            e.printStackTrace();
            dismissProgress();
        }
        dismissProgress();
    }



//        // API 요청
//        Call<kopisApiDetail> call = api.getDetailSearch(Id , service);
//        call.enqueue(new Callback<kopisApiDetail>() {
//            @Override
//            public void onResponse(@NonNull Call<kopisApiDetail> call, @NonNull Response<kopisApiDetail> response) {
//                dismissProgress();
//
//                // 200 OK, 네트워크 정상 응답
//                if(response.isSuccessful()) {
//                    kopisApiDetail data = response.body();
//
//                    imgUrl = data.getPoster();
//                    Prfnm = data.getPrfnm();
//                    Prfpdfrom = data.getPrfpdfrom();
//                    Prfpdto = data.getPrfpdto();
//                    fcltynm = data.getFcltynm();
//                    sty = data.getSty();
//
//                }
//
//                // 진행중인 공연이 없을 경우 메시지 출력
//                else if(response.code() == 500) {
//                    Toast.makeText(eventInfoActivity.this, "현재 진행중인 공연이 없습니다.", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<kopisApiDetail> call, @NonNull Throwable t) {
//                dismissProgress();
//            }
//        });
//        initKeyword(); // 검색 조건 초기화
//    }

//    // Retrofit 값을 바로 저장하기 위한 동기 처리
//        new Thread(() -> {
//        try {
//            kopisApiDetail data = call.execute().body();
//            imgUrl = data.getPoster();
//            Prfnm = data.getPrfnm();
//            Prfpdfrom = data.getPrfpdfrom();
//            Prfpdto = data.getPrfpdto();
//            fcltynm = data.getFcltynm();
//            sty = data.getSty();
//
//            // 화면에 표시
//            txtEventTitle1.setText(Prfnm);
//            txtEventDate1.setText(Prfpdfrom);
//            txtEventTitle1.setText(fcltynm);
//            txtEventDesc1.setText(sty);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }).start();
//
//    // API 응답에 따른 약간의 대기 시간 설정
//        try {
//        Thread.sleep(3000);
//        dismissProgress();
//    } catch (InterruptedException e) {
//        e.printStackTrace();
//        dismissProgress();
//    }
//    dismissProgress();


    // 공연 조회 변수 초기화
    public void initKeyword() {
        mt20id = "";
        service="";
    }

    // 프로그레스 다이얼로그
    void showProgress(String message) {
        dialog = new ProgressDialog(eventInfoActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
        dialog.show();
    }
    void dismissProgress() {
        dialog.dismiss();
    }

}

//    // 네트워크로 데이터 전송, Retrofit 객체 생성
//    Retrofit retrofit = NetworkClient.getRetrofitClient(getActivity());
//    KopisPerformanceApi api = retrofit.create(KopisPerformanceApi.class);
//
//    // API 요청
//    Call<KopisApiPerformance> call = api.getPlaceSearch(currentTime, currentTime, cpage, rows, prfName, prfPlace, prfGenreCode, prfSidoCode, prfState);
//
//// Retrofit 값을 바로 저장하기 위한 동기 처리
//        new Thread(() -> {
//                try {
//                performanceList = call.execute().body().getResultList();
//                } catch (IOException e) {
//                e.printStackTrace();
//                }
//                }).start();
//
//                // API 응답에 따른 약간의 대기 시간 설정
//                try {
//                Thread.sleep(2000);
//                dismissProgressBar();
//                } catch (InterruptedException e) {
//                e.printStackTrace();
//                dismissProgressBar();
//                }
//                initKeyword(); // 검색 조건 초기화
//                }
