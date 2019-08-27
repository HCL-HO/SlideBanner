package com.example.ericho.ehbanner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericho.ehbanner.BannerAdapter;

public class BannerAdapter extends PagerAdapter implements BannerAdapterInteractor {

    private BannerAdapter.BannerAdapterEvent bannerAdapterEvent;
    private float scaleY;

    private int fakeCount = 1000000;

    public interface BannerAdapterEvent {
        void onItemClicked(int position);

        View getView(Context context, int count);

        int getRealCount();
    }

    public BannerAdapter(BannerAdapter.BannerAdapterEvent bannerAdapterEvent) {
        this.bannerAdapterEvent = bannerAdapterEvent;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        return instantiateLoopingItem(container, position);
    }

//    private Object instantiateNormalItem(ViewGroup container, int position) {
//        View view = bannerAdapterEvent.getView(container.getContext(), getRealPosition(position));
//        view = setupView(view, position);
//        container.addView(view);
//        return view;
//    }

    private int getRealPosition(int position) {
        return position % bannerAdapterEvent.getRealCount();
    }


    private Object instantiateLoopingItem(ViewGroup container, int position) {
        int realPosition = getRealPosition(position);
        View view = bannerAdapterEvent.getView(container.getContext(), realPosition);
        view = setupView(view, position);
        if (view.getParent() == null) {
//            Log.d("ME/BANNER", "ADD " + realPosition);
            container.addView(view);
        }
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
