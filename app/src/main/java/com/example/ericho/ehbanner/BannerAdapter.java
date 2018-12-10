package com.example.ericho.ehbanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class BannerAdapter extends PagerAdapter implements SlidingBanner.BannerAdapterInteractor {

    private boolean isLooping;
    private BannerAdapterEvent bannerAdapterEvent;
    private float scaleY;

    private int fakeCount = 1000000;

    public interface BannerAdapterEvent {
        void onItemClicked(int position);

        View getView(Context context, int count);

        int getRealCount();
    }

    public BannerAdapter(BannerAdapterEvent bannerAdapterEvent, boolean loop) {
        this.bannerAdapterEvent = bannerAdapterEvent;
        this.isLooping = loop;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        if (isLooping) {
            return instantiateLoopingItem(container, position);
        } else {
            return instantiateNormalItem(container, position);
        }
    }

    private Object instantiateNormalItem(ViewGroup container, int position) {
        View view = bannerAdapterEvent.getView(container.getContext(), getRealPosition(position));
        view = setupView(view, position);
        container.addView(view);
        return view;
    }

    private int getRealPosition(int position) {
        return position % bannerAdapterEvent.getRealCount();
    }


    private Object instantiateLoopingItem(ViewGroup container, int position) {
        View view = bannerAdapterEvent.getView(container.getContext(), getRealPosition(position));
        view = setupView(view, position);
        container.addView(view);
        return view;
    }

    private View setupView(View view, final int position) {
        view.setScaleY(1 - scaleY);
        view.setTag(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bannerAdapterEvent.onItemClicked(getRealPosition(position));
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        return fakeCount;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public void setScaleY(float y) {
        this.scaleY = y;
    }

    @Override
    public int getFakeCount() {
        return fakeCount;
    }
}
