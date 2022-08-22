package com.luvris2.publicperfomancedisplayapp.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luvris2.publicperfomancedisplayapp.R;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CardView cardView1;
    CardView cardView2;
    TextView txtPlace;
    TextView txtType;
    ImageView img1;
    ImageView img2;
    ImageView imgSearch;
    ViewPager2 viewPager2;
    private ViewGroup rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        imgSearch = rootView.findViewById(R.id.imgHomeSearch);
        cardView1 = rootView.findViewById(R.id.cardView1);
        cardView2 = rootView.findViewById(R.id.cardView2);
        txtPlace = rootView.findViewById(R.id.txtHomeSortRegion);
        txtType = rootView.findViewById(R.id.txtHomeSortType);
        img1 = rootView.findViewById(R.id.img1);
        img2 = rootView.findViewById(R.id.img2);

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo : 다이얼로그를 활용하여 화면에 새로운 레이아웃 띄우기
                // 별점 남기기 모의 테스트
                // dialogAddRating = 확인을 누르면 별점을 남길 수 있도록하는 다이얼로그
                // viewAddRating = 유저에게 보여줄 레이아웃의 뷰 정보 저장한 객체
                // R.layout.dialog_add_reivew = 유저에게 보여줄 레이아웃

                // 다이얼로그 객체 생성
                AlertDialog.Builder dialogSearch = new AlertDialog.Builder(getContext());

                // 다이얼로그 제목 설정 - 안 해도 될 듯?
//                dialogSearch.setMessage("연극");
//                dialogSearch.setTitle("유형선택");

                // 레이아웃 xml 뷰와 연결 설정
                ViewGroup itemView = (ViewGroup) inflater.inflate(R.layout.fragment_dialog, container, false);
                dialogSearch.setView(itemView);

                // 확인을 누르면 실행 될 코드 작성
                dialogSearch.setPositiveButton("검색", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 확인시 실행 코드 작성
                        // todo: 다이얼로그 데이터 받아와서 새로운 액티비티로 검색결과 띄워주기
                    }
                });

                // 취소를 누르면 실행 될 코드 작성
                dialogSearch.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 취소시 실행 코드 작성
                    }
                });

                // 다이얼로그 유저에게 출력
                dialogSearch.show();
            }
        });

        return rootView;
    }

}