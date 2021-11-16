package com.trial.drawer.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * <pre>
 *     @author : Trial
 *     @time   : 2021/11/16
 *     @desc   :
 *     @version: 1.0
 * </pre>
 */
abstract class BaseNavFragment<B : ViewDataBinding>(@LayoutRes contentLayoutId: Int = 0) :
        Fragment(contentLayoutId) {
    lateinit var binding: B
    var contentView: View? = null

    override fun onCreateView(
            inflater: LayoutInflater ,
            container: ViewGroup? ,
            savedInstanceState: Bundle?
    ): View? {
        contentView = contentView ?: super.onCreateView(inflater , container , savedInstanceState)
        return contentView
    }

    protected abstract fun initView()
    protected abstract fun initData()

    override fun onResume() {
        try {
            initView()
            initData()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onResume()
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        binding = DataBindingUtil.bind(view)!!
    }
}