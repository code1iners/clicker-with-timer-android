package com.codeliner.clickerwithtimer.scores

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(resultScore: Int): ViewModel() {

    private val _resultScore = MutableLiveData<Int>()
    val resultScore: LiveData<Int> get() = _resultScore

    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean> get() = _eventPlayAgain

    init {
        _resultScore.value = resultScore
    }

    fun playAgain() {
        _eventPlayAgain.value = true
    }

    fun playAgainComplete() {
        _eventPlayAgain.value = false
    }
}