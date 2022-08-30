package com.luvris2.publicperfomancedisplayapp.ui;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.luvris2.publicperfomancedisplayapp.R;

// 내가 쓴 리뷰 리스트에서 수정버튼 눌렀을 때 (리뷰 수정 액티비티) : 리뷰 수정 기능
public class MyReviewEditActivity extends AppCompatActivity {

    ImageView imgBack; // 이전 화면 이동
    Button btnReviewEdit; // 리뷰 수정 버튼

    CardView cardViewReviewEdit; // (공연정보) 리뷰 수정 카드뷰
    ImageView imgReviewEditShow; // (공연정보) 공연 포스터
    TextView txtReviewEditTitle; // (공연정보) 공연 제목
    TextView txtReviewEditData; // (공연정보) 공연날짜
    TextView txtReviewEditPlace; // (공연정보) 공연 장소

    RatingBar ratingBarReviewEdit; // 별점 수정
    ImageView imgReviewEditPhoto; // 리뷰사진 수정
    TextView editReviewContents; // 리뷰내용 수정

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review_edit);

        imgBack = findViewById(R.id.imgBack);
        btnReviewEdit = findViewById(R.id.btnReviewEdit);

        cardViewReviewEdit = findViewById(R.id.cardViewReivewEdit);
        imgReviewEditShow = findViewById(R.id.imgReviewEditShow);
        txtReviewEditTitle = findViewById(R.id.txtReviewEditTitle);
        txtReviewEditData = findViewById(R.id.txtReviewEditDate);
        txtReviewEditPlace = findViewById(R.id.txtReviewEditPlace);

        ratingBarReviewEdit = findViewById(R.id.ratingBarReviewEdit);
        imgReviewEditPhoto = findViewById(R.id.imgReviewEditPhoto);
        editReviewContents = findViewById(R.id.editReviewContents);


        // 리뷰수정 버튼 클릭시
        btnReviewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyReviewActivity.class);
                startActivity(intent);
            }
        });



    }
}