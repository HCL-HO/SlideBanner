package com.example.ericho.ehbanner;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ViewPager bannerViewPager = findViewById(R.id.home_banner);
        BannerAdapter bannerPagerAdapter = new BannerAdapter(getViews(), new BannerAdapter.BannerAdapterEvent() {
            @Override
            public void onItemClicked(int position) {

            }
        });
        SlidingBanner slidingBanner = new SlidingBanner(bannerViewPager, bannerPagerAdapter);
        bannerViewPager.setAdapter(bannerPagerAdapter);
        bannerViewPager = SlidingBanner.setUpPager(this, bannerViewPager);
        bannerViewPager.addOnPageChangeListener(slidingBanner);
        bannerViewPager.setPageTransformer(false, slidingBanner);
        BannerIndicator indicator = findViewById(R.id.home_banner_indicator);
        indicator.setupWithViewPager(bannerViewPager);
    }

    private List<View> getViews() {
        List<View> views = new ArrayList<>();
        addItem(R.color.colorAccent, views);
        addItem(R.color.colorPrimary, views);
        addItem(R.color.colorPrimaryDark, views);
        addItem(R.color.white, views);
        return views;
    }

    private void addItem(int colorAccent, List<View> views) {
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();
        params.height = ViewPager.LayoutParams.MATCH_PARENT;
        params.width = ViewPager.LayoutParams.MATCH_PARENT;
        View v = new View(this);
        v.setLayoutParams(params);
        v.setBackgroundColor(getResources().getColor(colorAccent));
        views.add(v);
    }
}
