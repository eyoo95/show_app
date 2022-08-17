package com.luvris2.publicperfomancedisplayapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.luvris2.publicperfomancedisplayapp.fragment.CommunityFragment;
import com.luvris2.publicperfomancedisplayapp.fragment.HomeFragment;
import com.luvris2.publicperfomancedisplayapp.fragment.MapFragment;
import com.luvris2.publicperfomancedisplayapp.fragment.MyPageFragment;

public class MainActivity extends AppCompatActivity {

    // 탭 메뉴
    BottomNavigationView bottomNavigationView;

    // 프래그먼트
    Fragment homeFragment, mapFragment, communityFragment, myPageFragment;

    Context mContext;

    LocationManager locationManager;
    LocationListener locationListener;
    double gpsX;
    double gpsY;
    private String strGpsX;
    private String strGpsY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 탭 메뉴 객체 생성
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // 프래그먼트 객체 생성
        homeFragment = new HomeFragment();
        mapFragment = new MapFragment();
        communityFragment = new CommunityFragment();
        myPageFragment = new MyPageFragment();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // 마지막 위치 받아오기

        // 퍼미션 체크 코드
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location loc_Current = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // 좌표 받아서 변수 지정
        gpsX = loc_Current.getLatitude();
        gpsY = loc_Current.getLongitude();

        Log.i("myLocation create", "위도 : " + gpsX);
        Log.i("myLocation create", "경도 : " + gpsY);

        // 번들로 데이터 이동할 수 있도록 메소드 작성.
        openFragment();

        // 탭 메뉴 클릭시 이벤트
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 메뉴 선택시 이동 할 프래그먼트의 이름을 저장 할 변수
                Fragment fragment = null;

                if (item.getItemId() == R.id.menuHome) {
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
                } return loadFragment(fragment);
            }
        });
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

    private void openFragment(){
        Bundle bundleX = new Bundle();
        Bundle bundleY = new Bundle();

        // 더블로 이동하는 방법을 못 찾아서 번거롭지만 문자열로 바꿨다가 다시 변환하는 방법 사용
        // todo : 직접 옮길 수 있다면 코드 바꾸기
        strGpsX = new Double(gpsX).toString();
        strGpsY = new Double(gpsY).toString();

        bundleX.putString("gpsX", strGpsX);
        bundleY.putString("gpsY", strGpsY);
    }


}