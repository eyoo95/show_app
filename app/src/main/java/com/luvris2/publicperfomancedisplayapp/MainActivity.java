package com.luvris2.publicperfomancedisplayapp;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.api.UserApi;
import com.luvris2.publicperfomancedisplayapp.config.Config;
import com.luvris2.publicperfomancedisplayapp.fragment.CommunityFragment;
import com.luvris2.publicperfomancedisplayapp.fragment.HomeFragment;
import com.luvris2.publicperfomancedisplayapp.fragment.MapFragment;
import com.luvris2.publicperfomancedisplayapp.fragment.MyPageFragment;
import com.luvris2.publicperfomancedisplayapp.model.User;
import com.luvris2.publicperfomancedisplayapp.model.UserRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    // 탭 메뉴
    BottomNavigationView bottomNavigationView;

    // 프래그먼트
    Fragment homeFragment;
    Fragment mapFragment;
    Fragment communityFragment;
    Fragment myPageFragment;

    private String myEmail;
    private String myNickname;
    private int myAge;
    private int myGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 억세스 토큰 확인
        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken","");

        if (accessToken.isEmpty()){
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);

            finish();
            return;
        }

        loadUserInfo();

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("email", myEmail);
        editor.apply();

        editor.putString("nickname", myNickname);
        editor.apply();

        editor.putInt("age",myAge);
        editor.apply();

        editor.putInt("gender",myGender);
        editor.apply();


        // 탭 메뉴 객체 생성
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // 프래그먼트 객체 생성
        homeFragment = new HomeFragment();
        mapFragment = new MapFragment();
        communityFragment = new CommunityFragment();
        myPageFragment = new MyPageFragment();

        // 탭 메뉴 클릭시 이벤트
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                // 메뉴 선택시 이동 할 프래그먼트의 이름을 저장 할 변수
                Fragment fragment = null;

                if (itemId == R.id.menuHome) {
                    // 메뉴 선택시 지정된 프래그먼트로 이동
                    fragment = homeFragment;
                    // 프래그먼트의 액션바 타이틀 설정
                    getSupportActionBar().setTitle("홈");
                    getSupportActionBar().show();

                    // 메뉴 선택시 아이콘 이미지 변경
                    item.setIcon(R.drawable.tap_menu_icon_home_fill);
                    // 선택되지 않은 메뉴의 아이콘 이미지 변경
                    bottomNavigationView.getMenu().findItem(R.id.menuMap).setIcon(R.drawable.tap_menu_icon_map);
                    bottomNavigationView.getMenu().findItem(R.id.menuCommunity).setIcon(R.drawable.tap_menu_icon_community);
                    bottomNavigationView.getMenu().findItem(R.id.menuMyPage).setIcon(R.drawable.tap_menu_icon_my_page);
                }
                else if (item.getItemId() == R.id.menuMap) {
                    fragment = mapFragment;
                    getSupportActionBar().setTitle("내 주변 공연/전시 찾기");
                    getSupportActionBar().show();
                    item.setIcon(R.drawable.tap_menu_icon_map_fill);
                    bottomNavigationView.getMenu().findItem(R.id.menuHome).setIcon(R.drawable.tap_menu_icon_home);
                    bottomNavigationView.getMenu().findItem(R.id.menuCommunity).setIcon(R.drawable.tap_menu_icon_community);
                    bottomNavigationView.getMenu().findItem(R.id.menuMyPage).setIcon(R.drawable.tap_menu_icon_my_page);
                }
                else if (item.getItemId() == R.id.menuCommunity) {
                    fragment = communityFragment;
                    getSupportActionBar().setTitle("커뮤니티");
                    getSupportActionBar().show();
                    item.setIcon(R.drawable.tap_menu_icon_map_fill);
                    item.setIcon(R.drawable.tap_menu_icon_community_fill);
                    bottomNavigationView.getMenu().findItem(R.id.menuHome).setIcon(R.drawable.tap_menu_icon_home);
                    bottomNavigationView.getMenu().findItem(R.id.menuMap).setIcon(R.drawable.tap_menu_icon_map);
                    bottomNavigationView.getMenu().findItem(R.id.menuMyPage).setIcon(R.drawable.tap_menu_icon_my_page);
                }
                else if (item.getItemId() == R.id.menuMyPage) {
                    fragment = myPageFragment;
                    getSupportActionBar().setTitle("내 정보");
                    getSupportActionBar().show();
                    item.setIcon(R.drawable.tap_menu_icon_my_page_fill);
                    bottomNavigationView.getMenu().findItem(R.id.menuHome).setIcon(R.drawable.tap_menu_icon_home);
                    bottomNavigationView.getMenu().findItem(R.id.menuMap).setIcon(R.drawable.tap_menu_icon_map);
                    bottomNavigationView.getMenu().findItem(R.id.menuCommunity).setIcon(R.drawable.tap_menu_icon_community);
                }
                return loadFragment(fragment);
            }
        });




        // todo : 이후 코드 작성

    }

    // 프래그먼트 이동 메소드
    private boolean loadFragment(Fragment fragment) {
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    // 회원정보 불러오는 기능
    private void loadUserInfo() {

        Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
        UserApi api = retrofit.create(UserApi.class);

        SharedPreferences sp = MainActivity.this.getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
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

                } else {
                    Toast.makeText(MainActivity.this, "에러 발생 : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override // 실패했을 때
            public void onFailure(Call<UserRes> call, Throwable t) {
                // 네트워크 자체 문제로 실패!
            }
        });
    }
}