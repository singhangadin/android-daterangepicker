package com.github.angads25.daterangepickerdemo

import android.app.Application
import com.squareup.leakcanary.LeakCanary

/**<p>
 * Created by Angad Singh on 28/12/17.
 *</p>
 */

class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}