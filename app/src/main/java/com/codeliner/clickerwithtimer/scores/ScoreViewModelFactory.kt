package com.codeliner.clickerwithtimer.scores

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ScoreViewModelFactory(private val application: Application, private val resultScore: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(application, resultScore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}