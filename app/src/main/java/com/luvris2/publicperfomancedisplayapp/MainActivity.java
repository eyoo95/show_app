package com.luvris2.publicperfomancedisplayapp;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

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

    // GPS 관련 변수
    LocationManager locationManager;
    LocationListener locationListener;
    double gpsX, gpsY;

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

        // todo : 이후 코드 작성
        // 현재 위치 정보 확인 및 저장
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                gpsX = location.getLatitude();
                gpsY = location.getLongitude();
                Bundle gpsLatLng = new Bundle();
                gpsLatLng.putDouble("gpsLat", gpsX);
                gpsLatLng.putDouble("gpsLng", gpsY);
                getSupportFragmentManager().setFragmentResult("requestKey", gpsLatLng);
                Log.i("MyTest", "onLocationChanged " + gpsX + ", " + gpsY);
                locationManager.removeUpdates(locationListener);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }
        };

        // GPS 사용 권한 확인 및 요청
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) == false ) {
                showGPSDisabledAlertToUser();
            }
            else if (locationManager.getAllProviders().contains("network")) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, locationListener);
                //locationManager.removeUpdates(locationListener);
            }
        } else {
            // You can directly ask for the permission.
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION },
                    100);
        }
    }

    // GPS 기능 설정 대화 상자 출력
    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS가 비활성화되어 있습니다. 기능을 활성화하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("GPS 설정 페이지로 이동하기",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("취소",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    // GPS 사용자 기기의 현재 위치 정보 확인
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, locationListener);
            //removeUpdates(locationListener);
        }
        else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) == false ) {
            showGPSDisabledAlertToUser();
        }
        else {
            ActivityCompat.requestPermissions(MainActivity.this,
                new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, 100);
        }
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

}