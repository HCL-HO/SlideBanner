package com.example.ericho.ehbanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class BannerViewPager extends ViewPager {
    private BannerAdapter bannerAdapter;
    private BannerIndicator bannerIndicator;
    private BannerAdapter.BannerAdapterEvent bannerAdapterEvent;
    private BannerSlider bannerSlider;

    public BannerViewPager(@NonNull Context context) {
        super(context);
    }

    public BannerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBannerSlider(@NonNull BannerSlider bannerSlider) {
        addOnPageChangeListener(bannerSlider);
        setPageTransformer(false, bannerSlider);
        bannerSlider.setUpPager(this);
        this.bannerSlider = bannerSlider;
    }

    public void setBannerComponent(BannerAdapter.BannerAdapterEvent bannerAdapterEvent, BannerIndicator bannerIndicator, int numOfItems) {
        this.bannerAdapter = new BannerAdapter(bannerAdapterEvent, numOfItems);
        setAdapter(bannerAdapter);
        this.bannerAdapterEvent = bannerAdapterEvent;
        this.bannerIndicator = bannerIndicator;
        bannerIndicator.setupWithViewPager(this, numOfItems);
    }

    public void setBannerComponent(BannerAdapter.BannerAdapterEvent bannerAdapterEvent, BannerIndicator bannerIndicator) {
        this.bannerAdapter = new BannerAdapter(bannerAdapterEvent, 1);
        setAdapter(bannerAdapter);
        this.bannerAdapterEvent = bannerAdapterEvent;
        this.bannerIndicator = bannerIndicator;
        bannerIndicator.setupWithViewPager(this, 1);
    }

    public void refresh(int numOfItems) {
        clearOnPageChangeListeners();
        removeAllViews();
        setAdapter(bannerAdapter);
        bannerIndicator.removeAllViews();
        bannerIndicator.setupWithViewPager(this, numOfItems);
        bannerAdapter = new BannerAdapter(bannerAdapterEvent, numOfItems);
        setBannerSlider(bannerSlider);
    }
}
