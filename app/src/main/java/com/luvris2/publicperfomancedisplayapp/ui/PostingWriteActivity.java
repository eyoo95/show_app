package com.luvris2.publicperfomancedisplayapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.adapter.BoardAdapter;
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

// 자유게시판 - 게시글 작성
// 최지훈
public class PostingWriteActivity extends AppCompatActivity {

    ImageView imgBack;
    EditText editTxtBoardTitle;
    ImageView imgBoardRead;
    EditText editTxtBoard;
    Button btnBoardUpload;

    BoardAdapter adapter;
    ArrayList<Posting> postingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_write);

        imgBack = findViewById(R.id.imgBack);
        editTxtBoardTitle = findViewById(R.id.editTxtBoardTitle);
        imgBoardRead = findViewById(R.id.imgBoardRead);
        editTxtBoard = findViewById(R.id.editTxtBoard);
        btnBoardUpload = findViewById(R.id.btnBoardUpload);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnBoardUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTxtBoardTitle.getText().toString().trim();
                String content = editTxtBoard.getText().toString().trim();

                Retrofit retrofit = NetworkClient.getRetrofitClient(PostingWriteActivity.this);
                PostingApi api = retrofit.create(PostingApi.class);

                Posting posting = new Posting(title, content);

//                RequestBody titleBody = RequestBody.create(title, MediaType.parse("text/plain"));
//                RequestBody contentBody = RequestBody.create(content, MediaType.parse("text/plain"));

                SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);

                String token = sp.getString("accessToken", "");

//                Call<PostingList> call = api.addPosting("Bearer "+ token, titleBody, contentBody);
                Call<PostingList> call = api.addPosting("Bearer "+ token, posting);

                call.enqueue(new Callback<PostingList>() {
                    @Override
                    public void onResponse(Call<PostingList> call, Response<PostingList> response) {
                        if(response.isSuccessful()){

                            Intent intent = new Intent(PostingWriteActivity.this, PostingActivity.class);
                            startActivity(intent);

                            finish();
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<PostingList> call, Throwable t) {

                    }
                });
            }
        });
    }
}