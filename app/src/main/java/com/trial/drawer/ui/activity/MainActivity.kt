package com.trial.drawer.ui.activity

import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.trial.drawer.R
import com.trial.drawer.util.immersive
import com.trial.drawer.base.BaseActivity
import com.trial.drawer.databinding.ActivityMainBinding
import com.trial.drawer.util.smoothnav.FragmentNavigator

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        immersive(binding.toolbar , true)

        val navController = findNavController(R.id.nav)
        binding.toolbar.setupWithNavController(navController , AppBarConfiguration(binding.navView.menu , binding.drawer))

        navController.addOnDestinationChangedListener { _ , destination , _ ->
            binding.toolbar.subtitle =
                    (destination as FragmentNavigator.Destination).className.substringAfterLast('.')
        }

        binding.drawer.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View , slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerStateChanged(newState: Int) {
            }

        })
        binding.navView.setupWithNavController(navController)
    }

    override fun initData() {

    }

    override fun onBackPressed() {
        if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
            binding.drawer.closeDrawers()
        } else super.onBackPressed()
    }
}