package com.codeliner.clickerwithtimer.scores

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codeliner.clickerwithtimer.databinding.ItemScoreBinding
import com.codeliner.clickerwithtimer.domains.scores.Score

class ScoreAdapter: ListAdapter<Score, ScoreAdapter.ViewHolder>(ScoreDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (itemCount > 0) {
            holder.bind(item)
        } else {

        }
    }

    class ViewHolder private constructor(val binding: ItemScoreBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Score) {
            binding.score = item
            binding.executePendingBindings()
        }
        
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemScoreBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
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