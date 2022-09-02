package com.luvris2.publicperfomancedisplayapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.adapter.PartyAdapter;
import com.luvris2.publicperfomancedisplayapp.model.PartyData;

import java.util.ArrayList;

public class PartyActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText editChat;
    Button btnSend;

    private String nick = "nick2";

    // 어댑터, 리스트
    PartyAdapter adapter;
    ArrayList<PartyData> partyDataList = new ArrayList<>();

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

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = editChat.getText().toString(); //msg

                if(msg != null) {
                    PartyData chat = new PartyData();
                    chat.setNickname(nick);
                    chat.setMsg(msg);
                    commandsRef.push().setValue(chat);
                }
            }
        });

        adapter = new PartyAdapter(PartyActivity.this, partyDataList, nick);

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
}