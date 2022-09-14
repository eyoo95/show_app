package com.luvris2.publicperfomancedisplayapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.ui.UserEditActivity;


public class HomePagerFourth extends Fragment {

    ImageView imgEventimg;
    FrameLayout Frame4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.home_pager_fourth, container, false);

        imgEventimg = rootView.findViewById(R.id.imgEventimg);
        Frame4 = rootView.findViewById(R.id.Frame4);

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_icon);
//        imgEventimg.setImageBitmap(bitmap);

        Frame4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), UserEditActivity.class);
                startActivity(intent);

            }
        });

        return rootView;
    }
}