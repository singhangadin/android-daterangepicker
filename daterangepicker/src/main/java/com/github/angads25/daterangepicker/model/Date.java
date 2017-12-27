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

package com.github.angads25.daterangepicker.model;

import android.content.Context;

import com.github.angads25.daterangepicker.R;

import java.util.Calendar;

/**
 * <p>
 * Created by Angad Singh on 23/12/17.
 * </p>
 */

public class Date {
    private int day;
    private int month;
    private int year;

    private long utc;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getUtc() {
        return utc;
    }

    public void setUtc(long utc) {
        this.utc = utc;
    }

    public String getFormattedDate(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(utc);
        String day = context.getResources().getStringArray(R.array.days)[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        String month = context.getResources().getStringArray(R.array.months)[getMonth()];
        return day + ", " + getDay() + " " + month + " " + getYear();
    }
}
