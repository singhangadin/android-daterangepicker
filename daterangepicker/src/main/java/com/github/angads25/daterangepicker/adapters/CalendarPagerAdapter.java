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

package com.github.angads25.daterangepicker.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.angads25.daterangepicker.R;
import com.github.angads25.daterangepicker.interfaces.DateEventListener;
import com.github.angads25.daterangepicker.interfaces.OnItemClickListener;
import com.github.angads25.daterangepicker.model.CalendarGridItem;
import com.github.angads25.daterangepicker.model.Date;
import com.github.angads25.daterangepicker.utils.Constants;
import com.github.angads25.daterangepicker.utils.RecyclerViewTouchHandler;
import com.github.angads25.daterangepicker.utils.Utility;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * <p>
 * Created by Angad Singh on 21/12/17.
 * </p>
 */

public class CalendarPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;

    private ArrayList<Date> dates;
    private Pair<Date, Date> datePair;

    private int startOfYear;
    private DateEventListener dateEventListener;

    public CalendarPagerAdapter(Context context, ArrayList<Date> dates) {
        this.context = context;
        this.dates = dates;
        inflater = LayoutInflater.from(context);
        startOfYear = context.getResources().getIntArray(R.array.years)[0];
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayoutCompat) object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        int year = Utility.getYearFromPosition(startOfYear, position);
        int month = Utility.getMonthFromPosition(position);

        String monthString = context.getResources().getStringArray(R.array.months)[month];
        return monthString + " " + year;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @NonNull
    @Override
    public View instantiateItem(@NonNull ViewGroup container, final int pagePosition) {
        final int year = Utility.getYearFromPosition(startOfYear, pagePosition);
        final int month = Utility.getMonthFromPosition(pagePosition);
        View itemView = inflater.inflate(R.layout.item_calendar, container, false);
        RecyclerView recyclerView = itemView.findViewById(R.id.grid_calender);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 7));

        final ArrayList<CalendarGridItem> gridItems = new ArrayList<>();
        int[] days = context.getResources().getIntArray(R.array.days_month);
        String[] days_week = context.getResources().getStringArray(R.array.days_week);

        Calendar firstDay = Calendar.getInstance();
        firstDay.set(Calendar.DAY_OF_MONTH, 1);
        firstDay.set(Calendar.MONTH, month);
        firstDay.set(Calendar.YEAR, year);

        int padding = firstDay.get(Calendar.DAY_OF_WEEK);

        for (String aDays_week : days_week) {
            CalendarGridItem gridItem = new CalendarGridItem();
            gridItem.setLabel(aDays_week);
            gridItem.setSelectable(false);
            gridItem.setItemType(Constants.ITEM_TYPE_WEEK);
            gridItems.add(gridItem);
        }

        for(int i = 0; i < padding - 1; i++) {
            CalendarGridItem gridItem = new CalendarGridItem();
            gridItem.setLabel("");
            gridItem.setSelectable(false);
            gridItem.setItemType(Constants.ITEM_TYPE_WEEK);
            gridItems.add(gridItem);
        }

        Calendar lastDay = Calendar.getInstance();
        lastDay.set(Calendar.DAY_OF_MONTH, 1);
        lastDay.set(Calendar.MONTH, month);
        lastDay.set(Calendar.YEAR, year);
        lastDay.add(Calendar.MONTH, 1);
        lastDay.add(Calendar.DAY_OF_MONTH, -1);

        for (int i = 0; i < lastDay.get(Calendar.DAY_OF_MONTH); i++) {
            CalendarGridItem gridItem = new CalendarGridItem();
            gridItem.setSelectable(true);
            gridItem.setLabel(String.valueOf(days[i]));
            gridItem.setItemType(Constants.ITEM_TYPE_DAY);
            gridItems.add(gridItem);
        }
        final CalendarGridAdapter gridAdapter = new CalendarGridAdapter(context, gridItems);
        recyclerView.setAdapter(gridAdapter);
        container.addView(itemView);
        RecyclerViewTouchHandler handler = new RecyclerViewTouchHandler(context, recyclerView);
        handler.setOnClickListener(new OnItemClickListener() {
            @Override
            public void onClick(RecyclerView recyclerView, View v, int position) {
                CalendarGridItem gridItem = gridItems.get(position);
                if(gridItem.isSelectable()) {
                    Date date = new Date();
                    date.setDay(Integer.parseInt(gridItem.getLabel()));
                    date.setMonth(month);
                    date.setYear(year);
                    gridItem.setSelected(!gridItem.isSelected());
                    gridAdapter.notifyItemChanged(position);

                    if(gridItem.isSelected()) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(gridItem.getLabel()));
                        cal.set(Calendar.MONTH, month);
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);

                        date.setUtc(cal.getTimeInMillis());
                        if (dateEventListener != null) {
                            dateEventListener.onDateChanged(date, 0);
                        }
                    }
                }
            }
        });
        return itemView;
    }

    public void setDatePair(Pair<Date, Date> datePair) {
        this.datePair = datePair;
    }

    public void setDateEventListener(DateEventListener dateEventListener) {
        this.dateEventListener = dateEventListener;
    }
}
