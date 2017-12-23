package com.github.angads25.daterangepickerdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.angads25.daterangepicker.dialog.DateRangePickerDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DateRangePickerDialog(this).show()
    }
}
