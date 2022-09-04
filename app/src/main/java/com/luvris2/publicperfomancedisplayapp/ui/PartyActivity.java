package com.luvris2.publicperfomancedisplayapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.adapter.PartyAdapter;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.api.UserApi;
import com.luvris2.publicperfomancedisplayapp.config.Config;
import com.luvris2.publicperfomancedisplayapp.fragment.MyPageFragment;
import com.luvris2.publicperfomancedisplayapp.model.PartyData;
import com.luvris2.publicperfomancedisplayapp.model.User;
import com.luvris2.publicperfomancedisplayapp.model.UserRes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PartyActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText editChat;
    private Button btnSend;

    private String myNickName;

    // 어댑터, 리스트
    public PartyAdapter adapter;
    private ArrayList<PartyData> partyDataList = new ArrayList<>();

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference commandsRef = rootRef.child("rooms").child("chat");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);

        editChat = findViewById(R.id.editChat);
        btnSend = findViewById(R.id.btnSend);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PartyActivity.this));

        loadUserInfo();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = editChat.getText().toString(); //msg

                if(msg != null) {
                    PartyData chat = new PartyData();
                    chat.setNickname(adapter.getMyNickName());
                    chat.setMsg(msg);
                    commandsRef.push().setValue(chat);
                }

                editChat.setText("");
            }
        });

        adapter = new PartyAdapter(PartyActivity.this, partyDataList);

        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);

        commandsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("CHATCHAT", dataSnapshot.getValue().toString());
                PartyData chat = dataSnapshot.getValue(PartyData.class);
                adapter.addChat(chat);
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
                    String userInfo = data.getUserInfo().getNickname();
                    adapter.setMyNickName(userInfo);

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