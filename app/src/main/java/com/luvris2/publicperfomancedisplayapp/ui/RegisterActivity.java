package com.luvris2.publicperfomancedisplayapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.luvris2.publicperfomancedisplayapp.LoginActivity;
import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.api.UserApi;
import com.luvris2.publicperfomancedisplayapp.config.Config;
import com.luvris2.publicperfomancedisplayapp.model.UserRes;
import com.luvris2.publicperfomancedisplayapp.model.Users;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    EditText editNickname;
    EditText editName;
    EditText editAge;
    EditText editEmail;
    EditText editPassword;
    RadioGroup radioGender;
    Button btnRegister;

    TextView txtLogin;

    // 네트워크 처리 보여주는 프로그레스 다이얼로그
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 화면연결
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editName = findViewById(R.id.editName);
        editNickname = findViewById(R.id.editNickname);
        editAge = findViewById(R.id.editAge);
        radioGender = findViewById(R.id.radioGender);
        btnRegister = findViewById(R.id.btnLogin);

        txtLogin = findViewById(R.id.txtLogin);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);

                finish();
            }
        });

        // 회원가입 버튼
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 이메일 - 형식체크
                String email = editEmail.getText().toString().trim();
                Pattern pattern = Patterns.EMAIL_ADDRESS;
                if (pattern.matcher(email).matches() == false) {
                    Toast.makeText(RegisterActivity.this, "이메일 제대로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 비밀번호 - 길이체크
                String password = editPassword.getText().toString().trim();
                if (password.length() < 4 || password.length() > 12) {
                    Toast.makeText(RegisterActivity.this, "비번길이는 4자이상 12자 이하로만 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 이름 - 빈 문자열인지만 체크
                String name = editName.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 닉네임 - 빈 문자열인지만 체크
                String nickname = editNickname.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 성별
                String gender;
                int checkedId = radioGender.getCheckedRadioButtonId();
                if (checkedId == R.id.radioMale) {
                    gender = "Male";
                } else if (checkedId == R.id.radioFemale) {
                    gender = "Female";
                } else {
                    Toast.makeText(RegisterActivity.this, "선택하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 나이
                int age = Integer.parseInt(editAge.getText().toString());
                if (age >= 0) {
                    Toast.makeText(RegisterActivity.this, "나이를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                // 프로그레스 다이얼로그
                showProgress(getString(R.string.dialog_register));

                Retrofit retrofit = NetworkClient.getRetrofitClient(RegisterActivity.this);

                UserApi api = retrofit.create(UserApi.class);
                Users user = new Users (email, password, name, nickname);

                Call<UserRes> call = api.register(user);
                call.enqueue(new Callback<UserRes>() {
                    @Override // 성공했을 때
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                        dismissProgress(); // 동글동글 돌아가서 '회원가입 중입니다.' 없애는 함수!

                        // 200 OK 일 때,
                        if (response.isSuccessful()){

                            UserRes registerRes = response.body();

                            SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("accessToken", registerRes.getAccess_token());
                            editor.apply();

                            finish();

                        } else if (response.code() == 400){

                        } else {
                            Toast.makeText(RegisterActivity.this, "에러 발생 : "+response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override // 실패했을 때
                    public void onFailure(Call<UserRes> call, Throwable t) {
                        // 네트워크 자체 문제로 실패!
                        dismissProgress();
                    }
                });

            }
        });

    }

    // 다이얼로그 나오는 함수 만들기
    void showProgress(String message){
        dialog = new ProgressDialog(RegisterActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
        dialog.show();
    }

    // 다이얼로그를 없애기
    void dismissProgress(){
        dialog.dismiss();
    }
}