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

    public BannerViewPager(@NonNull Context context) {
        super(context);
    }

    public BannerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBannerAdapter(BannerAdapter adapter, BannerIndicator bannerIndicator) {
        setAdapter(adapter);
        this.bannerAdapter = adapter;
        this.bannerIndicator = bannerIndicator;
        bannerIndicator.setupWithViewPager(this, adapter.getRealCount());
    }

    public void setBannerSlider(@NonNull BannerSlider bannerSlider) {
        addOnPageChangeListener(bannerSlider);
        setPageTransformer(false, bannerSlider);
        bannerSlider.setUpPager(this);
    }

    public void refresh() {
        bannerIndicator.removeAllViews();
        bannerIndicator.setupWithViewPager(this, bannerAdapter.getRealCount());
    }

}
