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

package com.github.angads25.daterangepicker.utils;

/**
 * <p>
 * Created by Angad Singh on 23/12/17.
 * </p>
 */

public class Utility {

    // Assuming dates range from 1900-2100
    public static int getYearFromPosition(int startOfYear, int position) {
        return startOfYear + ((int) Math.floor(position / 12));
    }

    public static int getMonthFromPosition(int position) {
        return position % 12;
    }

    // Date 24/12/2017, startOfYear = 2018, month = Dec, year = 2017; offset = -1
    public static int getPositionFromYearMonth(int startOfYear, int month, int year) {
        if(startOfYear > year) {
            return 0;
        } else {
            return ((year - startOfYear) * 12) + (month);
        }
    }
}
