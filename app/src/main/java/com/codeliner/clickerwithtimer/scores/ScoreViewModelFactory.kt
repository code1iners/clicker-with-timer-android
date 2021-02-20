package com.codeliner.clickerwithtimer.scores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ScoreViewModelFactory(private val resultScore: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(resultScore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}