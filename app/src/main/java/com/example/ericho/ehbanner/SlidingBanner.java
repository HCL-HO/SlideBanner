package com.example.ericho.ehbanner;


import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;

import java.util.List;

public class SlidingBanner implements ViewPager.OnPageChangeListener, ViewPager.PageTransformer {

    private ViewPager viewPager;
    private int currentPage;
    private List<View> pageList;

    private Handler handler;
    private float RATIO_SCALE = 0.2f;

    private void loopBanner() {
        viewPager.setCurrentItem(currentPage % pageList.size());
        handler.sendEmptyMessageDelayed(0, 3000);
    }

    public SlidingBanner(ViewPager viewPager, List<View> pageList) {
        this.viewPager = viewPager;
        this.pageList = pageList;
        initBanner();
    }


    private void initBanner() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                currentPage++;
                loopBanner();
            }
        };
        loopBanner();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.i("", "onPageScrolled: " + position);
        Log.i("", "Offset: " + positionOffset);

        View view = pageList.get(position);


        float scale = 1 - (positionOffset * RATIO_SCALE);

        // Just a shortcut to findViewById(R.id.image).setScale(scale);
        scaleY(view, scale);

        if (position + 1 < viewPager.getAdapter().getCount()) {
            view = pageList.get(position + 1);
            scale = positionOffset * RATIO_SCALE + (1 - RATIO_SCALE);
            scaleY(view, scale);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void transformPage(View page, float position) {
        if (position >= -0.1 && position <= 0.1) {
            View view = pageList.get(viewPager.getCurrentItem());
            view.setScaleY(1);
            if (viewPager.getCurrentItem() > 0) {
                view = pageList.get(viewPager.getCurrentItem() - 1);
                scaleY(view, 1 - RATIO_SCALE);
            }

            if (viewPager.getCurrentItem() + 1 < viewPager.getAdapter().getCount()) {
                view = pageList.get(viewPager.getCurrentItem() + 1);
                scaleY(view, 1 - RATIO_SCALE);
            }
        }
    }

    private void scaleY(View view, float scale) {
        view.setScaleY(scale);
    }


    public static ViewPager setUpPager(Activity context, ViewPager bannerViewPager) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int padding = (int) Math.round(displayMetrics.widthPixels * 0.1);
        bannerViewPager.setOffscreenPageLimit(3);
        bannerViewPager.setClipToPadding(false);
        bannerViewPager.setPaddingRelative(padding, 0, padding, 0);
        bannerViewPager.setPageMargin(DisplayUtil.getPxByDp(context, DisplayUtil.getDpFromRes(context, R.dimen.page_padding)));
        return bannerViewPager;
    }
}
