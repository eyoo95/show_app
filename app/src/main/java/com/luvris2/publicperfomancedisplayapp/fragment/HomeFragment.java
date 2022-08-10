package com.luvris2.publicperfomancedisplayapp.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luvris2.publicperfomancedisplayapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        imgSearch = rootView.findViewById(R.id.imgSearch);
        cardView1 = rootView.findViewById(R.id.cardView1);
        cardView2 = rootView.findViewById(R.id.cardView2);
        txtPlace = rootView.findViewById(R.id.txtPlace);
        txtType = rootView.findViewById(R.id.txtType);
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

                // 다이얼로그 제목 설정
//                dialogSearch.setMessage("연극");
//                dialogSearch.setTitle("유형선택");

                // 레이아웃 xml 뷰와 연결 설정
                ViewGroup itemView = (ViewGroup) inflater.inflate(R.layout.dialog_search, container, false);
                dialogSearch.setView(itemView);

                // 확인을 누르면 실행 될 코드 작성
                dialogSearch.setPositiveButton("검색", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 확인시 실행 코드 작성
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