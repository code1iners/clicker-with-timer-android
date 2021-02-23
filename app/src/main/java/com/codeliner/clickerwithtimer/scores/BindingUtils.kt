package com.codeliner.clickerwithtimer.scores

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.codeliner.clickerwithtimer.R
import com.codeliner.clickerwithtimer.domains.scores.Score
import com.codeliner.clickerwithtimer.utils.getGreetingFormatted
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("scoreTestFormatted")
fun TextView.setTestFormatted(item: Score?) {
    item?.let {
        text = this.context.getString(R.string.record_score, item.score)
    }
}

@BindingAdapter("scoreCreatedString")
fun TextView.setScoreCreatedString(item: Score?) {
    item?.let {
        val format = SimpleDateFormat("yyyy/MM/dd")
        val formatted = format.format(Date(item.created))
        text = formatted
    }
}

@BindingAdapter("scoreGreetingString")
fun TextView.setScoreGreeting(item: Score?) {
    item?.let {
        text = getGreetingFormatted(this.context, it.score)
    }
}