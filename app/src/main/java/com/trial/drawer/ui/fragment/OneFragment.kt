package com.trial.drawer.ui.fragment

import com.trial.drawer.R
import com.trial.drawer.base.BaseFragment
import com.trial.drawer.databinding.FragmentOneBinding


class OneFragment : BaseFragment<FragmentOneBinding>(R.layout.fragment_one) {
    override fun initView() {
        binding.textGo.setOnClickListener {
            nav()?.navigate(R.id.action_oneFragment_to_fourFragment)
        }
    }

    override fun initData() {
    }


}