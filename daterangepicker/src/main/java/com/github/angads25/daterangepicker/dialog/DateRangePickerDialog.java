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
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;

import com.github.angads25.daterangepicker.R;
import com.github.angads25.daterangepicker.adapters.CalendarPagerAdapter;
import com.github.angads25.daterangepicker.adapters.YearPickerAdapter;
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
    private int startOfYear;

    private Context context;

    private ArrayList<Integer> years;

    private YearPickerAdapter yearAdapter;
    private CalendarPagerAdapter calendarAdapter;

    private LinearLayoutCompat yearPickerLayout;

    private ViewPager viewPager;

    private RecyclerView yearPicker;
    private AppCompatTextView tvYearMonth;
    private AppCompatImageButton actionBack;
    private AppCompatImageButton actionNext;

    private boolean isYearRevealed = false;
    private int pagerPosition;

    private LinearLayoutManager yearLayoutManager;

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

        startOfYear = context.getResources().getIntArray(R.array.years)[0];

        tvYearMonth = findViewById(R.id.tv_month_year);
        actionBack = findViewById(R.id.action_back);
        actionNext = findViewById(R.id.action_next);

        yearPickerLayout = findViewById(R.id.layout_year_picker);

        viewPager = findViewById(R.id.calendar_pager);

        years = new ArrayList<>();
        for(int year: context.getResources().getIntArray(R.array.years)) {
            years.add(year);
        }

        yearPicker = findViewById(R.id.list_year_picker);
        yearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        yearPicker.setLayoutManager(yearLayoutManager);
        yearAdapter = new YearPickerAdapter(context, years);
        yearPicker.setAdapter(yearAdapter);

        final LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(yearPicker);
        yearPicker.setOnFlingListener(snapHelper);

        yearPicker.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper.findSnapView(yearLayoutManager);
                    if(centerView!=null) {
                        int pos = yearLayoutManager.getPosition(centerView);
                        int month = Utility.getMonthFromPosition(pagerPosition);
                        int year = years.get(pos);
                        pagerPosition = Utility.getPositionFromYearMonth(startOfYear, month, year);
                        viewPager.setCurrentItem(pagerPosition, true);
                    }
                }
            }
        });

        ArrayList<Date> dates = new ArrayList<>();
        for (int i = 0; i < (years.size() * 12); i++) {
            Date date = new Date();
            date.setMonth(Utility.getMonthFromPosition(i));
            date.setYear(Utility.getYearFromPosition(startOfYear, i));
            dates.add(date);
        }
        calendarAdapter = new CalendarPagerAdapter(context, dates);
        viewPager.setAdapter(calendarAdapter);
        viewPager.addOnPageChangeListener(this);

        Calendar cal = Calendar.getInstance();
        int offset = Utility.getPositionFromYearMonth(startOfYear, cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
        pagerPosition = offset;
        if(offset == 0) {
            tvYearMonth.setText(calendarAdapter.getPageTitle(0));
        } else {
            viewPager.setCurrentItem(offset);
        }

        actionBack.setOnClickListener(this);
        actionNext.setOnClickListener(this);
        tvYearMonth.setOnClickListener(this);

        findViewById(R.id.action_ok).setOnClickListener(this);
        findViewById(R.id.action_cancel).setOnClickListener(this);
        findViewById(R.id.action_drop_up).setOnClickListener(this);
        findViewById(R.id.action_drop_down).setOnClickListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        pagerPosition = position;
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
        if(isYearRevealed) {
            int offset = years.indexOf(Utility.getYearFromPosition(startOfYear, pagerPosition));
            yearPicker.scrollToPosition(offset);
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
        } else if(id == R.id.tv_month_year) {
            if(isYearRevealed) {
                yearPickerLayout.setVisibility(View.GONE);
                isYearRevealed = false;
            } else {
                yearPickerLayout.setVisibility(View.VISIBLE);
                isYearRevealed = true;
                int offset = years.indexOf(Utility.getYearFromPosition(startOfYear, pagerPosition));
                yearPicker.scrollToPosition(offset);
            }
        } else if(id == R.id.action_cancel) {
            cancel();
        } else if(id == R.id.action_ok) {
            dismiss();
        } else if(id == R.id.action_drop_up) {
            int position = yearLayoutManager.findFirstVisibleItemPosition();
            if(position - 1 >= 0) {
                yearPicker.scrollToPosition(position - 1);
                int month = Utility.getMonthFromPosition(pagerPosition);
                int year = years.get(position - 1);
                pagerPosition = Utility.getPositionFromYearMonth(startOfYear, month, year);
                viewPager.setCurrentItem(pagerPosition, true);
            }
        } else if(id == R.id.action_drop_down) {
            int position = yearLayoutManager.findFirstVisibleItemPosition();
            if(position + 1 < years.size()) {
                yearPicker.scrollToPosition(position + 1);
                int month = Utility.getMonthFromPosition(pagerPosition);
                int year = years.get(position + 1);
                pagerPosition = Utility.getPositionFromYearMonth(startOfYear, month, year);
                viewPager.setCurrentItem(pagerPosition, true);
            }
        }
    }
}
