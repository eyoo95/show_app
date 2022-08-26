package com.luvris2.publicperfomancedisplayapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.ui.eventInfoActivity;


public class HomePagerFirst extends Fragment {

    View frame;
    ImageView imgEventImg;
    TextView txtEventTitle;
    TextView txtEventDate;
    TextView txtEventPlace;
    private String prfName;
    private String prfPlace;
    private String currentTime;
    private String currentTime2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.home_pager_first, container, false);

        frame = rootView.findViewById(R.id.frame1);
        imgEventImg = rootView.findViewById(R.id.imgEventimg1);
        txtEventTitle = rootView.findViewById(R.id.txtEventTitle1);
        txtEventDate = rootView.findViewById(R.id.txtEventDate1);
        txtEventPlace = rootView.findViewById(R.id.txtEventPlace1);

        getParentFragmentManager().setFragmentResultListener("apiResult", getActivity(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                String prfName = bundle.getString("prfName");
                String prfPlace = bundle.getString("prfPlace");
                String currentTime = bundle.getString("currentTime");
                String currentTime2 = bundle.getString("currentTime");
                // Do something with the result...

                Log.i("Fragment DATA Result", prfName +" / "+ prfPlace +" / "+ currentTime +" / "+ currentTime2 , null);
            }
        });

        txtEventTitle.setText(prfName);
        txtEventDate.setText(currentTime +" ~ "+ currentTime2);
        txtEventPlace.setText(prfPlace);

        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), eventInfoActivity.class);
                startActivity(intent);
            }
        });


        return rootView;
    }
}