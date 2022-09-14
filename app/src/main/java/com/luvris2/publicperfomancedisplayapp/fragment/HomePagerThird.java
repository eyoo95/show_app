package com.luvris2.publicperfomancedisplayapp.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.luvris2.publicperfomancedisplayapp.R;


public class HomePagerThird extends Fragment {

    ImageView imgEventimg;
    FrameLayout Frame3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.home_pager_third, container, false);

        imgEventimg = rootView.findViewById(R.id.imgEventimg);
        Frame3 = rootView.findViewById(R.id.Frame3);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.banner3);
        imgEventimg.setImageBitmap(bitmap);

        Frame3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rootView;
    }
}