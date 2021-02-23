package com.codeliner.clickerwithtimer.scores

import android.app.Application
import androidx.lifecycle.*
import com.codeliner.clickerwithtimer.R
import com.codeliner.clickerwithtimer.domains.scores.Score
import com.codeliner.clickerwithtimer.domains.scores.ScoreDatabaseDao
import com.codeliner.clickerwithtimer.utils.getGreetingFormatted
import kotlinx.coroutines.*

class ScoreViewModel(
        app: Application,
        resultScore: Int,
        private val dataSourceDao: ScoreDatabaseDao
): AndroidViewModel(app) {

    // note. coroutines
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean> get() = _eventPlayAgain

    init {
        _score.value = resultScore
        onScoreSave(Score(score=resultScore))
    }

    fun playAgain() {
        _eventPlayAgain.value = true
    }

    fun playAgainComplete() {
        _eventPlayAgain.value = false
    }

    val greeting = Transformations.map(score) {
        getGreetingFormatted(app, it)
    }

    private fun onScoreSave(score: Score) {
        uiScope.launch {
            insert(score)
        }
    }

    private suspend fun insert(newScore: Score) {
        withContext(Dispatchers.IO) {
            dataSourceDao.insert(newScore)
        }
    }
}