package com.luvris2.publicperfomancedisplayapp;

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

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText editNickname;
    EditText editName;
    EditText editAge;
    EditText editEmail;
    EditText editPassword;
    RadioGroup radioGender;
    Button btnRegister;
    TextView txtLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editName = findViewById(R.id.editName);
        editNickname = findViewById(R.id.editNickname);
        radioGender = findViewById(R.id.radioGender);
        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editEmail.getText().toString().trim();
                Pattern pattern = Patterns.EMAIL_ADDRESS;
                if (pattern.matcher(email).matches() == false) {
                    Toast.makeText(RegisterActivity.this, "이메일 제대로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String password = editPassword.getText().toString().trim();
                if (password.length() < 4 || password.length() > 12) {
                    Toast.makeText(RegisterActivity.this, "비번길이는 4자이상 12자 이하로만 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String name = editName.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String nickname = editNickname.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

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
            }

        }
    }
}