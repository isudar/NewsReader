package com.example.sudo.zadatakpt.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.sudo.zadatakpt.fragments.ScreenSliderFragment;
import com.example.sudo.zadatakpt.models.News;

import java.util.List;

/**
 * Created by Ivan on 22.03.2018..
 */

public class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

    private List<News> newsList;

    public ScreenSlidePagerAdapter(List<News> newsList, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.newsList = newsList;
    }

    @Override
    public Fragment getItem(int position) {
         return ScreenSliderFragment.newInstance(newsList.get(position));
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return newsList.get(position).getTitle();
    }
}
