package com.example.sudo.zadatakpt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sudo.zadatakpt.adapter.ScreenSlidePagerAdapter;
import com.example.sudo.zadatakpt.helpers.AppConstants;
import com.example.sudo.zadatakpt.helpers.DBHelper;
import com.example.sudo.zadatakpt.models.News;

import java.util.List;


public class Read extends AppCompatActivity {

    private Integer itemPosition;

    //https://developer.android.com/training/animation/screen-slide.html
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;
    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private ScreenSlidePagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        DBHelper dbHelper = DBHelper.getInstance(this);
        Intent intent = getIntent();
        Long firstNewsId = intent.getLongExtra(AppConstants.NEWS_ID, 0);
        News firstNews = null;
        final List<News> newsList = dbHelper.getAllNews();
        for (int i = 0; i < newsList.size(); ++i) {
            if(newsList.get(i).getId().equals(firstNewsId)) {
             itemPosition = i;
             firstNews = newsList.get(i);
            }
        }
        if(firstNews != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(firstNews.getTitle());

            // Instantiate a ViewPager and a PagerAdapter.
            mPager = (ViewPager) findViewById(R.id.pager);
            mPagerAdapter = new ScreenSlidePagerAdapter(newsList, getSupportFragmentManager());
            mPager.setAdapter(mPagerAdapter);
            mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    Read.this.getSupportActionBar().setTitle(newsList.get(position).getTitle());
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPager.setCurrentItem(itemPosition);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
