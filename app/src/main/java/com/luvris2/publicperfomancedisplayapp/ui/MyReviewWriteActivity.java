package com.luvris2.publicperfomancedisplayapp.ui;


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


// 행사 상세 정보에서 리뷰 작성 눌렀을 때 (리뷰 작성하는 액티비티) : 리뷰 작성, 별점, 사진 추가
public class MyReviewWriteActivity extends AppCompatActivity {

    ImageView imgBack; // 이전 화면 이동
    Button btnReviewWrite; // 리뷰 작성 버튼

    CardView cardViewReview; // (공연 정보) 리뷰 작성 카드뷰
    ImageView imgReviewWriteShow; // (공연정보) 공연 포스터
    TextView txtReviewWriteTitle; // (공연정보) 공연 제목
    TextView txtReviewWriteData; // (공연정보) 공연날짜
    TextView txtReviewWritePlace; // (공연정보) 공연 장소

    RatingBar ratingBarReview; // 별점주기
    ImageView imgReviewPhoto; // 리뷰사진 추가
    TextView editReviewWrite; // 리뷰내용 작성


    // 사진관련 변수
    private final int GET_GALLERY_IMAGE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review_write);

        imgBack = findViewById(R.id.imgBack);
        btnReviewWrite = findViewById(R.id.btnReviewWrite);

        cardViewReview = findViewById(R.id.cardViewReview);
        imgReviewWriteShow = findViewById(R.id.imgReviewEditShow);
        txtReviewWriteTitle = findViewById(R.id.txtReviewEditTitle);
        txtReviewWriteData = findViewById(R.id.txtReviewEditDate);
        txtReviewWritePlace = findViewById(R.id.txtReviewEditPlace);

        ratingBarReview = findViewById(R.id.ratingBarReview);
        imgReviewPhoto = findViewById(R.id.imgReviewPhoto);
        editReviewWrite = findViewById(R.id.editReviewWrite);


        // 이전 화면 이동 클릭시
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // 리뷰작성 버튼 클릭시
        btnReviewWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        //이미지 버튼 클릭 시
        imgReviewPhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                //intent.setType("image/*");
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });



    }

}