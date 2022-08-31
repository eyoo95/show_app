package com.luvris2.publicperfomancedisplayapp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.adapter.PostingAdapter;
import com.luvris2.publicperfomancedisplayapp.model.Posting;

import java.util.ArrayList;


// 자유게시판 - 게시글 상세
// 레이아웃에 작성시간, 수정일, 추천수, 조회수, 작성자 등등 레이아웃 수정필요
// 완료시 해당 주석 삭제바람.
public class PostingReadActivity extends AppCompatActivity {

    ImageView imgBack;
    TextView txtTitle;
    TextView txtContent;

    PostingAdapter adapter;
    ArrayList<Posting> postingList = new ArrayList<>();

    private Posting posting;
    int postingId;
    String postingTitle;
    String postingContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_read);

        posting = (Posting) getIntent().getSerializableExtra("postingId");
        postingId = posting.getId();
        postingTitle = posting.getTitle();
        postingContent = posting.getContent();

        imgBack = findViewById(R.id.imgBack);
        txtTitle = findViewById(R.id.txtTitle);
        txtContent = findViewById(R.id.txtPrfTitle);

        txtTitle.setText(postingTitle);
        txtContent.setText(postingContent);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}