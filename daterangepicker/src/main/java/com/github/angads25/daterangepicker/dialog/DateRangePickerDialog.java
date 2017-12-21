package com.github.angads25.daterangepicker.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;

import com.github.angads25.daterangepicker.R;

/**
 * <p>
 * Created by Angad Singh on 20/12/17.
 * </p>
 */

public class DateRangePickerDialog extends AppCompatDialog {
    private Context context;

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
        setContentView(R.layout.dialog_date_range_picker);
    }
}
