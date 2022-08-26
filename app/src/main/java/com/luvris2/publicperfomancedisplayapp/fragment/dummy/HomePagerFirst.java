package com.luvris2.publicperfomancedisplayapp.fragment.dummy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.luvris2.publicperfomancedisplayapp.R;


public class HomePagerFirst extends Fragment {

    ImageView imgEventImg;
    TextView txtEventTitle;
    TextView txtEventDate;
    TextView txtEventPlace;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.home_pager_first, container, false);

        imgEventImg = rootView.findViewById(R.id.imgEventimg1);
        txtEventTitle = rootView.findViewById(R.id.txtEventTitle1);
        txtEventDate = rootView.findViewById(R.id.txtEventDate1);
        txtEventPlace = rootView.findViewById(R.id.txtEventPlace1);


        return rootView;
    }
}