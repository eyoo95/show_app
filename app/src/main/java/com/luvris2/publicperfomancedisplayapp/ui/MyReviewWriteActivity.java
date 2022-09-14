package com.luvris2.publicperfomancedisplayapp.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.api.NaverClovaApi;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.api.ReviewApi;
import com.luvris2.publicperfomancedisplayapp.config.Config;
import com.luvris2.publicperfomancedisplayapp.model.Review;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

// 행사 상세 정보에서 리뷰 작성 눌렀을 때 (리뷰 작성하는 액티비티) : 리뷰 작성, 별점, 사진 추가
public class MyReviewWriteActivity extends AppCompatActivity {
    ImageView imgBack; // 이전 화면 이동
    Button btnReviewWrite; // 리뷰 작성 버튼

    // 공연 관련
    ImageView imgReviewWritePoster; // (공연정보) 공연 포스터
    TextView txtReviewWriteTitle, txtReviewWriteDate, txtReviewWritePlace; // (공연정보) 공연 제목, 날짜, 장소
    String prfId, prfName, prfDate, prfPlace, prfPoster; // (공연정보) 공연 ID, 제목, 날짜, 장소, 포스터 URL 저장하는 변수

    // 리뷰 관련
    RatingBar ratingBarReview; // 래이팅 바
    ImageView imgReviewPhoto; // 리뷰 사진
    ImageView imgVerified; // 인증 여부
    EditText editReviewTitle, editReviewContent; // 리뷰 제목과 내용
    TextView txtReviewRating, txtVerified;

    // 별점과 리뷰 내용 저장 변수
    double rating;
    String title, content;

    // 사진 관련
    private File photoFile;
    String imgUrl = null; // 유저가 업로드한 사진의 url 주소
    int verified = 0; // 업로드한 사진의 실제 관람 인증 여부

