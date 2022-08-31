package com.luvris2.publicperfomancedisplayapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.api.KopisPerformanceApi;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.model.KopisApiPerformance;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;

public class PerformanceInfoActivity extends AppCompatActivity {

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
    private String fcltynm;


    KopisApiPerformance kopisApiPerformance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_info);

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

        getPerformanceDetailData(mt20id);

        // 티켓 링크 버튼 클릭 시
        btnEventUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prfnm = kopisApiPerformance.getPrfName();
                Intent browserIntent  =  new Intent(Intent.ACTION_VIEW, Uri.parse("https://tickets.interpark.com/search?keyword="+Prfnm));
                startActivity(browserIntent);
            }
        });

        // 리뷰 작성 하러가기  클릭 시
        btnEventEditReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =  new Intent(PerformanceInfoActivity.this, MyReviewWriteActivity.class);
                startActivity(intent);
            }
        });
    }

    void getPerformanceDetailData(String prfId) {
        showProgress("공연 목록 불러오는 중...");

        // retrofit 설정
        Retrofit retrofit = NetworkClient.getRetrofitClient(PerformanceInfoActivity.this);
        KopisPerformanceApi api = retrofit.create(KopisPerformanceApi.class);
        Call<KopisApiPerformance> call = api.getPerformanceDetail(prfId);

        // Retrofit 값을 바로 저장하기 위한 동기 처리
        new Thread(() -> {
            try {
                kopisApiPerformance = call.execute().body().getResult();
                Log.i("MyTest Data Response", "" + kopisApiPerformance);
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

        Glide.with(PerformanceInfoActivity.this).load(kopisApiPerformance.getPosterUrl())
                .into(imgEventPoster);
        Log.i("MyTest Data ImgUrl", ""+kopisApiPerformance.getPosterUrl());
        txtEventTitle1.setText(kopisApiPerformance.getPrfName());
        txtEventDate1.setText(kopisApiPerformance.getPrfpdfrom() + " ~ " + kopisApiPerformance.getPrfpdto());
        txtEventPlace1.setText(kopisApiPerformance.getPrfPlace());
        txtEventDesc1.setText("출연진:" + kopisApiPerformance.getPrfcast() + System.getProperty("line.separator")
                + "제작진:" + kopisApiPerformance.getPrfcrew() + System.getProperty("line.separator")
                + "공연런타임:" + kopisApiPerformance.getPrfruntime() + System.getProperty("line.separator")
                + "공연관람연령:" + kopisApiPerformance.getPrfage() + System.getProperty("line.separator")
                + "제작사:" + kopisApiPerformance.getEntrpsnm() + System.getProperty("line.separator")
                + "장르:" + kopisApiPerformance.getPrfGenre() + System.getProperty("line.separator")
                + "공연시간:" + kopisApiPerformance.getDtguidance());

        dismissProgress();
    }

    // 공연 조회 변수 초기화
    public void initKeyword() {
        mt20id = "";
        service="";
    }

    // 프로그레스 다이얼로그
    void showProgress(String message) {
        dialog = new ProgressDialog(PerformanceInfoActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
        dialog.show();
    }
    void dismissProgress() {
        dialog.dismiss();
    }

}