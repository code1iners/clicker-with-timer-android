package com.codeliner.clickerwithtimer.records

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.codeliner.clickerwithtimer.R
import com.codeliner.clickerwithtimer.scores.ScoreAdapter
import com.codeliner.clickerwithtimer.databinding.FragmentRecordBinding
import com.codeliner.clickerwithtimer.domains.scores.Score
import com.codeliner.clickerwithtimer.domains.scores.ScoreDatabase
import com.codeliner.clickerwithtimer.scores.ScoreListener
import timber.log.Timber

class RecordFragment: Fragment()
    ,ScoreListener
{

    private lateinit var binding: FragmentRecordBinding
    private lateinit var viewModel: RecordViewModel
    private lateinit var viewModelFactory: RecordViewModelFactory
    private lateinit var scoreAdater: ScoreAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRecordBinding.inflate(inflater)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)

        initViewModel()
        initObservers()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.record_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.recordMenu_clear -> {
                viewModel.onClear()
            }
        }
        return true
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
        scoreAdater = ScoreAdapter(this)
        binding.fragmentRecordScoreList.adapter = scoreAdater

        viewModel.scoreList.observe(viewLifecycleOwner, Observer { list ->
            scoreAdater.submitList(list)
        })

        // note. for navigate to detail fragment
        viewModel.navigateToScoreDetail.observe(viewLifecycleOwner, Observer { scoreId ->
            scoreId?.let {
                this.findNavController().navigate(
                        RecordFragmentDirections.actionRecordFragmentToDetailFragment(scoreId)
                )
                viewModel.onScoreDetailNavigated()
            }
        })
    }

    override fun onClick(score: Score) {
        viewModel.onScoreClicked(score.id)
    }

    override fun onScore(score: Score) {
        viewModel.onScoreClicked(score.id)
    }

    override fun onCreated(score: Score) {
        viewModel.onScoreClicked(score.id)
    }
}