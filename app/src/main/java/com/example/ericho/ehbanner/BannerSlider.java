package com.example.ericho.ehbanner;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class BannerSlider implements ViewPager.OnPageChangeListener, ViewPager.PageTransformer {

    private boolean auto;
    private ViewPager bannerViewPager;
    private int currentPage;

    private Handler handler;
    private float RATIO_SCALE = 0.2f;
    private long speed = 2000;

    private void loopBanner() {
        bannerViewPager.setCurrentItem(currentPage, true);
        handler.sendEmptyMessageDelayed(0, speed);
    }


    public BannerSlider(long speed, float sideRatio, boolean auto) {
//        this.bannerViewPager = viewPager;
        this.speed = speed;
        this.RATIO_SCALE = sideRatio;
        this.auto = auto;
    }

    public BannerSlider(long speed, boolean auto) {
        this(speed, 0.2f, auto);
    }

    public BannerSlider(ViewPager viewPager, boolean auto) {
        this(5000, auto);
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
            View view = bannerViewPager.findViewWithTag(position);
            float scale = 1 - (positionOffset * RATIO_SCALE);
            scaleY(view, scale);

            if (position + 1 < bannerViewPager.getAdapter().getCount()) {
                view = bannerViewPager.findViewWithTag(position + 1);
                scale = positionOffset * RATIO_SCALE + (1 - RATIO_SCALE);
                scaleY(view, scale);
            }

            if (position > 0) {
                view = bannerViewPager.findViewWithTag(position - 1);
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


    public void setUpPager(BannerViewPager bannerViewPager) {
        this.bannerViewPager = bannerViewPager;
        if (!(bannerViewPager.getAdapter() instanceof BannerAdapter)) {
            throw new IllegalArgumentException("ViewPager must setup with Banner adapter");
        }
        BannerAdapter adapter = (BannerAdapter) bannerViewPager.getAdapter();
        adapter.setScaleY(RATIO_SCALE);
        currentPage = adapter.getFakeCount() / 2;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) bannerViewPager.getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int padding = (int) Math.round(displayMetrics.widthPixels * 0.1);
        bannerViewPager.setOffscreenPageLimit(3);
        bannerViewPager.setClipToPadding(false);
        bannerViewPager.setPaddingRelative(padding, 0, padding, 0);
        bannerViewPager.setPageMargin(DisplayUtil.getPxByDp(bannerViewPager.getContext(), DisplayUtil.getDpFromRes(bannerViewPager.getContext(), R.dimen.frame_padding)));
        initBanner();
    }

}
