package com.codeliner.clickerwithtimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.codeliner.clickerwithtimer.databinding.ActivityMainBinding

class MainActivity :
        AppCompatActivity(),
        DrawerLayout.DrawerListener {

    val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    lateinit var binding: ActivityMainBinding

    private lateinit var drawerLayout: DrawerLayout
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel

        initDrawerLayout()
        initObservers()
    }

    private fun initDrawerLayout() {
        drawerLayout = binding.drawerLayout

        navController = this.findNavController(R.id.navHostFragment)
        drawerLayoutEnableOnlyStartDestination()
        NavigationUI.setupWithNavController(binding.navDrawerView, navController!!)

        drawerLayout.addDrawerListener(this)
    }

    private fun initObservers() {
        // note. for exit app when clicked back key in title fragment
        viewModel.isExit.observe(this, Observer { isExit ->
            if (isExit) {
                if (viewModel.drawerIsOpened.value == true) {
                    drawerLayout.close()
                } else {
                    viewModel.exitComplete()
                    finish()
                }
            }
        })
    }

    private fun drawerLayoutEnableOnlyStartDestination() {
        navController?.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == controller.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController!!, drawerLayout)
    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

    override fun onDrawerOpened(drawerView: View) {
        viewModel.onDrawerOpened()
    }

    override fun onDrawerClosed(drawerView: View) {
        viewModel.onDrawerClosed()
    }

    override fun onDrawerStateChanged(newState: Int) {}
}