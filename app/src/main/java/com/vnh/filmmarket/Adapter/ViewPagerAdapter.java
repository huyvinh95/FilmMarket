package com.vnh.filmmarket.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.vnh.filmmarket.Fragment.FragmentDiscover;
import com.vnh.filmmarket.Fragment.FragmentMovies;
import com.vnh.filmmarket.Fragment.FragmentTvShow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUYVINH on 06-Feb-17.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> listViewPager = new ArrayList<>();
    List<String> title = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        listViewPager.add(new FragmentDiscover());
        listViewPager.add(new FragmentMovies());
        listViewPager.add(new FragmentTvShow());

        title.add("Discover");
        title.add("Movies");
        title.add("TV Show");
    }

    @Override
    public Fragment getItem(int position) {
        return listViewPager.get(position);
    }

    @Override
    public int getCount() {
        return listViewPager.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}
