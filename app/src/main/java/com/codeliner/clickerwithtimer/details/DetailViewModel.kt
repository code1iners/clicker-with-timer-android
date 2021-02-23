package com.codeliner.clickerwithtimer.details

import android.app.Application
import androidx.lifecycle.*
import com.codeliner.clickerwithtimer.domains.scores.Score
import com.codeliner.clickerwithtimer.domains.scores.ScoreDatabaseDao
import com.codeliner.clickerwithtimer.utils.getGreetingFormatted
import kotlinx.coroutines.*
import timber.log.Timber

class DetailViewModel(
        app: Application,
        private val dataSourceDao: ScoreDatabaseDao,
        private val scoreId: Long
): AndroidViewModel(app) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _score = MutableLiveData<Score?>()
    val score: LiveData<Score?> get() = _score

    val greeting = Transformations.map(score) {
        it?.let {
            getGreetingFormatted(app, it.score)
        }
    }

    init {
        initScore()
    }

    private fun initScore() {
        uiScope.launch {
            _score.value = getScoreFromDatabase()
        }
    }

    private suspend fun getScoreFromDatabase(): Score {
        return withContext(Dispatchers.IO) {
            dataSourceDao.getScoreById(scoreId)
        }
    }

    fun onDelete() {
        uiScope.launch {
            delete()
            _score.value = null
        }
    }
    
    private suspend fun delete() {
        withContext(Dispatchers.IO) {
            score.value?.let { score ->
                dataSourceDao.deleteById(score.id)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}