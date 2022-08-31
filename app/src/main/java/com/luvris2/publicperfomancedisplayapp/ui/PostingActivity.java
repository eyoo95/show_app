package com.luvris2.publicperfomancedisplayapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.adapter.PostingAdapter;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.api.PostingApi;
import com.luvris2.publicperfomancedisplayapp.model.Posting;
import com.luvris2.publicperfomancedisplayapp.model.PostingList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

// 자유게시판 - 전체 게시글
// 최지훈
public class PostingActivity extends AppCompatActivity {

    ImageView imgBoardPosting;
    ImageView imgBack;
    TextView txtEventTitle;
    RecyclerView recyclerView;

    // 어댑터, 리스트
    PostingAdapter adapter;
    ArrayList<Posting>postingList = new ArrayList<>();

    // 페이징에 필요한 멤버변수
    int offset = 0;
    int limit = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);

        imgBoardPosting = findViewById(R.id.imgBoardPosting);
        imgBack = findViewById(R.id.imgBack);
        txtEventTitle = findViewById(R.id.txtEventTitle);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PostingActivity.this));

        imgBoardPosting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostingActivity.this, PostingWriteActivity.class);
                startActivity(intent);
                finish();
            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 네트워크로 부터 데이터 가져온다.
        getNetworkData();

    }

    private void getNetworkData() {
        postingList.clear();
        offset = 0;
        limit = 25;

        Retrofit retrofit = NetworkClient.getRetrofitClient(PostingActivity.this);
        PostingApi api = retrofit.create(PostingApi.class);

        Call<PostingList> call = api.getPostingList(offset, limit);

        call.enqueue(new Callback<PostingList>() {
            @Override
            public void onResponse(Call<PostingList> call, Response<PostingList> response) {

                if(response.isSuccessful()){

                    postingList.addAll( response.body().getResultList() );

                    adapter = new PostingAdapter(PostingActivity.this, postingList);

                    adapter.notifyDataSetChanged();

                    recyclerView.setAdapter(adapter);

                }else{

                }

            }

            @Override
            public void onFailure(Call<PostingList> call, Throwable t) {
            }
        });

    }
}