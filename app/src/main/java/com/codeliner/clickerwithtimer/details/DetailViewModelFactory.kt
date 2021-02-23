package com.codeliner.clickerwithtimer.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codeliner.clickerwithtimer.domains.scores.ScoreDatabaseDao

class DetailViewModelFactory(
        private val application: Application,
        private val dataSourceDao: ScoreDatabaseDao,
        private val scoreId: Long
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(
                    application,
                    dataSourceDao,
                    scoreId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}