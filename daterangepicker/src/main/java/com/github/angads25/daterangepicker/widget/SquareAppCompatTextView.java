package com.github.angads25.daterangepicker.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * <p>
 * Created by Angad Singh on 23/12/17.
 * </p>
 */

public class SquareAppCompatTextView extends AppCompatTextView {

    public SquareAppCompatTextView(Context context) {
        super(context);
    }

    public SquareAppCompatTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareAppCompatTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
