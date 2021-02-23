package com.codeliner.clickerwithtimer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codeliner.clickerwithtimer.R
import com.codeliner.clickerwithtimer.domains.scores.Score

class ScoreAdapter: ListAdapter<Score, ScoreAdapter.ViewHolder>(ScoreDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ScoreAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        var score: TextView = itemView.findViewById(R.id.itemScore_scoreValue)
        var created: TextView = itemView.findViewById(R.id.itemScore_created)
        
        fun bind(item: Score) {
            val res = itemView.context.resources
            score.text = item.score.toString()
            created.text = item.created.toString()
        }
        
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_score, parent, false)
                return ViewHolder(view)
            }
        }
    }
}

class ScoreDiffCallback: DiffUtil.ItemCallback<Score>() {
    override fun areItemsTheSame(oldItem: Score, newItem: Score): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Score, newItem: Score): Boolean {
        return oldItem == newItem
    }
}