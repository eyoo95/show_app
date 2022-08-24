package com.luvris2.publicperfomancedisplayapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.luvris2.publicperfomancedisplayapp.R;


public class HomePagersecond extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.home_pager_second, container, false);

        return rootView;
    }
}