/*
 * Copyright (C) 2018 Angad Singh
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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.github.angads25.daterangepicker.interfaces.OnItemClickListener;
import com.github.angads25.daterangepicker.interfaces.OnItemLongClickListener;

/**
 * <p>
 * Created by Angad Singh on 13/7/17.
 * </p>
 */

public class RecyclerViewTouchHandler implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;

    private RecyclerView mRecyclerView;

    private OnItemClickListener clickListener;
    private OnItemLongClickListener mOnlongClickListener;

    public RecyclerViewTouchHandler(Context context, RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        mRecyclerView.addOnItemTouchListener(this);

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mOnlongClickListener != null) {
                    mOnlongClickListener.onLongClick(mRecyclerView, child, mRecyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(rv, child, rv.getChildAdapterPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    public void setOnClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setOnlongClickListener(OnItemLongClickListener onlongClickListener) {
        this.mOnlongClickListener = onlongClickListener;
    }
}

