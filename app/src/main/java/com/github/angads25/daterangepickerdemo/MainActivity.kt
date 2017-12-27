package com.github.angads25.daterangepickerdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.view.View
import android.view.View.OnClickListener
import com.github.angads25.daterangepicker.dialog.DateRangePickerDialog

class MainActivity : AppCompatActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<AppCompatButton>(R.id.btn_show_dialog)
        btn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_show_dialog ->  {
                DateRangePickerDialog(this).show()
            }
        }
    }
}
