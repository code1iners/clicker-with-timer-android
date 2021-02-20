package com.codeliner.clickerwithtimer.clicks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChangeViewModel: ViewModel() {

    private val _number = MutableLiveData<Int>()
    val number: LiveData<Int> get() = _number

    init {
        Log.i("ChangeViewModel", "number: ${number.value}")
        _number.value = 0
    }

    fun increase() {
        _number.value = _number.value?.plus(1)
    }

    fun decrease() {
        _number.value = number.value?.minus(1)
    }
}