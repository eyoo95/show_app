package com.luvris2.publicperfomancedisplayapp.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.luvris2.publicperfomancedisplayapp.R;


// 행사 상세 정보에서 리뷰 작성 눌렀을 때 (리뷰 작성하는 액티비티) : 리뷰 작성, 별점, 사진 추가
public class MyReviewWriteActivity extends AppCompatActivity {

    ImageView imgBack; // 이전 화면 이동
    Button btnReviewWrite; // 리뷰 작성 버튼

    ImageView imgReviewWritePoster; // (공연정보) 공연 포스터
    TextView txtReviewWriteTitle; // (공연정보) 공연 제목
    TextView txtReviewWriteDate; // (공연정보) 공연날짜
    TextView txtReviewWritePlace; // (공연정보) 공연 장소

    RatingBar ratingBarReview; // 별점주기
    ImageView imgReviewPhoto; // 리뷰사진 추가
    TextView editReviewWrite; // 리뷰내용 작성
    TextView txtReviewRating; // 평점

    // 사진관련 변수
    private final int GET_GALLERY_IMAGE = 200;

    private int rating;
    private String prfName;
    private String prfodDate;
    private String prfPlace;

    private String imgUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review_write);

        imgBack = findViewById(R.id.imgMoreReview);
        btnReviewWrite = findViewById(R.id.btnReviewWrite);

        imgReviewWritePoster = findViewById(R.id.imgReviewWritePoster);
        txtReviewWriteTitle = findViewById(R.id.txtReviewWriteTitle);
        txtReviewWriteDate = findViewById(R.id.txtReviewWriteDate);
        txtReviewWritePlace = findViewById(R.id.txtReviewWritePlace);

        ratingBarReview = findViewById(R.id.ratingBarReview);
        imgReviewPhoto = findViewById(R.id.imgReviewPhoto);
        editReviewWrite = findViewById(R.id.editReviewWrite);
        txtReviewRating = findViewById(R.id.txtReviewRating);


        // 공연 정보 가져오기
        prfName = getIntent().getStringExtra("prfName"); // 공연 이름
        txtReviewWriteTitle.setText(prfName);
        prfodDate = getIntent().getStringExtra("prfodDate"); // 공연 기간
        txtReviewWriteDate.setText(prfodDate);
        prfPlace = getIntent().getStringExtra("prfPlace"); // 공연 장소
        txtReviewWritePlace.setText(prfPlace);

        imgUrl = getIntent().getStringExtra("Url"); // 공연 포스터

        Glide.with(MyReviewWriteActivity.this).load(imgUrl)
                .into(imgReviewWritePoster);


        // 이전 화면 이동 클릭시
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // 별점
        ratingBarReview.setStepSize((float) 0.5); // 별 색 1칸씩 줄어들고 늘어남, 0.5는 반칸씩
        ratingBarReview.setRating((float) 2.5); // 처음 보여줄 때 (색 X) default 값 0
        ratingBarReview.setIsIndicator(false); // true 별점만 표시 사용자 변경 불가, false 사용자 변경가능

        ratingBarReview.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                txtReviewRating.setText("별점 감사합니다!");

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


        // 리뷰작성 버튼 클릭시
        btnReviewWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });



    }

}