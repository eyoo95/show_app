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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.adapter.PerformanceReviewAdapter;
import com.luvris2.publicperfomancedisplayapp.api.KopisPerformanceApi;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.api.ReviewApi;
import com.luvris2.publicperfomancedisplayapp.model.KopisApiPerformance;
import com.luvris2.publicperfomancedisplayapp.model.Review;
import com.luvris2.publicperfomancedisplayapp.model.ReviewList;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

    KopisApiPerformance kopisApiPerformance;
    private String prfName;
    private String prfPlace;
    private String prfodfrom;
    private String Url;

    // 리사이클러 뷰 관련 변수
    RecyclerView recyclerViewPerfomanceInfo;
    PerformanceReviewAdapter adapter;
    ArrayList<Review> reviewList = new ArrayList<>();

    // 페이징에 필요한 멤버변수
    int offset = 0;
    int limit = 25;

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

        Log.i("MyTest ", " prfName : " + prfName, null);

        // 티켓 링크 버튼 클릭 시
        btnEventUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prfnm = kopisApiPerformance.getPrfName();
                Intent browserIntent  =  new Intent(Intent.ACTION_VIEW, Uri.parse("https://tickets.interpark.com/search?keyword="+Prfnm));
                startActivity(browserIntent);
            }
        });

        // 리뷰 작성 하러가기 클릭 시
        btnEventEditReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =  new Intent(PerformanceInfoActivity.this, MyReviewWriteActivity.class);

                intent.putExtra("Url", kopisApiPerformance.getPosterUrl()); // 포스터
                intent.putExtra("prfName", kopisApiPerformance.getPrfName()); // 이름
                intent.putExtra("prfPlace", kopisApiPerformance.getPrfPlace()); // 장소
                intent.putExtra("prfodDate", kopisApiPerformance.getPrfpdfrom() +" ~ "+ kopisApiPerformance.getPrfpdto()); // 기간

                startActivity(intent);
            }
        });

        // 리사이클러뷰 화면 설정
        recyclerViewPerfomanceInfo = findViewById(R.id.recyclerViewPerfomanceInfo);
        recyclerViewPerfomanceInfo.setHasFixedSize(true);
        recyclerViewPerfomanceInfo.setLayoutManager(new GridLayoutManager(PerformanceInfoActivity.this,2));

        getReviewList();
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

        Url = kopisApiPerformance.getPosterUrl();
        Glide.with(PerformanceInfoActivity.this).load(Url)
                .into(imgEventPoster);
        Log.i("MyTest Data ImgUrl", ""+kopisApiPerformance.getPosterUrl());

        // API에서 데이터를 불러와 멤버변수에 저장
        prfName = kopisApiPerformance.getPrfName(); // 이름
        prfPlace = kopisApiPerformance.getPrfPlace(); // 장소
        prfodfrom = kopisApiPerformance.getPrfpdfrom(); // 기간

        // 메모리에 저장된 데이터를 UI객체에 입력.
        txtEventTitle1.setText(prfName);
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

    void getReviewList() {
        // 데이터 초기화
        reviewList.clear();

        // 현재 시간 불러오기

        showProgress("리뷰 목록 불러오는 중...");

        // 네트워크로 데이터 전송, Retrofit 객체 생성
        Retrofit retrofit = NetworkClient.getRetrofitClient(PerformanceInfoActivity.this);
        ReviewApi api = retrofit.create(ReviewApi.class);

        Log.i("MyTest", "PerformanceInfoActivity retrofit : "+mt20id);
        Call<ReviewList> call = api.getShowReview(mt20id, offset, limit);

        call.enqueue(new Callback<ReviewList>() {
            @Override
            public void onResponse(Call<ReviewList> call, Response<ReviewList> response) {

                if(response.isSuccessful()){

                    reviewList.addAll( response.body().getResultList() );

                    if(reviewList != null){
                        adapter = new PerformanceReviewAdapter(PerformanceInfoActivity.this, reviewList);

                        adapter.notifyDataSetChanged();

                        recyclerViewPerfomanceInfo.setAdapter(adapter);
                    }


                    dismissProgress();
                }
                // 진행중인 공연이 없을 경우 메시지 출력
                else if(response.code() == 500) {
                    Toast.makeText(PerformanceInfoActivity.this, "현재 남겨진 리뷰가 없습니다.", Toast.LENGTH_LONG).show();
                    reviewList.clear();
                    adapter = new PerformanceReviewAdapter(PerformanceInfoActivity.this, reviewList);
                    recyclerViewPerfomanceInfo.setAdapter(adapter);
                    dismissProgress();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ReviewList> call, @NonNull Throwable t) {
                dismissProgress();
                Toast.makeText(PerformanceInfoActivity.this, "리뷰를 불러오지 못 했습니다.", Toast.LENGTH_LONG).show();
            }
        });
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