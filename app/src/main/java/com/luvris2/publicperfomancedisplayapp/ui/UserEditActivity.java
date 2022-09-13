package com.luvris2.publicperfomancedisplayapp.ui;

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

import com.luvris2.publicperfomancedisplayapp.R;
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
    Button btnNickname;
    Button btnAge;
    Button btnPassword;
    Button btnGender;
    private String nickname;
    private String password;
    private int age;
    private int gender;

    private String myNickname;
    private String myEmail;
    private int myAge;
    private int myGender;

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
        btnNickname = findViewById(R.id.btnNickname);
        btnAge = findViewById(R.id.btnAge);
        btnPassword = findViewById(R.id.btnPassword);
        btnGender = findViewById(R.id.btnGender);
        radioGender = findViewById(R.id.radioGender);

        loadUserInfo();

        // 비밀번호 변경
        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // 비밀번호 - 길이체크
                password = editPassword.getText().toString().trim();
                if (password.length() < 4 || password.length() > 18) {
                    Toast.makeText(UserEditActivity.this, "비밀번호의 길이는 4자이상 18자 이하로만 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder alert = new AlertDialog.Builder(UserEditActivity.this);
                alert.setTitle("비밀번호를 바꾸시겠습니까?");
                alert.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       changePassword();
                    }
                });

                alert.setNegativeButton("아니요", null);
                alert.show();

            }
        });

        // 닉네임 변경
        btnNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 닉네임 - 빈 문자열, 길이 체크
                nickname = editNickname.getText().toString().trim();
                if (nickname.isEmpty() || nickname.length() < 2 || nickname.length() > 12) {
                    Toast.makeText(UserEditActivity.this, "닉네임의 길이는 2자이상 12자 이하로만 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder alert = new AlertDialog.Builder(UserEditActivity.this);
                alert.setTitle("닉네임을 "+nickname+"(으)로 바꾸시겠습니까?");
                alert.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        changeNickname();
                    }
                });

                alert.setNegativeButton("아니요", null);
                alert.show();

            }
        });

        // 나이 변경
        btnAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                age = Integer.parseInt(editAge.getText().toString());
                if (age <= 0) {
                    Toast.makeText(UserEditActivity.this, "나이를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder alert = new AlertDialog.Builder(UserEditActivity.this);
                alert.setTitle("나이를 "+age+"세로 바꾸시겠습니까?");
                alert.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        changeAge();
                    }
                });

                alert.setNegativeButton("아니요", null);
                alert.show();

            }
        });

        // 성별 변경
        btnGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 성별
                int checkedId = radioGender.getCheckedRadioButtonId();
                if (checkedId == R.id.radioMale) {
                    gender = 1;
                } else if (checkedId == R.id.radioFemale) {
                    gender = 0;
                } else {
                    Toast.makeText(UserEditActivity.this, "성별을 선택하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder alert = new AlertDialog.Builder(UserEditActivity.this);
                alert.setTitle("성별을 바꾸시겠습니까?");
                alert.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        changeGender();
                    }
                });

                alert.setNegativeButton("아니요", null);
                alert.show();

            }
        });


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

    // 비밀번호 변경기능
    private void changePassword(){

        // 프로그레스 다이얼로그
        showProgress("비밀번호를 변경합니다..");

        Retrofit retrofit = NetworkClient.getRetrofitClient(UserEditActivity.this);
        UserApi api = retrofit.create(UserApi.class);
        User user = new User ();
        user.setPassword(password);

        SharedPreferences sp = UserEditActivity.this.getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        Call<UserRes> call = api.editpassword("Bearer " + accessToken, user);

        call.enqueue(new Callback<UserRes>() {
            @Override // 성공했을 때
            public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                dismissProgress();

                // 200 OK 일 때,
                if (response.isSuccessful()) {
                    editPassword.setText("");
                    Toast.makeText(UserEditActivity.this, "비밀번호를 변경했습니다." , Toast.LENGTH_SHORT).show();

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

    // 닉네임 변경기능
    private void changeNickname(){



        // 프로그레스 다이얼로그
        showProgress("닉네임을 변경합니다..");

        Retrofit retrofit = NetworkClient.getRetrofitClient(UserEditActivity.this);
        UserApi api = retrofit.create(UserApi.class);
        User user = new User();
        user.setNickname(nickname);

        SharedPreferences sp = UserEditActivity.this.getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        Call<UserRes> call = api.editnickname("Bearer " + accessToken, user);

        call.enqueue(new Callback<UserRes>() {
            @Override // 성공했을 때
            public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                dismissProgress();

                // 200 OK 일 때,
                if (response.isSuccessful()) {
                    editPassword.setText("");
                    Toast.makeText(UserEditActivity.this, "닉네임을 변경했습니다." , Toast.LENGTH_SHORT).show();

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

    // 나이 변경기능
    private void changeAge(){

        // 프로그레스 다이얼로그
        showProgress("나이를 수정합니다..");

        Retrofit retrofit = NetworkClient.getRetrofitClient(UserEditActivity.this);
        UserApi api = retrofit.create(UserApi.class);
        User user = new User();
        user.setAge(age);

        SharedPreferences sp = UserEditActivity.this.getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        Call<UserRes> call = api.editage("Bearer " + accessToken, user);

        call.enqueue(new Callback<UserRes>() {
            @Override // 성공했을 때
            public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                dismissProgress();

                // 200 OK 일 때,
                if (response.isSuccessful()) {
                    editPassword.setText("");
                    Toast.makeText(UserEditActivity.this, "나이를 수정했습니다." , Toast.LENGTH_SHORT).show();

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

    // 성별 변경기능
    private void changeGender(){

        // 프로그레스 다이얼로그
        showProgress("성별을 수정합니다..");

        Retrofit retrofit = NetworkClient.getRetrofitClient(UserEditActivity.this);
        UserApi api = retrofit.create(UserApi.class);
        User user = new User();
        user.setGender(gender);

        SharedPreferences sp = UserEditActivity.this.getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        Call<UserRes> call = api.editgender("Bearer " + accessToken, user);

        call.enqueue(new Callback<UserRes>() {
            @Override // 성공했을 때
            public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                dismissProgress();

                // 200 OK 일 때,
                if (response.isSuccessful()) {
                    editPassword.setText("");
                    Toast.makeText(UserEditActivity.this, "성별을 수정했습니다." , Toast.LENGTH_SHORT).show();

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

                    SharedPreferences sp = UserEditActivity.this.getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("accessToken", null);
                    editor.apply();

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

    // 회원정보 불러오는 기능
    private void loadUserInfo() {

        Retrofit retrofit = NetworkClient.getRetrofitClient(UserEditActivity.this);
        UserApi api = retrofit.create(UserApi.class);

        SharedPreferences sp = UserEditActivity.this.getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
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

                    // 여기에 넣는 기능
                    editNickname.setText(myNickname+"");
                    editAge.setText(myAge+"");

                } else {
                    Toast.makeText(UserEditActivity.this, "에러 발생 : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override // 실패했을 때
            public void onFailure(Call<UserRes> call, Throwable t) {
                // 네트워크 자체 문제로 실패!
            }
        });
    }

}