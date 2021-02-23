package com.codeliner.clickerwithtimer.records

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codeliner.clickerwithtimer.domains.scores.ScoreDatabaseDao

class RecordViewModelFactory(
        private val application: Application,
        private val dataSourceDao: ScoreDatabaseDao
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecordViewModel::class.java)) {
            return RecordViewModel(
                    application,
                    dataSourceDao
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}