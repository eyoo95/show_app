package com.luvris2.publicperfomancedisplayapp.ui;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private EditText editChat;
    private Button btnSend;
    private DatabaseReference myRef;

    private String myNickName;
    private int userId;

    PartyRoom partyRoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);

        loadUserInfo();

        btnSend = findViewById(R.id.btnSend);
        editChat = findViewById(R.id.editChat);

        partyRoom = (PartyRoom) getIntent().getSerializableExtra("partyRoom");

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PartyAdapter(PartyActivity.this, partyDataList, 0);
        mRecyclerView.setAdapter(mAdapter);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("room").child("party").child(partyRoom.getId() + "");


        //caution!!!
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("CHATCHAT", dataSnapshot.getValue().toString());
                Log.d("CHATCHAT2", dataSnapshot.child("room").child("party").child(partyRoom.getId() + "").getKey().toString());
                Log.d("CHATCHAT3", dataSnapshot.child("room").child("party").child(partyRoom.getId() + "").child(userId + "").getKey().toString());
                String myUserId = dataSnapshot.child("room").child("party").child(partyRoom.getId() + "").child(userId + "").getKey();
                int intUserId = Integer.parseInt(myUserId);
                mAdapter = new PartyAdapter(PartyActivity.this, partyDataList, intUserId);
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

        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
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

                    myNickName = userInfo.getNickname();

                    userId = userInfo.getUserId();

                    btnSend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String msg = editChat.getText().toString(); //msg

                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String createdAt = formatter.format(calendar.getTime()).toString();
                            if(msg != null) {
                                PartyData party = new PartyData();
                                party.setUserId(userId);
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
}
