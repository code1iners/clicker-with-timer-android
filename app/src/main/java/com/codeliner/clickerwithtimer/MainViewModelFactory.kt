package com.codeliner.clickerwithtimer

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codeliner.clickerwithtimer.domains.scores.ScoreDatabaseDao

class MainViewModelFactory(
    private val app: Application,
    private val dataSourceDao: ScoreDatabaseDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                    app,
                    dataSourceDao
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}