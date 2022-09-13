package com.luvris2.publicperfomancedisplayapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.adapter.PartyRoomAdapter;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.api.PartyApi;
import com.luvris2.publicperfomancedisplayapp.model.PartyRoom;
import com.luvris2.publicperfomancedisplayapp.model.PartyRoomRes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PartyMainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    List<PartyRoom> partyRoomList = new ArrayList<>();
    PartyRoomAdapter partyRoomAdapter;

    int limit = 25;
    int offset = 0;
    String keyword = "";
    String searchType = "all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PartyMainActivity.this));

        Retrofit retrofit = NetworkClient.getRetrofitClient(PartyMainActivity.this);

        PartyApi api = retrofit.create(PartyApi.class);

        Call<PartyRoomRes> call = api.getPartyRoom(limit, offset, keyword, searchType);

        call.enqueue(new Callback<PartyRoomRes>() {
            @Override
            public void onResponse(Call<PartyRoomRes> call, Response<PartyRoomRes> response) {

                if (response.isSuccessful()) {
                    PartyRoomRes partyRoomRes = response.body();

                    partyRoomList.addAll(partyRoomRes.getResultList());
                    partyRoomAdapter = new PartyRoomAdapter(PartyMainActivity.this, partyRoomList);
                    recyclerView.setAdapter(partyRoomAdapter);
                } else {
                    Toast.makeText(PartyMainActivity.this, "에러발생 : " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PartyRoomRes> call, Throwable t) {

            }
        });
    }
}