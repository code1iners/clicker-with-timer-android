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
import com.codeliner.clickerwithtimer.databinding.PartialNavHeaderBinding
import com.codeliner.clickerwithtimer.domains.scores.ScoreDatabase
import com.codeliner.clickerwithtimer.titles.TitleFragmentDirections
import com.google.android.material.navigation.NavigationView
import timber.log.Timber

class MainActivity :
        AppCompatActivity()
        , DrawerLayout.DrawerListener
        , NavigationView.OnNavigationItemSelectedListener
{

    lateinit var binding: ActivityMainBinding
    lateinit var drawerHeaderBinding: PartialNavHeaderBinding

    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var drawerLayout: DrawerLayout
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initViewModel()
        initDrawerHeader()
        initDrawerLayout()
        initObservers()
    }

    private fun initViewModel() {
        // note. prepare app & dataSourceDao for create view model factory
        val app = requireNotNull(this).application
        val dataSourceDao = ScoreDatabase.getInstance(app).scoreDatabaseDao
        // note. assignment view model with factory
        viewModelFactory = MainViewModelFactory(app, dataSourceDao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        // note. apply into binding
        binding.viewModel = viewModel
    }

    private fun initDrawerHeader() {
        drawerHeaderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.partial_nav_header, binding.navDrawerView, false)
        binding.navDrawerView.addHeaderView(drawerHeaderBinding.root)
    }

    private fun initDrawerLayout() {
        // note. Assignment drawer layout
        drawerLayout = binding.drawerLayout
        drawerLayout.addDrawerListener(this)

        // note. Combine drawer layout with nav controller
        navController = this.findNavController(R.id.navHostFragment)
        navController?.let { nc ->
            NavigationUI.setupActionBarWithNavController(this, nc, drawerLayout)
            drawerLayoutEnableOnlyStartDestination(nc)
            NavigationUI.setupWithNavController(binding.navDrawerView, nc)
        }
        // note. Adding navigation item click listener
        binding.navDrawerView.setNavigationItemSelectedListener(this)
    }

    private fun drawerLayoutEnableOnlyStartDestination(nc: NavController) {
        // note. Drawer layout only enable start destination
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

        viewModel.scoreLatest.observe(this, Observer {
            it?.let { score ->
                drawerHeaderBinding.score = score
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