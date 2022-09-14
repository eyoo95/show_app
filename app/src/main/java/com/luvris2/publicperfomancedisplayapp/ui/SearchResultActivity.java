package com.luvris2.publicperfomancedisplayapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.adapter.PerformanceSearchDialogAdapter;
import com.luvris2.publicperfomancedisplayapp.api.KopisPerformanceApi;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.model.KopisApiPerformance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchResultActivity extends AppCompatActivity {

    ImageView imgBack;

    // 리사이클러 뷰 관련 변수
    RecyclerView recyclerViewSearch;
    PerformanceSearchDialogAdapter adapter1;
    ArrayList<KopisApiPerformance> performanceList1 = new ArrayList<>();

    // 프로그레스 다이얼로그
    private ProgressDialog dialog;

    // 공연 검색 키워드
    String prfTime;
    String prfName="", prfPlace="", prfGenre="", signgucode="";
    int prfState=2; // 2=공연중

    int cpage = 1;
    int rows = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        imgBack = findViewById(R.id.imgBack);
        recyclerViewSearch = findViewById(R.id.recyclerViewSearch);
        recyclerViewSearch.setHasFixedSize(true);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this));

        Intent getIntent = getIntent();
        prfTime = getIntent.getStringExtra("prfTime");
        prfName = getIntent.getStringExtra("prfName");
        prfPlace = getIntent.getStringExtra("prfPlace");
        prfGenre = getIntent.getStringExtra("prfGenre");
        signgucode = getIntent.getStringExtra("signgucode");

        showProgress("공연 목록 불러오는 중...");

        // 네트워크로 데이터 전송, Retrofit 객체 생성
        Retrofit retrofit = NetworkClient.getRetrofitClient(SearchResultActivity.this);
        KopisPerformanceApi api = retrofit.create(KopisPerformanceApi.class);

        // API 요청
        Call<KopisApiPerformance> call = api.getPerformance(prfTime, prfTime, cpage, rows, prfName, prfPlace, prfGenre, signgucode, "" ,2);

        call.enqueue(new Callback<KopisApiPerformance>() {
            @Override
            public void onResponse(@NonNull Call<KopisApiPerformance> call, @NonNull Response<KopisApiPerformance> response) {
                dismissProgress();
                // 200 OK, 네트워크 정상 응답
                if (response.isSuccessful()) {
                    ArrayList<KopisApiPerformance> data = response.body().getResultList();
                    // 공연 검색
                    if (data != null) {
                        performanceList1.addAll(data);
                        Log.i("MyTest SearchResultActi", "Retrofit : "+ data.get(0).getPrfName()+ data.get(0).getPrfId());
                    } else {
                        performanceList1.clear();
                        Toast.makeText(SearchResultActivity.this, "현재 진행중인 공연이 없습니다.", Toast.LENGTH_LONG).show();
                    }
                    adapter1 = new PerformanceSearchDialogAdapter(SearchResultActivity.this, performanceList1);
                    recyclerViewSearch.setAdapter(adapter1);
                }
                // 진행중인 공연이 없을 경우 메시지 출력
                else if (response.code() == 500) {
                    Toast.makeText(SearchResultActivity.this, "현재 진행중인 공연이 없습니다.", Toast.LENGTH_LONG).show();
                    performanceList1.clear();
                    adapter1 = new PerformanceSearchDialogAdapter(SearchResultActivity.this, performanceList1);
                    recyclerViewSearch.setAdapter(adapter1);
                }
            }

            @Override
            public void onFailure(@NonNull Call<KopisApiPerformance> call, @NonNull Throwable t) {
                dismissProgress();
                Log.i("MyTest SearchResultActi", "Retrofit Error : " + t);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // 프로그레스 다이얼로그
    void showProgress(String message) {
        dialog = new ProgressDialog(SearchResultActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
        dialog.show();
    }
    void dismissProgress() {
        dialog.dismiss();
    }
}