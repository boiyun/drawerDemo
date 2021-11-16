package com.trial.drawer.base

import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * <pre>
 *     @author : Trial
 *     @time   : 2021/11/15
 *     @desc   :
 *     @version: 1.0
 * </pre>
 */
abstract class BaseActivity<B : ViewDataBinding>(@LayoutRes contentLayoutId: Int = 0) :
        AppCompatActivity(contentLayoutId) {
    lateinit var binding: B
    lateinit var rootView: View

    override fun setContentView(layoutResId: Int) {
        rootView = layoutInflater.inflate(layoutResId, null)
        setContentView(rootView)
        binding = DataBindingUtil.bind(rootView)!!
        init()
    }

    open fun init() {
        try {
            initView()
            initData()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected abstract fun initView()
    protected abstract fun initData()
}