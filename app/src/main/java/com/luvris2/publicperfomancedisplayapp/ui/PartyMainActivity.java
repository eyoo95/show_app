package com.luvris2.publicperfomancedisplayapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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
    TextView editSearch;
    ImageView imgSearch;
    ImageView imgCreate;
    Spinner spinner;

    // 검색타입 + 키워드 조합으로 검색
    String[] searchTypeList = {"제목", "공연", "제목+공연"};

    List<PartyRoom> partyRoomList = new ArrayList<>();
    PartyRoomAdapter partyRoomAdapter;

    int limit = 25;
    int offset = 0;
    String keyword = "";
    String searchType = "all";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_main);

        getPartyRoom(keyword, searchType);

        editSearch = findViewById(R.id.editSearch);
        imgSearch = findViewById(R.id.imgSearch);
        imgCreate = findViewById(R.id.imgCreate);
        spinner = findViewById(R.id.spinner);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PartyMainActivity.this));

        // 스피너 설정
        ArrayAdapter<String> searchArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item, searchTypeList);
        searchArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 스피너 화면 설정
        spinner.setAdapter(searchArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSearchType(i, searchTypeList);

                getPartyRoom(keyword, searchType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = editSearch.getText().toString().trim();

                getPartyRoom(keyword, searchType);
            }
        });

        imgCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PartyMainActivity.this, PartyCreateActivity.class);
                startActivity(intent);

                finish();

            }
        });
    }

    void selectedSearchType(int i, @NonNull String[] searchTypeList) {
        switch (searchTypeList[i]) {
            case "제목":
                searchType = "title";
                break;
            case "공연":
                searchType = "prfnm";
                break;
            case "제목+공연":
                searchType = "all";
                break;
        }
    }

    private void getPartyRoom(String keyword, String searchType) {

        Retrofit retrofit = NetworkClient.getRetrofitClient(PartyMainActivity.this);

        PartyApi api = retrofit.create(PartyApi.class);

        Call<PartyRoomRes> call = api.getPartyRoom(limit, offset, keyword, searchType);

        call.enqueue(new Callback<PartyRoomRes>() {
            @Override
            public void onResponse(Call<PartyRoomRes> call, Response<PartyRoomRes> response) {


                if (response.isSuccessful()) {
                    PartyRoomRes partyRoomRes = response.body();
                    partyRoomList.clear();
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