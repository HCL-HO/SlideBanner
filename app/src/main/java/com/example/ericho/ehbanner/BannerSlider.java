package com.example.ericho.ehbanner;


import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.ericho.ehbanner.R;
import com.example.ericho.ehbanner.DisplayUtil;
import com.example.ericho.ehbanner.BannerSlider;

public class BannerSlider implements ViewPager.OnPageChangeListener, ViewPager.PageTransformer {

    private boolean auto;
    private ViewPager viewPager;
    private int currentPage;

    private Handler handler;
    private float RATIO_SCALE = 0.2f;
    private long speed = 2000;

    private void loopBanner() {
        viewPager.setCurrentItem(currentPage, true);
        handler.sendEmptyMessageDelayed(0, speed);
    }


    public BannerSlider(ViewPager viewPager, BannerAdapterInteractor adapter, long speed, float sideRatio, boolean auto) {
        this.viewPager = viewPager;
        this.speed = speed;
        this.RATIO_SCALE = sideRatio;
        this.auto = auto;
        adapter.setScaleY(sideRatio);
        currentPage = adapter.getFakeCount() / 2;
        initBanner();
    }

    public BannerSlider(ViewPager viewPager, BannerAdapterInteractor adapter, long speed, boolean auto) {
        this(viewPager, adapter, speed, 0.2f, auto);
    }

    public BannerSlider(ViewPager viewPager, BannerAdapterInteractor adapter, boolean auto) {
        this(viewPager, adapter, 5000, auto);
    }


    private void initBanner() {
        if (auto) {
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    currentPage++;
                    loopBanner();
                }
            };
            loopBanner();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        currentPage = position;

        try {
            View view = viewPager.findViewWithTag(position);
            float scale = 1 - (positionOffset * RATIO_SCALE);
            scaleY(view, scale);

            if (position + 1 < viewPager.getAdapter().getCount()) {
                view = viewPager.findViewWithTag(position + 1);
                scale = positionOffset * RATIO_SCALE + (1 - RATIO_SCALE);
                scaleY(view, scale);
            }

            if (position > 0) {
                view = viewPager.findViewWithTag(position - 1);
                scale = positionOffset * RATIO_SCALE + (1 - RATIO_SCALE);
                scaleY(view, scale);
            }
        } catch (NullPointerException e) {

        }
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void transformPage(View page, float position) {
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
        bannerViewPager.setPageMargin(DisplayUtil.getPxByDp(context, DisplayUtil.getDpFromRes(context, R.dimen.frame_padding)));
        return bannerViewPager;
    }

}
