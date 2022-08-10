package com.luvris2.publicperfomancedisplayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class RegisterActivity extends AppCompatActivity {

    EditText editNickname;
    EditText editName;
    EditText editAge;
    EditText editEmail;
    EditText editPassword;
    RadioGroup radioGender;
    Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editNickname = findViewById(R.id.editNickname);
        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        radioGender = findViewById(R.id.radioGender);
        btnRegister = findViewById(R.id.btnRegister);

    }
}