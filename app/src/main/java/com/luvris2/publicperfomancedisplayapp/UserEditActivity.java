package com.luvris2.publicperfomancedisplayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.api.UserApi;
import com.luvris2.publicperfomancedisplayapp.config.Config;
import com.luvris2.publicperfomancedisplayapp.model.User;
import com.luvris2.publicperfomancedisplayapp.model.UserRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserEditActivity extends AppCompatActivity {

    EditText editNickname;
    EditText editAge;
    EditText editPassword;
    RadioGroup radioGender;
    Button btnWithdrawal;

    // 네트워크 처리 보여주는 프로그레스 다이얼로그
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
        // 화면연결
        btnWithdrawal = findViewById(R.id.btnWithdrawal);
        editPassword = findViewById(R.id.editPassword);
        editNickname = findViewById(R.id.editNickname);
        editAge = findViewById(R.id.editAge);
        radioGender = findViewById(R.id.radioGender);


        // 회원탈퇴
        btnWithdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(UserEditActivity.this);
                alert.setTitle("정말로 회원탈퇴 하시겠습니까?");
                alert.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        userWithdrawal();
                    }
                });

                alert.setNegativeButton("아니요", null);
                alert.show();

            }
        });

    }

    // 다이얼로그 나오는 함수 만들기
    void showProgress(String message){
        dialog = new ProgressDialog(UserEditActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
        dialog.show();
    }

    // 다이얼로그를 없애기
    void dismissProgress(){
        dialog.dismiss();
    }

    // 회원탈퇴 기능
    private void userWithdrawal() {
        // 프로그레스 다이얼로그
        showProgress("회원가입 화면으로 이동합니다..");

        Retrofit retrofit = NetworkClient.getRetrofitClient(UserEditActivity.this);
        UserApi api = retrofit.create(UserApi.class);

        SharedPreferences sp = UserEditActivity.this.getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        Call<UserRes> call = api.withdrawal("Bearer " + accessToken);

        call.enqueue(new Callback<UserRes>() {
            @Override // 성공했을 때
            public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                dismissProgress();

                // 200 OK 일 때,
                if (response.isSuccessful()) {

                    Intent intent = new Intent(UserEditActivity.this, RegisterActivity.class);
                    startActivity(intent);

                    finish();

                } else {
                    Toast.makeText(UserEditActivity.this, "에러 발생 : " + response.code(), Toast.LENGTH_SHORT).show();
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