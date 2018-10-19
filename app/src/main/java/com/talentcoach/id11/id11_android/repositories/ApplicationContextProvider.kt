package com.talentcoach.id11.id11_android.repositories

import android.app.Application
import android.content.Context

class ApplicationContextProvider : Application(){
    /**
     * Keeps a reference of the application context
     */
    private var sContext: Context? = null

    override fun onCreate() {
        super.onCreate()

        sContext = applicationContext

    }

    /**
     * Returns the application context
     *
     * @return application context
     */
    fun getContext(): Context? {
        return sContext
    }
}

