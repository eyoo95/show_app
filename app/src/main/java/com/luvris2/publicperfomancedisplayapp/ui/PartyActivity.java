package com.luvris2.publicperfomancedisplayapp.ui;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.adapter.PartyAdapter;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.api.UserApi;
import com.luvris2.publicperfomancedisplayapp.config.Config;
import com.luvris2.publicperfomancedisplayapp.model.PartyData;
import com.luvris2.publicperfomancedisplayapp.model.PartyRoom;
import com.luvris2.publicperfomancedisplayapp.model.User;
import com.luvris2.publicperfomancedisplayapp.model.UserRes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PartyActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    public  RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<PartyData> partyList;
    private List<PartyData> partyDataList = new ArrayList<>();

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editorPreferences;

    private EditText editChat;
    private Button btnSend;
    private ImageView imgBack;
    private DatabaseReference myRef;

    private String myNickName;
    private int userId;
    private String myUserId;

    PartyRoom partyRoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);

        mPreferences = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        editorPreferences = mPreferences.edit();

        imgBack = findViewById(R.id.imgBack);
        btnSend = findViewById(R.id.btnSend);
        editChat = findViewById(R.id.editChat);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        partyRoom = (PartyRoom) getIntent().getSerializableExtra("partyRoom");

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        // 스택프롬엔드 ()
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("room").child("party").child(partyRoom.getId() + "");

        //caution!!!
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("CHATCHAT", dataSnapshot.getValue().toString());
                Log.d("CHATCHAT2", dataSnapshot.child("room").child("party").child(partyRoom.getId() + "").getKey().toString());

                mAdapter = new PartyAdapter(PartyActivity.this, partyDataList, mPreferences.getInt("userId", 0));
                mRecyclerView.setAdapter(mAdapter);

                PartyData party = dataSnapshot.getValue(PartyData.class);
                ((PartyAdapter) mAdapter).addChat(party);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadUserInfo() {

        Retrofit retrofit = NetworkClient.getRetrofitClient(PartyActivity.this);
        UserApi api = retrofit.create(UserApi.class);

        String accessToken = mPreferences.getString("accessToken", "");

        Call<UserRes> call = api.getUserInfo("Bearer " + accessToken);
        call.enqueue(new Callback<UserRes>() {
            @Override // 성공했을 때
            public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                // 200 OK 일 때,
                if (response.isSuccessful()) {

                    //TODO: 회원정보 넣어야 됨

                    UserRes data = response.body();
                    User userInfo = data.getUserInfo();

                    myNickName = userInfo.getNickname();

                    userId = userInfo.getUserId();

                    editorPreferences.putInt("userId", userId);
                    editorPreferences.apply();

                    Log.i("myuserId", mPreferences.getInt("userId", 0) + "");

                    btnSend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String msg = editChat.getText().toString(); //msg

                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String createdAt = formatter.format(calendar.getTime()).toString();
                            if(msg != null) {
                                PartyData party = new PartyData();
                                party.setUserId(mPreferences.getInt("userId", 0));
                                party.setNickname(myNickName);
                                party.setMsg(msg);
                                party.setCreatedAt(createdAt);
                                myRef.push().setValue(party);

                                Log.i("TAG", myNickName + "");
                                Log.i("TAG", userId + "");
                            }

                            if (msg == null) {

                            }

                            editChat.setText("");

                        }
                    });


                } else {
                    Toast.makeText(getApplication(), "에러 발생 : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override // 실패했을 때
            public void onFailure(Call<UserRes> call, Throwable t) {
                // 네트워크 자체 문제로 실패!
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadUserInfo();
    }
}
