package com.example.ericho.ehbanner;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class BannerAdapter extends PagerAdapter implements SlidingBanner.BannerAdapterInteractor {

    private final List<View> viewList;
    private BannerAdapterEvent bannerAdapterEvent;
    private float scaleY;

    public interface BannerAdapterEvent {
        void onItemClicked(int position);
    }

    public BannerAdapter(List<View> views, BannerAdapterEvent bannerAdapterEvent) {
        this.viewList = views;
        this.bannerAdapterEvent = bannerAdapterEvent;
    }

    @Override
    public int getCount() {
        return viewList != null ? viewList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = viewList.get(position);
        view.setScaleY(1 - scaleY);
        container.addView(view);
        viewList.get(position).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bannerAdapterEvent.onItemClicked(position);
            }
        });
        return view;
    }

    @Override
    public List<View> getViewList() {
        return viewList;
    }

    @Override
    public void setScaleY(float y){
        this.scaleY = y;
    }
}
