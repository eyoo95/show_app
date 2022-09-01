package com.luvris2.publicperfomancedisplayapp.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.luvris2.publicperfomancedisplayapp.R;

public class DialogFragment extends Fragment {

    DatePicker datePicker;
    EditText editText;

    private int year;
    private int month;
    private int day;

    Spinner Tspinner;
    Spinner Rspinner;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        Tspinner = (Spinner) rootView.findViewById(R.id.spinnerType);
        datePicker = rootView.findViewById(R.id.datePicker);
        Rspinner = (Spinner) rootView.findViewById(R.id.spinnerRegion);
        editText = rootView.findViewById(R.id.editPerformanceKeyword);

        initTypeSpinnerFooter();

        //날짜 정보 가져오기
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = year + "/" + monthOfYear + "/" + dayOfMonth;
                //todo: 선택한 날짜 데이터 담아서 HomeFragment로
            }
        });
        
        // 라디오버튼이든 스피너로 지역 정보 가져오기
        initRegionSpinnerFooter();

        // 검색어 가져오기
        String keyword = editText.getText().toString().trim();

        return rootView;
    }
    private void initTypeSpinnerFooter() {
        String[] items = new String[]{
                "전체", "연극", "뮤지컬", "무용", "클래식", "오페라", "국악", "복합"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Tspinner.setAdapter(adapter);

        Tspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

    }

    private void initRegionSpinnerFooter() {
        String[] items = new String[]{
                "전체", "서울", "부산", "대구", "인천", "광주", "대전", "울산",  "세종", "경기", "강원", "충북", "충남"
, "전북", "전남", "경북", "경남", "제주"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Rspinner.setAdapter(adapter);

        Rspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

    }

}