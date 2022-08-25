package com.luvris2.publicperfomancedisplayapp.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.adapter.BoardMyAdapter;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.api.PostingApi;
import com.luvris2.publicperfomancedisplayapp.config.Config;
import com.luvris2.publicperfomancedisplayapp.model.Posting;
import com.luvris2.publicperfomancedisplayapp.model.PostingList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BoardMyActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imgBoardMyBack;

    // 어댑터, 리스트
    BoardMyAdapter adapter;
    ArrayList<Posting> postingList = new ArrayList<>();

    // 페이징에 필요한 멤버변수
    int offset = 0;
    int limit = 25;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_my);

        imgBoardMyBack = findViewById(R.id.imgBoardMyBack);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(BoardMyActivity.this));

        imgBoardMyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 네트워크 데이터를 받아온다.
        getNetworkData();
    }

    private void getNetworkData() {
        postingList.clear();
        offset = 0;
        limit = 25;

        Retrofit retrofit = NetworkClient.getRetrofitClient(BoardMyActivity.this);
        PostingApi api = retrofit.create(PostingApi.class);

        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);

        String token = sp.getString("accessToken", "");

        Call<PostingList> call = api.getMyPosting("Bearer "+ token, offset, limit);

        call.enqueue(new Callback<PostingList>() {
            @Override
            public void onResponse(Call<PostingList> call, Response<PostingList> response) {

                if(response.isSuccessful()){

                    postingList.addAll( response.body().getResultList() );

                    adapter = new BoardMyAdapter(BoardMyActivity.this, postingList);

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