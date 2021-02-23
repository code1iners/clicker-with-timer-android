package com.codeliner.clickerwithtimer.records

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.codeliner.clickerwithtimer.domains.scores.Score
import com.codeliner.clickerwithtimer.domains.scores.ScoreDatabaseDao
import kotlinx.coroutines.*

class RecordViewModel(
        app: Application,
        private val dataSourceDao: ScoreDatabaseDao
): AndroidViewModel(app) {
    // note. coroutines
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _scoreList = dataSourceDao.getAllScores()
    val scoreList: LiveData<List<Score>> get() = _scoreList

    fun onClear() {
        uiScope.launch {
            clear()
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            dataSourceDao.clear()
        }
    }
}