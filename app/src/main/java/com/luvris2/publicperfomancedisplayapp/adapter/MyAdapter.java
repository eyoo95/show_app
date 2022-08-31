package com.luvris2.publicperfomancedisplayapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.luvris2.publicperfomancedisplayapp.fragment.HomePagerFirst;
import com.luvris2.publicperfomancedisplayapp.fragment.HomePagerFourth;
import com.luvris2.publicperfomancedisplayapp.fragment.HomePagerThird;
import com.luvris2.publicperfomancedisplayapp.fragment.HomePagerSecond;

public class MyAdapter extends FragmentStateAdapter {

    public int mCount;

    public MyAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if(index==0) return new HomePagerFirst();
        else if(index==1) return new HomePagerSecond();
        else if(index==2) return new HomePagerThird();
        else return new HomePagerFourth();

    }

    @Override
    public int getItemCount() {
        return 2000;
    }

    public int getRealPosition(int position) { return position % mCount; }

}