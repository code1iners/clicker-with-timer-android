package com.codeliner.clickerwithtimer.scores

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codeliner.clickerwithtimer.domains.scores.ScoreDatabase
import com.codeliner.clickerwithtimer.domains.scores.ScoreDatabaseDao

class ScoreViewModelFactory(
        private val application: Application,
        private val resultScore: Int,
        private val dataSourceDao: ScoreDatabaseDao
        ): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(
                    application,
                    resultScore,
                    dataSourceDao
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}