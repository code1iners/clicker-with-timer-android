package com.codeliner.clickerwithtimer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codeliner.clickerwithtimer.R
import com.codeliner.clickerwithtimer.domains.scores.Score

class ScoreAdapter: RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    var data = listOf<Score>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreAdapter.ScoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_score, parent, false)
        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreAdapter.ScoreViewHolder, position: Int) {
        val item = data[position]

        holder.score?.text = item.score.toString()
        holder.created?.text = item.created.toString()
    }

    override fun getItemCount(): Int = data.size

    inner class ScoreViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var score: TextView? = null
        var created: TextView? = null

        init {
            score = view.findViewById(R.id.itemScore_scoreValue)
            created = view.findViewById(R.id.itemScore_created)
        }
    }
}