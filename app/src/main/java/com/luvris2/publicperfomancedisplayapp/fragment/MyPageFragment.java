package com.luvris2.publicperfomancedisplayapp.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

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
import com.luvris2.publicperfomancedisplayapp.ui.LikeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

    private String getPackageName;

    public MyPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPageFragment newInstance(String param1, String param2) {
        MyPageFragment fragment = new MyPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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


        // 우저정보 화면에 보여주기

        loadUserInfo();

        // 로그아웃
        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("로그아웃 하시겠습니까?");
                alert.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        userLogout();
                    }
                });

                alert.setNegativeButton("아니요", null);
                alert.show();

            }
        });

        // 내 정보 수정버튼
        imgEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                YoYo.with(Techniques.Pulse).duration(400).repeat(0).playOn(imgEditUser);

                Intent intent = new Intent(getActivity(), UserEditActivity.class);
                startActivity(intent);

            }
        });

        // 내 별점 (리뷰) 버튼
        imgMypageRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyReviewActivity.class);
                startActivity(intent);
            }
        });


        // 내가 작성한 글 (자유게시판)
        imgMypagePosting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PostingMyActivity.class);
                startActivity(intent);
            }
        });

        // 좋아요
        imgMypageLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LikeActivity.class);
                startActivity(intent);
            }
        });
        
//        // 취향분석
//        imgMypageAnalysis.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        // 피드백보내기
        btnMypageFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                String[] address = {"email@gmail.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT, "보내질 email 제목");
                email.putExtra(Intent.EXTRA_TEXT, "보낼 email 내용을 미리 적어 놓을 수 있습니다.\n");
                startActivity(email);
            }
        });


        // 앱 별점주기 (플레이스토어)
        btnMypageApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent  =  new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="));
                startActivity(browserIntent);
            }
        });


        // 이 위에서 기능 작업하기
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
            public void onResponse(Call<UserRes> call, Response<UserRes> response) {

                // 200 OK 일 때,
                if (response.isSuccessful()) {

                    //TODO: 회원정보 넣어야 됨

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
            public void onFailure(Call<UserRes> call, Throwable t) {
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
            public void onResponse(Call<UserRes> call, Response<UserRes> response) {
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
            public void onFailure(Call<UserRes> call, Throwable t) {
                // 네트워크 자체 문제로 실패!
                dismissProgress();
            }
        });
    }


}




