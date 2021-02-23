package com.codeliner.clickerwithtimer.utils

import android.content.Context
import com.codeliner.clickerwithtimer.R

fun getGreetingFormatted(context: Context, score: Int): String {
    return context.applicationContext.getString(when {
        score in 0..19 -> R.string.greeting_bad
        score in 20..39 -> R.string.greeting_normal
        score in 40..59 -> R.string.greeting_good
        score in 60..79 -> R.string.greeting_great
        score > 80 -> R.string.greeting_perfect
        else -> R.string.greeting_else
    })
}