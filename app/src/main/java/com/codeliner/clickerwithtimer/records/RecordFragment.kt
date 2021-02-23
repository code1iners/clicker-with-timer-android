package com.codeliner.clickerwithtimer.records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codeliner.clickerwithtimer.adapters.ScoreAdapter
import com.codeliner.clickerwithtimer.databinding.FragmentRecordBinding
import com.codeliner.clickerwithtimer.domains.scores.ScoreDatabase
import timber.log.Timber

class RecordFragment: Fragment() {

    private lateinit var binding: FragmentRecordBinding
    private lateinit var viewModel: RecordViewModel
    private lateinit var viewModelFactory: RecordViewModelFactory
    private lateinit var scoreAdater: ScoreAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRecordBinding.inflate(inflater)
        binding.lifecycleOwner = this

        initViewModel()
        initObservers()

        return binding.root
    }

    private fun initViewModel() {
        val application = requireNotNull(activity).application
        val dataSourceDao = ScoreDatabase.getInstance(requireContext().applicationContext).scoreDatabaseDao
        viewModelFactory = RecordViewModelFactory(
                application,
                dataSourceDao
        )
        viewModel = ViewModelProvider(this, viewModelFactory).get(RecordViewModel::class.java)
        binding.viewModel = viewModel
    }

    private fun initObservers() {
        scoreAdater = ScoreAdapter()
        binding.fragmentRecordScoreList.adapter = scoreAdater

        viewModel.scoreList.observe(viewLifecycleOwner, Observer { list ->
            Timber.i("scoreListSize: ${list.size}")
            scoreAdater.submitList(list)
        })
    }
}