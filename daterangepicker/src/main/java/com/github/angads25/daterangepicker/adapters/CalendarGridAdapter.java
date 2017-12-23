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
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.angads25.daterangepicker.R;
import com.github.angads25.daterangepicker.model.CalendarGridItem;
import com.github.angads25.daterangepicker.utils.Constants;
import com.github.angads25.daterangepicker.widget.SquareAppCompatTextView;

import java.util.ArrayList;

/**
 * <p>
 * Created by Angad Singh on 23/12/17.
 * </p>
 */

public class CalendarGridAdapter extends RecyclerView.Adapter<CalendarGridAdapter.CalendarViewHolder> {
    private Context context;
    private ArrayList<CalendarGridItem> gridItems;

    public CalendarGridAdapter(Context context, ArrayList<CalendarGridItem> gridItems) {
        this.context = context;
        this.gridItems = gridItems;
    }

    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item_calendar, parent, false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CalendarViewHolder holder, int position) {
        CalendarGridItem gridItem = gridItems.get(position);
        if(gridItem.getItemType() == Constants.ITEM_TYPE_WEEK) {
            holder.label.setTextColor(Color.parseColor("#A09FA0"));
        } else if(gridItem.getItemType() == Constants.ITEM_TYPE_DAY) {
            holder.label.setTextColor(Color.parseColor("#444444"));
        }
        holder.label.setText(gridItem.getLabel());
    }

    @Override
    public int getItemCount() {
        return gridItems.size();
    }

    class CalendarViewHolder extends RecyclerView.ViewHolder {
        SquareAppCompatTextView label;

        CalendarViewHolder(View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.tv_label);
        }
    }
}
