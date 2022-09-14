package com.luvris2.publicperfomancedisplayapp.fragment;

import android.content.Context;
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
import com.luvris2.publicperfomancedisplayapp.ui.MainActivity;


public class HomePagerSecond extends Fragment {

    ImageView imgEventimg;
    FrameLayout Frame2;
    MainActivity mainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    // 메인 액티비티에서 내려온다.
    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.home_pager_second, container, false);

        imgEventimg = rootView.findViewById(R.id.imgEventimg);
        Frame2 = rootView.findViewById(R.id.Frame2);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.banner2);
        imgEventimg.setImageBitmap(bitmap);



        Frame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        return rootView;
    }



}