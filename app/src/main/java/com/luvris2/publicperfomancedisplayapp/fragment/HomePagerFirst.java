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
import com.luvris2.publicperfomancedisplayapp.ui.PartyActivity;
import com.luvris2.publicperfomancedisplayapp.ui.PartyMainActivity;

public class HomePagerFirst extends Fragment {

    ImageView imgEventimg;
    FrameLayout Frame1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.home_pager_first, container, false);

        imgEventimg = rootView.findViewById(R.id.imgEventimg);
        Frame1 = rootView.findViewById(R.id.Frame1);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.banner1);
        imgEventimg.setImageBitmap(bitmap);

        Frame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PartyMainActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}