package com.luvris2.publicperfomancedisplayapp.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.luvris2.publicperfomancedisplayapp.ui.LoginActivity;
import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.ui.MyReviewActivity;
import com.luvris2.publicperfomancedisplayapp.ui.UserEditActivity;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.api.UserApi;
import com.luvris2.publicperfomancedisplayapp.config.Config;
import com.luvris2.publicperfomancedisplayapp.model.User;
import com.luvris2.publicperfomancedisplayapp.model.UserRes;
import com.luvris2.publicperfomancedisplayapp.ui.PostingMyActivity;
import com.luvris2.publicperfomancedisplayapp.ui.MyPageLikeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyPageFragment extends Fragment {
    TextView txtLogout;
    ImageView imgEditUser;
    TextView txtNickname;
    TextView txtEmail;

    ImageView imgMypageRating; // 별점 (리뷰)
    ImageView imgMypagePosting; // 내가 쓴 글 (자유게시판)
    ImageView imgMypageLike; // 좋아요
    ImageView imgMypageAnalysis; // 취향분석

    Button btnMypageFeedback; // 앱 피드백 보내기
    Button btnMypageApp; // 앱 별점주기

    // 프로그레스 다이얼로그
    private ProgressDialog dialog;
    private String myEmail;
    private String myNickname;
    private int myAge;
    private int myGender;

    public MyPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_my_page, container, false);

        txtLogout = rootView.findViewById(R.id.txtLogout);
        imgEditUser = rootView.findViewById(R.id.imgEditUser);
        txtNickname = rootView.findViewById(R.id.txtMyNickname);
        txtEmail = rootView.findViewById(R.id.txtMyEmail);

        imgMypageRating = rootView.findViewById(R.id.imgMypageRating);
        imgMypagePosting = rootView.findViewById(R.id.imgMypagePosting);
        imgMypageLike = rootView.findViewById(R.id.imgMypageLike);
        imgMypageAnalysis = rootView.findViewById(R.id.imgMypageAnalysis);

        btnMypageFeedback = rootView.findViewById(R.id.btnMypageFeedback); // 앱 피드백
        btnMypageApp = rootView.findViewById(R.id.btnMypageApp); // 앱 별점

        // 유저 정보 화면에 보여주기
        loadUserInfo();

        // 로그아웃
        txtLogout.setOnClickListener(view -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("로그아웃 하시겠습니까?");
            alert.setPositiveButton("네", (dialogInterface, i) -> userLogout());

            alert.setNegativeButton("아니요", null);
            alert.show();

        });

        // 내 정보 수정버튼
        imgEditUser.setOnClickListener(view -> {
            YoYo.with(Techniques.Pulse).duration(400).repeat(0).playOn(imgEditUser);
            Intent intent = new Intent(getActivity(), UserEditActivity.class);
            startActivity(intent);
        });

        // 내 별점 (리뷰) 버튼
        imgMypageRating.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), MyReviewActivity.class);
            startActivity(intent);
        });

        // 내가 작성한 글 (자유게시판)
        imgMypagePosting.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), PostingMyActivity.class);
            startActivity(intent);
        });

        // 좋아요
        imgMypageLike.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), MyPageLikeActivity.class);
            startActivity(intent);
        });

        // 피드백보내기
        btnMypageFeedback.setOnClickListener(view -> {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.setType("plain/text");
            String[] address = {"email@gmail.com"};
            email.putExtra(Intent.EXTRA_EMAIL, address);
            email.putExtra(Intent.EXTRA_SUBJECT, "보내질 email 제목");
            email.putExtra(Intent.EXTRA_TEXT, "보낼 email 내용을 미리 적어 놓을 수 있습니다.\n");
            startActivity(email);
        });

        // 앱 별점주기 (플레이스토어)
        btnMypageApp.setOnClickListener(view -> {
            Intent browserIntent  =  new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="));
            startActivity(browserIntent);
        });


        // todo : 이 위에서 기능 작업하기
        return rootView;
    }

    // 다이얼로그 나오는 함수 만들기
    void showProgress(String message) {
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
        dialog.show();
    }

    // 다이얼로그를 없애기
    void dismissProgress() {
        dialog.dismiss();
    }

    // 회원정보 불러오는 기능
    private void loadUserInfo() {

        Retrofit retrofit = NetworkClient.getRetrofitClient(getContext());
        UserApi api = retrofit.create(UserApi.class);

        SharedPreferences sp = getActivity().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        Call<UserRes> call = api.getUserInfo("Bearer " + accessToken);

        call.enqueue(new Callback<UserRes>() {
            @Override // 성공했을 때
            public void onResponse(@NonNull Call<UserRes> call, @NonNull Response<UserRes> response) {

                // 200 OK 일 때,
                if (response.isSuccessful()) {
                    UserRes data = response.body();
                    User userInfo = data.getUserInfo();
                    myEmail = userInfo.getEmail();
                    myNickname = userInfo.getNickname();
                    myAge = userInfo.getAge();
                    myGender = userInfo.getGender();

                    txtNickname.setText(myNickname);
                    txtEmail.setText(myEmail);

                } else {
                    Toast.makeText(getActivity(), "에러 발생 : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override // 실패했을 때
            public void onFailure(@NonNull Call<UserRes> call, @NonNull Throwable t) {
                // 네트워크 자체 문제로 실패!
            }
        });
    }

    // 로그아웃 기능
    private void userLogout() {
        // 프로그레스 다이얼로그
        showProgress(getString(R.string.dialog_logout));

        Retrofit retrofit = NetworkClient.getRetrofitClient(getContext());
        UserApi api = retrofit.create(UserApi.class);

        SharedPreferences sp = getActivity().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        Call<UserRes> call = api.logout("Bearer " + accessToken);

        call.enqueue(new Callback<UserRes>() {
            @Override // 성공했을 때
            public void onResponse(@NonNull Call<UserRes> call, @NonNull Response<UserRes> response) {
                dismissProgress();

                // 200 OK 일 때,
                if (response.isSuccessful()) {

                    SharedPreferences sp = getActivity().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("accessToken", null);
                    editor.apply();

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);

                    getActivity().finish();

                } else {
                    Toast.makeText(getActivity(), "에러 발생 : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override // 실패했을 때
            public void onFailure(@NonNull Call<UserRes> call, @NonNull Throwable t) {
                // 네트워크 자체 문제로 실패!
                dismissProgress();
            }
        });
    }


}




