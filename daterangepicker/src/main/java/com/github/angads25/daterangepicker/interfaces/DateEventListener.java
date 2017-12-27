package com.github.angads25.daterangepicker.interfaces;

import com.github.angads25.daterangepicker.model.Date;

/**
 * <p>
 * Created by Angad Singh on 28/12/17.
 * </p>
 */

public interface DateEventListener {
    void onDateChanged(Date date, int type);
}
