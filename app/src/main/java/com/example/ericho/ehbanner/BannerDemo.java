package com.example.ericho.ehbanner;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BannerDemo {

    public static void setup(ViewPager bannerViewPager, BannerIndicator bannerIndicator, Activity context) {
        BannerAdapter bannerPagerAdapter = new BannerAdapter(new BannerAdapter.BannerAdapterEvent() {
            @Override
            public void onItemClicked(int position) {
            }

            @Override
            public View getView(Context context, int count) {
                return getTextView(context, count);
            }

            @Override
            public int getRealCount() {
                return 5;
            }
        }, true);
        SlidingBanner slidingBanner = new SlidingBanner(bannerViewPager, bannerPagerAdapter, 2000, 0.4f);
        bannerViewPager.setAdapter(bannerPagerAdapter);
        bannerViewPager = SlidingBanner.setUpPager(context, bannerViewPager);
        bannerViewPager.addOnPageChangeListener(slidingBanner);
        bannerViewPager.setPageTransformer(false, slidingBanner);
        bannerIndicator.setupWithViewPager(bannerViewPager,5);
    }

    private static List<View> getViews(Context context) {
        List<View> views = new ArrayList<>();
        addItem(R.color.colorAccent, views, context);
        addItem(R.color.colorPrimary, views, context);
        addItem(R.color.colorPrimaryDark, views, context);
        addItem(R.color.white, views, context);
        return views;
    }

    private static void addItem(int colorAccent, List<View> views, Context context) {
        View v = getView(context, colorAccent);
        views.add(v);
    }

    private static View getView(Context context, int colorAccent) {
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();
        params.height = ViewPager.LayoutParams.MATCH_PARENT;
        params.width = ViewPager.LayoutParams.MATCH_PARENT;
        View v = new View(context);
        v.setLayoutParams(params);
        v.setBackgroundColor(context.getResources().getColor(colorAccent));
        return v;
    }


    private static View getTextView(Context context, int count) {
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();
        params.height = ViewPager.LayoutParams.MATCH_PARENT;
        params.width = ViewPager.LayoutParams.MATCH_PARENT;
        RelativeLayout v = new RelativeLayout(context);
        v.setLayoutParams(params);
        v.setBackgroundColor(context.getResources().getColor(R.color.white));
        TextView textView = new TextView(context);
        textView.setText(String.valueOf(count));
        textView.setTextSize(200);
        textView.setGravity(Gravity.CENTER);
        v.addView(textView);
        return v;
    }
}
