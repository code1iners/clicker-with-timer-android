package com.codeliner.clickerwithtimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.codeliner.clickerwithtimer.databinding.ActivityMainBinding
import com.codeliner.clickerwithtimer.titles.TitleFragmentDirections
import com.google.android.material.navigation.NavigationView
import timber.log.Timber

class MainActivity :
        AppCompatActivity()
        , DrawerLayout.DrawerListener
        , NavigationView.OnNavigationItemSelectedListener
{

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

        binding.navDrawerView.setNavigationItemSelectedListener(this)
    }

    private fun initDrawerLayout() {
        drawerLayout = binding.drawerLayout
        drawerLayout.addDrawerListener(this)

        navController = this.findNavController(R.id.navHostFragment)
        navController?.let { nc ->
            NavigationUI.setupActionBarWithNavController(this, nc, drawerLayout)
            drawerLayoutEnableOnlyStartDestination(nc)
            NavigationUI.setupWithNavController(binding.navDrawerView, nc)
        }
    }

    private fun drawerLayoutEnableOnlyStartDestination(nc: NavController) {
        nc.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == controller.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return try {
            NavigationUI.navigateUp(navController!!, drawerLayout)
        } catch (e: Exception) {
            false
        }
    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
    override fun onDrawerOpened(drawerView: View) { viewModel.onDrawerOpened() }
    override fun onDrawerClosed(drawerView: View) { viewModel.onDrawerClosed() }
    override fun onDrawerStateChanged(newState: Int) {}

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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_records -> {
                navController?.navigate(TitleFragmentDirections.actionTitleFragmentToRecordFragment())
            }
        }
        return true
    }
}