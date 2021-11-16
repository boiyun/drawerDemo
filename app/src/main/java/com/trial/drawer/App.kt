package com.trial.drawer

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

/**
 * <pre>
 *     @author : Trial
 *     @time   : 2021/11/15
 *     @desc   :
 *     @version: 1.0
 * </pre>
 */
class App : Application() , ViewModelStoreOwner {
    private lateinit var mAppViewModelStore: ViewModelStore
    override fun onCreate() {
        super.onCreate()
        mAppViewModelStore = ViewModelStore()
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }
}