package com.example.ericho.ehbanner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class BannerIndicator extends RadioGroup {

    public BannerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void addButtons(int id) {
        RadioButton addBtn = getNewButton(id);
        addView(addBtn);
    }

    private RadioButton getNewButton(int id) {
        RadioButton radioButton = new RadioButton(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMarginStart(DisplayUtil.getPxByDp(getContext(), 5));
        radioButton.setLayoutParams(layoutParams);
        radioButton.setButtonDrawable(R.drawable.banner_indicator);
        radioButton.setId(id);
        return radioButton;
    }

    public void selectButton(int id) {
        clearCheck();
        check(id);
    }

    private void refreshAfterSelect(int id, int previous) {
        try {
            getChildAt(previous).requestLayout();
            getChildAt(id).requestLayout();
        } catch (Exception e) {
            Log.d("ericho", id + " " + previous);
        }
    }

    public void setupWithViewPager(ViewPager bannerViewPager, final int realItemCount) {
        removeAllViews();
        for (int i = 0; i < realItemCount; i++) {
            addButtons(i);
        }
        bannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int previous = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int realPosition = position % realItemCount;
                selectButton(realPosition);
                refreshAfterSelect(realPosition, previous);
                previous = realPosition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        selectButton(0);
    }

}
