package io.bitsound.contentprovidersample

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.support.v4.content.ContextCompat

class App : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var sContext: Context? = null

        fun context(): Context = sContext!!
        fun string(resid: Int): String = sContext!!.getString(resid)
        fun color(resid: Int): Int = ContextCompat.getColor(sContext!!, resid)
    }

    override fun onCreate() {
        super.onCreate()
        sContext = this.applicationContext
    }
}