    // 진행사항을 보여줄 프로그레스 다이얼로그
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review_write);

        imgBack = findViewById(R.id.imgBack);
        btnReviewWrite = findViewById(R.id.btnReviewWrite);

        imgReviewWritePoster = findViewById(R.id.imgReviewWritePoster);
        txtReviewWriteTitle = findViewById(R.id.txtReviewWriteTitle);
        txtReviewWriteDate = findViewById(R.id.txtReviewWriteDate);
        txtReviewWritePlace = findViewById(R.id.txtReviewWritePlace);

        ratingBarReview = findViewById(R.id.ratingBarReview);
        imgReviewPhoto = findViewById(R.id.imgReviewPhoto);
        imgVerified = findViewById(R.id.imgVerified);
        editReviewTitle = findViewById(R.id.editReviewTitle);
        editReviewContent = findViewById(R.id.editReviewContent);
        txtReviewRating = findViewById(R.id.txtReviewRating);
        txtVerified = findViewById(R.id.txtVerified);

        // 공연 정보 가져오기
        prfId = getIntent().getStringExtra("prfId"); // 공연 ID
        prfName = getIntent().getStringExtra("prfName"); // 공연 이름
        txtReviewWriteTitle.setText(prfName);
        prfDate = getIntent().getStringExtra("prfDate"); // 공연 기간
        txtReviewWriteDate.setText(prfDate);
        prfPlace = getIntent().getStringExtra("prfPlace"); // 공연 장소
        txtReviewWritePlace.setText(prfPlace);
        prfPoster = getIntent().getStringExtra("Url"); // 공연 포스터
        Glide.with(MyReviewWriteActivity.this).load(prfPoster)
                .into(imgReviewWritePoster);

        // 이전 화면 이동 클릭시
        imgBack.setOnClickListener(view -> finish());

        // 별점 기본 설정
        ratingBarReview.setStepSize((float) 0.5); // 별 색 1칸씩 줄어들고 늘어남, 0.5는 반칸씩
        ratingBarReview.setRating((float) 2.5); // 처음 보여줄 때 (색 X) default 값 0
        ratingBarReview.setIsIndicator(false); // true 별점만 표시 사용자 변경 불가, false 사용자 변경가능

        // 별점 클릭시 메시지 출력
        ratingBarReview.setOnRatingBarChangeListener((ratingBar, v, b) -> txtReviewRating.setText("별점 감사합니다!"));

        // 이미지 버튼 클릭 시, 카메라와 앨범 중 선택하여 사진 업로드
        imgReviewPhoto.setOnClickListener(v -> {
            showDialog();
        });

        // 리뷰작성 버튼 클릭시
        btnReviewWrite.setOnClickListener(view -> {
            addReviewData();
        });
    }

    // 리뷰 작성
    void addReviewData() {
        // 리뷰 제목과 내용, 별점 저장
        title = editReviewTitle.getText().toString().trim();
        content = editReviewContent.getText().toString().trim();
        rating = ratingBarReview.getRating();

        // 사진과 내용 미입력시 확인 문구
        if ( title.isEmpty() ) {
            Toast.makeText(getApplicationContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        } else if ( photoFile == null ) {
            Toast.makeText(getApplicationContext(), "사진을 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        showProgressBar("리뷰를 저장 중입니다...");

        // 네트워크로 데이터 전송, Retrofit 객체 생성
        Retrofit retrofit = NetworkClient.getRetrofitClient(MyReviewWriteActivity.this);
        ReviewApi api = retrofit.create(ReviewApi.class);

        // 헤더에 설정 할 데이터 확인, 공유 저장소에 저장되어있는 토큰 호출
        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String token = sp.getString("accessToken", "");

        Review addReview = new Review(title, content, imgUrl, prfName, verified, rating);

        // API 요청
        Call<Review> call = api.addReview("Bearer "+token, prfId, addReview);

        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                // 200 OK, 네트워크 정상 응답
                if (response.isSuccessful()) {
                    Review res = response.body();
                    Log.i("MyTest", "MyReviewWrite addReviewData : "+res);
                    Toast.makeText(getApplicationContext(), "리뷰가 작성되었습니다.", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    dismissProgressBar();
                    Toast.makeText(getApplicationContext(), "알 수 없는 에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Toast.makeText(getApplicationContext(), ""+t, Toast.LENGTH_LONG).show();
                dismissProgressBar();
            }
        });
    }

    // 티켓 인증 여부 확인 메소드
    void verifiedTicket() {
        showProgressBar("관람 인증을 확인중입니다...");

        // 네트워크로 데이터 전송, Retrofit 객체 생성
        Retrofit retrofit = NetworkClient.getRetrofitClient(MyReviewWriteActivity.this);
        NaverClovaApi api = retrofit.create(NaverClovaApi.class);

        // 멀티파트로 파일을 보내는 경우 파라미터 생성 방법, (파일명, 파일 타입)
        RequestBody fileBody = RequestBody.create(photoFile, MediaType.parse("image/*"));
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", photoFile.getName(), fileBody);

        // 멀티파트를 텍스트로 보내는 경우 파라미터 보내는 방법 ( 내용, 텍스트 타입)
        RequestBody prfNameBody = RequestBody.create(prfName, MediaType.parse("text/plain"));

        // API 요청
        Call<Review> call = api.imgVerified(prfNameBody, photo);

        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                // 200 OK, 네트워크 정상 응답
                if (response.isSuccessful()) {
                    Review res = response.body();
                    Log.i("MyTest", "MyReviewWrite Verified Ticket : "+ res.getVerified()+" "+res.getImgUrl());

                    // 1 = 관람 인증, 0 = 관람 미인증
                    if ( res.getVerified() == 1 ) {
                        verified = res.getVerified();
                        imgUrl = res.getImgUrl();
                        txtVerified.setText("인증");
                        imgVerified.setImageResource(R.drawable.ic_review_verified_yes);
                        Log.i("MyTest", "MyReviewWrite Verified Ticket Success : "+ res.getVerified()+" "+res.getImgUrl());
                        Toast.makeText(getApplicationContext(), "관람 인증이 확인되었습니다.", Toast.LENGTH_LONG).show();

                    } else {
                        verified = 0;
                        imgUrl = res.getImgUrl();
                        txtVerified.setText("미인증");
                        imgVerified.setImageResource(R.drawable.ic_review_verified_no);
                        Log.i("MyTest", "MyReviewWrite Verified Ticket verify fail : "+ res.getVerified()+" "+res.getImgUrl());
                        Toast.makeText(getApplicationContext(), "인증에 실패하였습니다.\n" +
                                "올바른 티켓 사진으로 업로드를 재시도 해주시거나,\n미인증 상태로 리뷰가 등록됩니다.", Toast.LENGTH_LONG).show(); }
                }
                else {
                    Log.i("MyTest", "MyReviewWrite Verified Ticket Error : " + response.errorBody());
                    Toast.makeText(getApplicationContext(), "알 수 없는 에러가 발생하였습니다."+response.errorBody(), Toast.LENGTH_LONG).show();
                    return;
                }
                dismissProgressBar();
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Log.i("MyTest", "MyReviewWrite Verified Ticket onFailure : " + t);
                Toast.makeText(getApplicationContext(), ""+t, Toast.LENGTH_LONG).show();
                dismissProgressBar();
            }
        });
    }

    // 다이얼로그 출력 메소드
    void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyReviewWriteActivity.this);

        builder.setTitle(R.string.alert_title);

        // strings.xml에서 정의한 배열의 아이템 호출, 인덱스로 접근
        builder.setItems(R.array.alert_photo, (dialogInterface, i) -> {
            if ( i == 0 ) {
                // todo : 사진 찍는 코드 실행
                camera();
            } else if ( i == 1 ) {
                // todo : 사진첩에서 사진 선택
                album();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void camera(){
        int permissionCheck = ContextCompat.checkSelfPermission(
                MyReviewWriteActivity.this, Manifest.permission.CAMERA);

        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MyReviewWriteActivity.this,
                    new String[]{Manifest.permission.CAMERA} ,
                    1000);
            Toast.makeText(MyReviewWriteActivity.this, "카메라 권한 필요합니다.",
                    Toast.LENGTH_SHORT).show();
            return;
        } else {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(i.resolveActivity(MyReviewWriteActivity.this.getPackageManager())  != null  ){

                // 사진의 파일명을 만들기
                String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                photoFile = getPhotoFile(fileName);

                Uri fileProvider = FileProvider.getUriForFile(MyReviewWriteActivity.this,
                        "com.luvris2.publicperfomancedisplayapp.fileprovider", photoFile);
                i.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
                startActivityForResult(i, 100);
            } else{
                Toast.makeText(MyReviewWriteActivity.this, "이 폰에는 카메라 앱이 없습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        }


    }
    private File getPhotoFile(String fileName) {
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try{
            return File.createTempFile(fileName, ".jpg", storageDirectory);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    private void album(){
        if(checkPermission()){
            displayFileChoose();
        }else{
            requestPermission();
        }
    }
    private void requestPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(MyReviewWriteActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(MyReviewWriteActivity.this, "권한 수락이 필요합니다.",
                    Toast.LENGTH_SHORT).show();
        }else{
            ActivityCompat.requestPermissions(MyReviewWriteActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 500);
        }
    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(MyReviewWriteActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_DENIED){
            return false;
        }else{
            return true;
        }
    }

    private void displayFileChoose() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "SELECT IMAGE"), 300);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MyReviewWriteActivity.this, "권한 허가 되었음",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyReviewWriteActivity.this, "아직 승인하지 않았음",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case 500: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MyReviewWriteActivity.this, "권한 허가 되었음",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyReviewWriteActivity.this, "아직 승인하지 않았음",
                            Toast.LENGTH_SHORT).show();
                }

            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100 && resultCode == RESULT_OK){

            Bitmap photo = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

            ExifInterface exif = null;
            try {
                exif = new ExifInterface(photoFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            photo = rotateBitmap(photo, orientation);

            // 압축시킨다. 해상도 낮춰서
            OutputStream os;
            try {
                os = new FileOutputStream(photoFile);
                photo.compress(Bitmap.CompressFormat.JPEG, 50, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
            }

            photo = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

            imgReviewPhoto.setImageBitmap(photo);
            imgReviewPhoto.setScaleType(ImageView.ScaleType.FIT_XY);

        }else if(requestCode == 300 && resultCode == RESULT_OK && data != null &&
                data.getData() != null){

            Uri albumUri = data.getData( );
            String fileName = getFileName( albumUri );
            try {

                ParcelFileDescriptor parcelFileDescriptor = getContentResolver( ).openFileDescriptor( albumUri, "r" );
                if ( parcelFileDescriptor == null ) return;
                FileInputStream inputStream = new FileInputStream( parcelFileDescriptor.getFileDescriptor( ) );
                photoFile = new File( this.getCacheDir( ), fileName );
                FileOutputStream outputStream = new FileOutputStream( photoFile );
                IOUtils.copy( inputStream, outputStream );

//                //임시파일 생성
//                File file = createImgCacheFile( );
//                String cacheFilePath = file.getAbsolutePath( );


                // 압축시킨다. 해상도 낮춰서
                Bitmap photo = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                OutputStream os;
                try {
                    os = new FileOutputStream(photoFile);
                    photo.compress(Bitmap.CompressFormat.JPEG, 60, os);
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                }

                imgReviewPhoto.setImageBitmap(photo);
                imgReviewPhoto.setScaleType(ImageView.ScaleType.FIT_XY);

//                imageView.setImageBitmap( getBitmapAlbum( imageView, albumUri ) );

            } catch ( Exception e ) {
                e.printStackTrace( );
            }
        }
        // todo : 갤러리의 사진을 선택하면 관람 인증 검사
        Log.i("MyTest", "MyReviewWrite showDialog PhotoFile : "+photoFile);
        verifiedTicket();

        super.onActivityResult(requestCode, resultCode, data);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    //앨범에서 선택한 사진이름 가져오기
    public String getFileName( Uri uri ) {
        Cursor cursor = getContentResolver( ).query( uri, null, null, null, null );
        try {
            if ( cursor == null ) return null;
            cursor.moveToFirst( );
            @SuppressLint("Range") String fileName = cursor.getString( cursor.getColumnIndex( OpenableColumns.DISPLAY_NAME ) );
            cursor.close( );
            return fileName;

        } catch ( Exception e ) {
            e.printStackTrace( );
            cursor.close( );
            return null;
        }
    }

    //이미지뷰에 뿌려질 앨범 비트맵 반환
    public Bitmap getBitmapAlbum(View targetView, Uri uri ) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = getContentResolver( ).openFileDescriptor( uri, "r" );
            if ( parcelFileDescriptor == null ) return null;
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor( );
            if ( fileDescriptor == null ) return null;

            int targetW = targetView.getWidth( );
            int targetH = targetView.getHeight( );

            BitmapFactory.Options options = new BitmapFactory.Options( );
            options.inJustDecodeBounds = true;

            BitmapFactory.decodeFileDescriptor( fileDescriptor, null, options );

            int photoW = options.outWidth;
            int photoH = options.outHeight;

            int scaleFactor = Math.min( photoW / targetW, photoH / targetH );
            if ( scaleFactor >= 8 ) {
                options.inSampleSize = 8;
            } else if ( scaleFactor >= 4 ) {
                options.inSampleSize = 4;
            } else {
                options.inSampleSize = 2;
            }
            options.inJustDecodeBounds = false;

            Bitmap reSizeBit = BitmapFactory.decodeFileDescriptor( fileDescriptor, null, options );

            ExifInterface exifInterface = null;
            try {
                if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ) {
                    exifInterface = new ExifInterface( fileDescriptor );
                }
            } catch ( IOException e ) {
                e.printStackTrace( );
            }

            int exifOrientation;
            int exifDegree = 0;

            //사진 회전값 구하기
            if ( exifInterface != null ) {
                exifOrientation = exifInterface.getAttributeInt( ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL );

                if ( exifOrientation == ExifInterface.ORIENTATION_ROTATE_90 ) {
                    exifDegree = 90;
                } else if ( exifOrientation == ExifInterface.ORIENTATION_ROTATE_180 ) {
                    exifDegree = 180;
                } else if ( exifOrientation == ExifInterface.ORIENTATION_ROTATE_270 ) {
                    exifDegree = 270;
                }
            }

            parcelFileDescriptor.close( );
            Matrix matrix = new Matrix( );
            matrix.postRotate( exifDegree );

            Bitmap reSizeExifBitmap = Bitmap.createBitmap( reSizeBit, 0, 0, reSizeBit.getWidth( ), reSizeBit.getHeight( ), matrix, true );
            return reSizeExifBitmap;

        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    // todo : 위치 정보 수신 대기를 위한 프로그레스 다이얼로그
    public void showProgressBar(String message) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("정보 수신 중...");
        progressDialog.setMessage(message);
        progressDialog.show();
    }
    public void dismissProgressBar() {
        progressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 액티비티 종료시 프로그레스 다이얼로그 제거
        if (progressDialog != null) {
            dismissProgressBar();
            progressDialog = null;
        }
    }
}