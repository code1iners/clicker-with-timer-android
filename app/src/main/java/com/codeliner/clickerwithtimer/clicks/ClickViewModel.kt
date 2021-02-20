package com.codeliner.clickerwithtimer.clicks

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class ClickViewModel: ViewModel() {

    companion object {

        private const val COUNTDOWN_TIME = 10000L
        private const val SECOND = 1000L
    }

    private val _valueScore = MutableLiveData<Int>()
    val valueScore: LiveData<Int> get() = _valueScore

    private val _valueRemainingTime = MutableLiveData<Long>()
    val valueRemainingTime: LiveData<Long> get() = _valueRemainingTime

    val valueRemainingTimeString = Transformations.map(valueRemainingTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean> get() = _eventGameFinish

    private lateinit var timer: CountDownTimer

    init {
        _valueScore.value = 0
        startTimer()
    }

    private fun startTimer() {
        timer = object: CountDownTimer(COUNTDOWN_TIME, SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _valueRemainingTime.value = (millisUntilFinished / SECOND)
            }

            override fun onFinish() {
                _eventGameFinish.value = true
            }
        }

        timer.start()
    }

    fun increase() {
        _valueScore.value = _valueScore.value?.plus(1)
    }

    fun onEventGameFinishComplete() {
        _eventGameFinish.value = false
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }
}