package com.trial.drawer.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.trial.drawer.util.smoothnav.NavHostFragment


/**
 * <pre>
 *     @author : Trial
 *     @time   : 2021/11/15
 *     @desc   :
 *     @version: 1.0
 * </pre>
 */
abstract class BaseFragment<B : ViewDataBinding>(@LayoutRes contentLayoutId: Int = 0) :
        Fragment(contentLayoutId) {

    lateinit var binding: B

    protected abstract fun initView()
    protected abstract fun initData()

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        binding = DataBindingUtil.bind(view)!!

        try {
            initView()
            initData()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    protected fun nav(): NavController? {
        return NavHostFragment.findNavController(this)
    }
}