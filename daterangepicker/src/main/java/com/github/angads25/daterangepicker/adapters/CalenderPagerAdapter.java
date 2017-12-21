package com.github.angads25.daterangepicker.adapters;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * <p>
 * Created by Angad Singh on 21/12/17.
 * </p>
 */

public class CalenderPagerAdapter extends PagerAdapter {

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }
}
