package com.luvris2.publicperfomancedisplayapp.ui;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.api.GoogleMapApi;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.config.Config;
import com.luvris2.publicperfomancedisplayapp.fragment.CommunityFragment;
import com.luvris2.publicperfomancedisplayapp.fragment.HomeFragment;
import com.luvris2.publicperfomancedisplayapp.fragment.MapFragment;
import com.luvris2.publicperfomancedisplayapp.fragment.MyPageFragment;
import com.luvris2.publicperfomancedisplayapp.model.GoogleMapPlace;

import java.io.IOException;

import retrofit2.Call;
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

    // GPS 관련 변수
    LocationManager locationManager;
    LocationListener locationListener;
    Location location;
    double gpsX, gpsY;
    Object mySidoLocation;

    // 프로그레스 다이얼로그
    ProgressDialog progressDialog;

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

        Log.i("MyTestMainActivity", "getLocation");
        getLocation();
        // todo : onLocationChanged

        // GPS 사용 권한 확인 및 요청
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    100);
        }
    }

    // GPS 내 위치 정보 받아오는 메소드
    public LatLng getLocation() {
        Log.i("MyTestMainActivity", "In getLocation Method");
        try {
            Log.i("MyTestMainActivity", "In getLocation Method Start Try");
            // 로케이션 매니저 위치 참조
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // GPS, NETWORK 활성화 여부 확인
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Log.i("MyTestMainActivity", "In getLocation Method complete Enabled Checking");
            // 현재 위치 정보 확인 및 저장
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    // 내 위치 경도, 위도 저장
                    gpsX = location.getLatitude();
                    gpsY = location.getLongitude();
                    locationManager.removeUpdates(locationListener);
                }
                @Override
                public void onProviderEnabled(@NonNull String provider) { }
                @Override
                public void onProviderDisabled(@NonNull String provider) {
                    showGPSDisabledAlertToUser();
                }
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) { }
            };

            // GPS, Network 이용하여 내 위치 정보 확인
            if (!isGPSEnabled && !isNetworkEnabled) {
                // 모두 비활성화의 경우
                Toast.makeText(getApplicationContext(), "GPS 기능이 비활성화 되어있습니다.", Toast.LENGTH_LONG).show();
            } else {
                if (isNetworkEnabled) {
                    Log.i("MyTestMainActivity", "In getLocation Method NetworkProvider");
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);
                } else {
                    if (location == null) {
                        Log.i("MyTestMainActivity", "In getLocation Method GpsProvider");
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
                    }}
            }
        } catch (Exception e) { e.printStackTrace();}
        return new LatLng(gpsX, gpsY);
    }

    // Google Maps API를 통한 내 현재 위치의 지역 정보 추출
    public String getMySidoLocation(LatLng location) {
        showProgressBar("내 지역 정보를 확인중입니다. 잠시만 기다려주세요.");

        Retrofit retrofit = NetworkClient.getRetrofitGoogleMaps(MainActivity.this);
        GoogleMapApi api = retrofit.create(GoogleMapApi.class);

        Call<GoogleMapPlace> call = api.getMyLocation(
                location.latitude+","+location.longitude, "ko", Config.GOOGLE_MAPS_API_KEY);

        // Retrofit 값을 바로 저장하기 위한 동기 처리
        new Thread(() -> {
            try {
                mySidoLocation = call.execute().body().getPlus_code();
                Log.i("MyTest Location Geocode", "" + mySidoLocation );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // API 응답에 따른 약간의 대기 시간 설정
        try {
            Thread.sleep(1000);
            dismissProgressBar();
        } catch (InterruptedException e) {
            e.printStackTrace();
            dismissProgressBar();
        }

        // 지역 확인 후 지역 코드 변환
        if (mySidoLocation.toString().contains("서울특별시")) {
            return "11";
        } else if (mySidoLocation.toString().contains("부산광역시")) {
            return "26";
        } else if (mySidoLocation.toString().contains("대구광역시")) {
            return "27";
        } else if (mySidoLocation.toString().contains("인천광역시")) {
            return "28";
        } else if (mySidoLocation.toString().contains("광주광역시")) {
            return "29";
        } else if (mySidoLocation.toString().contains("대전광역시")) {
            return "30";
        } else if (mySidoLocation.toString().contains("울산광역시")) {
            return "31";
        } else if (mySidoLocation.toString().contains("세종특별자치시")) {
            return "36";
        } else if (mySidoLocation.toString().contains("경기도")) {
            return "41";
        } else if (mySidoLocation.toString().contains("강원도")) {
            return "42";
        } else if (mySidoLocation.toString().contains("충청북도")) {
            return "43";
        } else if (mySidoLocation.toString().contains("충청남도")) {
            return "44";
        } else if (mySidoLocation.toString().contains("전라북도")) {
            return "45";
        } else if (mySidoLocation.toString().contains("전라남도")) {
            return "46";
        } else if (mySidoLocation.toString().contains("경상북도")) {
            return "47";
        } else if (mySidoLocation.toString().contains("경상남도")) {
            return "48";
        } else if (mySidoLocation.toString().contains("제주특별자치도")) {
            return "50";
        }
        return "error";
    }

    // GPS 기능 설정 대화 상자 출력
    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS 기능이 비활성화되어 있습니다.\n기능을 활성화하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("GPS 설정 페이지로 이동하기",
                        (dialog, id) -> {
                            Intent callGPSSettingIntent = new Intent(
                                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(callGPSSettingIntent);
                        });
        alertDialogBuilder.setNegativeButton("취소",
                (dialog, id) -> dialog.cancel());
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    // GPS 사용자 기기의 현재 위치 정보 확인 및 권한 설정
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        }
        else if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
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

    // 위치 정보 수신 대기를 위한 프로그레스 다이얼로그
    public void showProgressBar(String message) {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("정보 수신 중...");
        progressDialog.setMessage(message);
        progressDialog.show();
    }
    public void dismissProgressBar() {
        progressDialog.dismiss();
    }

}