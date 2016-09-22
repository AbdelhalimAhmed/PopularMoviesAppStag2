package com.example.abdelhalim.popularmoviesapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by abdelhalim on 01/09/16.
 */
public class TabsAdapter extends FragmentPagerAdapter {
    Fragment[] fragments;
    public TabsAdapter(FragmentManager fm, Fragment [] fragments){
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments[i];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
