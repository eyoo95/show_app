package com.luvris2.publicperfomancedisplayapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.api.PartyApi;
import com.luvris2.publicperfomancedisplayapp.config.Config;
import com.luvris2.publicperfomancedisplayapp.model.PartyRoom;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PartyCreateActivity extends AppCompatActivity {

    private ImageView imgBack;
    private EditText editTitle;
    private EditText editPrfnm;
    private Button btnCreate;

    private String title;
    private String prfnm;

    private ArrayList<PartyRoom> partyRoomList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_create);

        imgBack = findViewById(R.id.imgBack);
        editTitle = findViewById(R.id.editTitle);
        editPrfnm = findViewById(R.id.editPrfnm);
        btnCreate = findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = editTitle.getText().toString().trim();
                prfnm = editPrfnm.getText().toString().trim();

                Retrofit retrofit = NetworkClient.getRetrofitClient(PartyCreateActivity.this);
                PartyApi api = retrofit.create(PartyApi.class);

                PartyRoom partyRoom = new PartyRoom(prfnm, title);

                SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);

                String token = sp.getString("accessToken", "");

                Call<PartyRoom> call = api.addPartyRoom("Bearer "+ token, partyRoom);

                call.enqueue(new Callback<PartyRoom>() {
                    @Override
                    public void onResponse(Call<PartyRoom> call, Response<PartyRoom> response) {
                        if(response.isSuccessful()){

                            Intent intent = new Intent(PartyCreateActivity.this, PartyMainActivity.class);
                            startActivity(intent);

                            finish();
                        } else {
                            Toast.makeText(PartyCreateActivity.this, "이미 파티를 생성하셨습니다.", Toast.LENGTH_SHORT).show();

                            finish();
                        }

                    }

                    @Override
                    public void onFailure(Call<PartyRoom> call, Throwable t) {

                    }
                });
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PartyCreateActivity.this, PartyMainActivity.class);
                startActivity(intent);

                finish();
            }
        });
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//        Intent intent = new Intent(PartyCreateActivity.this, PartyMainActivity.class);
//        startActivity(intent);
//
//        finish();
//    }
}