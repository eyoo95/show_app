package com.luvris2.publicperfomancedisplayapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.adapter.MyReviewAdapter;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.api.ReviewApi;
import com.luvris2.publicperfomancedisplayapp.config.Config;
import com.luvris2.publicperfomancedisplayapp.model.Review;
import com.luvris2.publicperfomancedisplayapp.model.ReviewList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

// 내가 별점 준 (리뷰 작성한) 목록 (내 별점 액티비티) : 내가 남겼던 리뷰 확인 기능
public class MyReviewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imgBack;
    ImageView imgDelete;
    
    // 어댑터, 어레이리스트
    MyReviewAdapter adapter;
    ArrayList<Review> reviewList = new ArrayList<>();

    // 페이징에 필요한 멤버변수
    int offset = 0;
    int limit = 25;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);

        // 쉐어드프리퍼런스에 억세스토큰 가져오기
        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String token = sp.getString("accessToken", "");

        imgBack = findViewById(R.id.imgBack);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyReviewActivity.this));

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 네트워크 데이터를 받아온다.
        getNetworkData();
    }

    private void getNetworkData() {
        reviewList.clear();
        offset = 0;
        limit = 25;

        Retrofit retrofit = NetworkClient.getRetrofitClient(MyReviewActivity.this);
        ReviewApi api = retrofit.create(ReviewApi.class);

        Call<ReviewList> call = api.getReview("Bearer "+ token, offset, limit);

        call.enqueue(new Callback<ReviewList>() {
            @Override
            public void onResponse(Call<ReviewList> call, Response<ReviewList> response) {

                if(response.isSuccessful()){

                    reviewList.addAll( response.body().getResultList() );

                    adapter = new MyReviewAdapter(MyReviewActivity.this, reviewList);

                    adapter.notifyDataSetChanged();

                    recyclerView.setAdapter(adapter);

                }else{

                }
            }

            @Override
            public void onFailure(Call<ReviewList> call, Throwable t) {
            }
        });

    }
}