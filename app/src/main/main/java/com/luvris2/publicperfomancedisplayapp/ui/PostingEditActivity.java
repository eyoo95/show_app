package com.luvris2.publicperfomancedisplayapp.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.adapter.PostingMyAdapter;
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

public class PostingEditActivity extends AppCompatActivity {

    ImageView imgBack;
    EditText editTitle;
    EditText editContent;
    Button btnUpdate;

    PostingMyAdapter adapter;
    ArrayList<Posting> postingList = new ArrayList<>();

    private Posting posting;
    int postingId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_edit);

        posting = (Posting) getIntent().getSerializableExtra("postingId");
        postingId = posting.getId();

        getSupportActionBar().setTitle(posting.getTitle() + " 수정");

        imgBack = findViewById(R.id.imgMoreReview);
        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        btnUpdate = findViewById(R.id.btnUpdate);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 수정하기 버튼 클릭 시
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTitle.getText().toString().trim();
                String content = editContent.getText().toString().trim();

                Retrofit retrofit = NetworkClient.getRetrofitClient(PostingEditActivity.this);
                PostingApi api = retrofit.create(PostingApi.class);

                Posting posting = new Posting(title, content);

                SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);

                String token = sp.getString("accessToken", "");

                Call<PostingList> call = api.updatePosting("Bearer "+ token, postingId, posting);

                call.enqueue(new Callback<PostingList>() {
                    @Override
                    public void onResponse(Call<PostingList> call, Response<PostingList> response) {
                        if(response.isSuccessful()){

//                            Intent intent = new Intent(PostingEditActivity.this, PostingMyActivity.class);
//                            startActivity(intent);

//                            adapter = new PostingMyAdapter(PostingEditActivity.this, postingList);
//
//                            adapter.notifyDataSetChanged();

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