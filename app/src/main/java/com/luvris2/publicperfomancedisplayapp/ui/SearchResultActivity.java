package com.luvris2.publicperfomancedisplayapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.adapter.PerformanceSearchAdapter;
import com.luvris2.publicperfomancedisplayapp.model.KopisApiPerformance;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    ImageView imgBoardPosting;
    ImageView imgBack;
    TextView txtEventTitle;

    // 리사이클러 뷰 관련 변수
    RecyclerView recyclerView;
    PerformanceSearchAdapter adapter;
    ArrayList<KopisApiPerformance> performanceList = new ArrayList<>();

    // 프로그레스 다이얼로그
    private ProgressDialog dialog;

    // 공연 검색 키워드
    String prfTime;
    String prfName="", prfPlace="", prfGenre="", signgucode="";
    int prfState=2; // 2=공연중

    // 페이징에 필요한 멤버변수
    int offset = 0;
    int limit = 25;

    int cpage = 1;
    int rows = 6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        imgBack = findViewById(R.id.imgBack);
        txtEventTitle = findViewById(R.id.txtEventTitle);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this));

        Intent getIntent = getIntent();
        prfTime = getIntent.getStringExtra("prfTime");
        prfName = getIntent.getStringExtra("prfName");
        prfPlace = getIntent.getStringExtra("prfPlace");
        prfGenre = getIntent.getStringExtra("prfGenre");
        signgucode = getIntent.getStringExtra("signgucode");

        Log.i("MyTest SearchAct Search", ""+prfTime+prfName+prfPlace+prfGenre+signgucode);
        Log.i("MyTest SearchAct Search", ""+prfGenre);

//        showProgress("공연 목록 불러오는 중...");

//        // 네트워크로 데이터 전송, Retrofit 객체 생성
//        Retrofit retrofit = NetworkClient.getRetrofitClient(SearchResultActivity.this);
//        KopisPerformanceApi api = retrofit.create(KopisPerformanceApi.class);
//
//        // 헤더에 설정 할 데이터 확인, 공유 저장소에 저장되어있는 토큰 호출
//        // API 요청
//        // todo: 여기서부터 다시 수정할 것.
//        Call<KopisApiPerformance> call = api.getPerformance(prfTime, prfTime, cpage, rows, prfName, prfPlace, prfGenre, signgucode);
//
//        call.enqueue(new Callback<KopisApiPerformance>() {
//            @Override
//            public void onResponse(@NonNull Call<KopisApiPerformance> call, @NonNull Response<KopisApiPerformance> response) {
//                dismissProgress();
//                // 200 OK, 네트워크 정상 응답
//                if (response.isSuccessful()) {
//                    ArrayList<KopisApiPerformance> data = response.body().getResultList();
//                    Log.i("MyTest HomeFrag getPrf", "" + data);
//                    // 공연 검색
//                    if (data != null) {
//                        performanceList.addAll(data);
//                    } else {
//                        performanceList.clear();
//                        Toast.makeText(SearchResultActivity.this, "현재 진행중인 공연이 없습니다.", Toast.LENGTH_LONG).show();
//                    }
//                    adapter = new PerformanceSearchAdapter(SearchResultActivity.this, performanceList);
//                    recyclerView.setAdapter(adapter);
//                }
//                // 진행중인 공연이 없을 경우 메시지 출력
//                else if (response.code() == 500) {
//                    Toast.makeText(SearchResultActivity.this, "현재 진행중인 공연이 없습니다.", Toast.LENGTH_LONG).show();
//                    performanceList.clear();
//                    adapter = new PerformanceSearchAdapter(SearchResultActivity.this, performanceList);
//                    recyclerView.setAdapter(adapter);
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<KopisApiPerformance> call, @NonNull Throwable t) {
//                dismissProgress();
//                Log.i("MyTest getprf fail", "" + t);
//            }
//        });
//
//        imgBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//    }
//    // 프로그레스 다이얼로그
//    void showProgress(String message) {
//        dialog = new ProgressDialog(SearchResultActivity.this);
//        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        dialog.setMessage(message);
//        dialog.show();
//    }
//    void dismissProgress() {
//        dialog.dismiss();

    }
}