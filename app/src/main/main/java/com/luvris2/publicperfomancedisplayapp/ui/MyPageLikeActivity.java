package com.luvris2.publicperfomancedisplayapp.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.adapter.MyReviewAdapter;
import com.luvris2.publicperfomancedisplayapp.config.Config;
import com.luvris2.publicperfomancedisplayapp.model.Like;

import java.util.ArrayList;

// 좋아요를 누른 행사 목록 (좋아요 액티비티) : 좋아요 누른 행사 확인하는 기능
public class MyPageLikeActivity extends AppCompatActivity {

    ImageView imgBack; // 이전 버튼 이동
    RecyclerView recyclerView; // 리사이클러뷰


    // 어댑터, 어레이리스트
    MyReviewAdapter adapter;
    ArrayList<Like> likeList = new ArrayList<>();

    // 페이징에 필요한 멤버변수
    int offset = 0;
    int limit = 25;

    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_like);

        // 쉐어드프리퍼런스에 억세스토큰 가져오기
        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String token = sp.getString("accessToken", "");

        imgBack = findViewById(R.id.imgMoreReview);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyPageLikeActivity.this));


        // 이전 화면 이동 클릭시
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // 네트워크 데이터를 받아온다.
//        getNetworkData();
    }

//    private void getNetworkData() {
//        likeList.clear();
//        offset = 0;
//        limit = 25;
//
//        Retrofit retrofit = NetworkClient.getRetrofitClient(MyPageLikeActivity.this);
//        ReviewApi api = retrofit.create(ReviewApi.class);
//
//        Call<likeList> call = api.getReview("Bearer "+ token, offset, limit);
//
//        call.enqueue(new Callback<likeList>() {
//            @Override
//            public void onResponse(Call<likeList> call, Response<likeList> response) {
//
//                if(response.isSuccessful()){
//
//                    likeList.addAll( response.body().getResultList() );
//
//                    adapter = new MyPageLikeAdapter(MyPageLikeActivity.this, likeList);
//
//                    adapter.notifyDataSetChanged();
//
//                    recyclerView.setAdapter(adapter);
//
//                }else{
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ReviewList> call, Throwable t) {
//            }
//        });
//
//    }

}