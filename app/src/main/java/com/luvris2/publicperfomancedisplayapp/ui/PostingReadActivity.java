package com.luvris2.publicperfomancedisplayapp.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.adapter.PostingAdapter;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.api.PostingApi;
import com.luvris2.publicperfomancedisplayapp.config.Config;
import com.luvris2.publicperfomancedisplayapp.model.PostRecomm;
import com.luvris2.publicperfomancedisplayapp.model.PostRes;
import com.luvris2.publicperfomancedisplayapp.model.Posting;
import com.luvris2.publicperfomancedisplayapp.model.PostingList;
import com.luvris2.publicperfomancedisplayapp.model.User;
import com.luvris2.publicperfomancedisplayapp.model.UserRes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


// 자유게시판 - 게시글 상세
// 레이아웃에 작성시간, 수정일, 추천수, 조회수, 작성자 등등 레이아웃 수정필요
// 완료시 해당 주석 삭제바람.
public class PostingReadActivity extends AppCompatActivity {

    ImageView imgBack;
    TextView txtTitle;
    TextView txtContent;
    TextView txtNickname;
    TextView txtDate;
    TextView txtRecommend;
    Button btnRecommend;



    PostingAdapter adapter;
    ArrayList<Posting> postingList = new ArrayList<>();

    private Posting posting;
    int postingId;
    String postingTitle;
    String postingContent;
    String postingDate;
    String postingNickname;
    int postingRecommendInt;

    // 추천 여부
    Boolean isRecommended = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_read);

        posting = (Posting) getIntent().getSerializableExtra("postingId");
        postingId = posting.getId();
        postingTitle = posting.getTitle();
        postingContent = posting.getContent();
        postingDate = posting.getCreatedAt();
        postingNickname = posting.getNickname();
        postingRecommendInt = posting.getRecommend();

        imgBack = findViewById(R.id.imgMoreReview);
        txtTitle = findViewById(R.id.txtTitle);
        txtContent = findViewById(R.id.txtContent);
        txtNickname = findViewById(R.id.txtNickname);
        txtDate = findViewById(R.id.txtDate);
        btnRecommend = findViewById(R.id.btnRecommend);
        txtRecommend = findViewById(R.id.txtRecommend);

        txtTitle.setText(postingTitle);
        txtContent.setText(postingContent);
        txtDate.setText("게시일: "+posting.getCreatedAt().substring(5,10)+" ("+posting.getCreatedAt().substring(11,16)+")");
        txtNickname.setText("작성자: "+postingNickname);
        txtRecommend.setText("추천수 "+postingRecommendInt);

        // 처음 한번만 추천 상태 확인
        checkRecommend();


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isRecommended==false){
                    postingRecommend();

                }else if (isRecommended==true) {
                    cancelRecommend();
                }

            }
        });
    }
    // 추천 상태 확인 메소드
    private void checkRecommend(){

        Retrofit retrofit = NetworkClient.getRetrofitClient(PostingReadActivity.this);
        PostingApi api = retrofit.create(PostingApi.class);

        SharedPreferences sp = PostingReadActivity.this.getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        Call<PostRes> call = api.checkRecommendPosting("Bearer " + accessToken, postingId);

        call.enqueue(new Callback<PostRes>() {
            @Override
            public void onResponse(Call<PostRes> call, Response<PostRes> response) {
                if(response.isSuccessful()){
                    PostRes data = response.body();
                    List<PostRecomm> postRecomm = data.getResultList();

                    String resultState = (postRecomm+"").trim();
                    Log.i("My test",resultState+"(결과 확인)");

                    if(resultState.equals("[]")){
                        isRecommended = false;
                        Log.i("My test",isRecommended+"(추천안됨)");
                        btnRecommend.setText("추천");
                    } else {
                        isRecommended = true;
                        Log.i("My test",isRecommended+"(추천됨)");
                        btnRecommend.setText("추천취소");
                    }

                } else {
                    Toast.makeText(PostingReadActivity.this, "추천 확인중 에러 발생 : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostRes> call, Throwable t) {

            }
        });
    }

    // 게시물 추천 메소드
    private void postingRecommend(){
        Retrofit retrofit = NetworkClient.getRetrofitClient(PostingReadActivity.this);
        PostingApi api = retrofit.create(PostingApi.class);

        SharedPreferences sp = PostingReadActivity.this.getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        Call<PostRes> call = api.recommendThisPosting("Bearer " + accessToken, postingId);

        call.enqueue(new Callback<PostRes>() {
            @Override
            public void onResponse(Call<PostRes> call, Response<PostRes> response) {
                // 200 OK 일 때,
                if (response.isSuccessful()) {
                    Toast.makeText(PostingReadActivity.this, "해당 게시물을 추천하셨습니다.", Toast.LENGTH_SHORT).show();
                    btnRecommend.setText("추천취소");
                    postingRecommendInt = postingRecommendInt+1;
                    txtRecommend.setText("추천수 "+postingRecommendInt);
                    isRecommended = true;

                } else {
                    Toast.makeText(PostingReadActivity.this, "추천중 에러 발생 : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostRes> call, Throwable t) {

            }
        });
    }

    // 게시물 추천취소 메소드
    private void cancelRecommend(){
        Retrofit retrofit = NetworkClient.getRetrofitClient(PostingReadActivity.this);
        PostingApi api = retrofit.create(PostingApi.class);

        SharedPreferences sp = PostingReadActivity.this.getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        Call<PostRes> call = api.cancelRecommendPosting("Bearer " + accessToken, postingId);

        call.enqueue(new Callback<PostRes>() {
            @Override
            public void onResponse(Call<PostRes> call, Response<PostRes> response) {
                // 200 OK 일 때,
                if (response.isSuccessful()) {
                    Toast.makeText(PostingReadActivity.this, "추천취소 하셨습니다.", Toast.LENGTH_SHORT).show();
                    btnRecommend.setText("추천");
                    postingRecommendInt = postingRecommendInt-1;
                    txtRecommend.setText("추천수 "+postingRecommendInt);
                    isRecommended = false;

                } else {
                    Toast.makeText(PostingReadActivity.this, "추천 취소중 에러 발생 : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostRes> call, Throwable t) {

            }
        });
    }

}