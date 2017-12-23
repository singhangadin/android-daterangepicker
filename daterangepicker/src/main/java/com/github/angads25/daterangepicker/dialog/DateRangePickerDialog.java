/*
 * Copyright (C) 2017 Angad Singh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.angads25.daterangepicker.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.Window;

import com.github.angads25.daterangepicker.R;
import com.github.angads25.daterangepicker.adapters.CalendarPagerAdapter;
import com.github.angads25.daterangepicker.model.Date;
import com.github.angads25.daterangepicker.utils.Utility;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * <p>
 * Created by Angad Singh on 20/12/17.
 * </p>
 */

public class DateRangePickerDialog extends AppCompatDialog implements
        ViewPager.OnPageChangeListener,
        View.OnClickListener {
    private Context context;

    private CalendarPagerAdapter calendarAdapter;

    private AppCompatTextView tvYearMonth;

    private ViewPager viewPager;

    private AppCompatImageButton actionBack;
    private AppCompatImageButton actionNext;

    private AppCompatButton actionOk;
    private AppCompatButton actionCancel;

    public DateRangePickerDialog(Context context) {
        super(context);
        this.context = context;
    }

    public DateRangePickerDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    protected DateRangePickerDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_date_range_picker);

        tvYearMonth = findViewById(R.id.tv_month_year);
        actionBack = findViewById(R.id.action_back);
        actionNext = findViewById(R.id.action_next);

        viewPager = findViewById(R.id.calendar_pager);
        ArrayList<Date> dates = new ArrayList<>();
        for (int i = 0; i < 2412; i++) {
            Date date = new Date();
            date.setMonth(Utility.getMonthFromPosition(i));
            date.setYear(Utility.getYearFromPosition(i));
            dates.add(date);
        }
        calendarAdapter = new CalendarPagerAdapter(context, dates);
        viewPager.setAdapter(calendarAdapter);
        viewPager.addOnPageChangeListener(this);

        Calendar cal = Calendar.getInstance();
        int offset = Utility.getPositionFromYearMonth(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
        viewPager.setCurrentItem(offset);

        actionBack.setOnClickListener(this);
        actionNext.setOnClickListener(this);
        findViewById(R.id.action_ok).setOnClickListener(this);
        findViewById(R.id.action_cancel).setOnClickListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        if(position == 0) {
            actionBack.setEnabled(false);
        } else {
            actionBack.setEnabled(true);
        }
        if(position == calendarAdapter.getCount() - 1) {
            actionNext.setEnabled(false);
        } else {
            actionNext.setEnabled(true);
        }
        tvYearMonth.setText(calendarAdapter.getPageTitle(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.action_back) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
        } else if(id == R.id.action_next) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
        } else if(id == R.id.action_cancel) {
            cancel();
        } else if(id == R.id.action_ok) {
            dismiss();
        }
    }
}
