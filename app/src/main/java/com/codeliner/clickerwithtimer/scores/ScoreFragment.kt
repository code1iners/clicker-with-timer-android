package com.codeliner.clickerwithtimer.scores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.codeliner.clickerwithtimer.databinding.FragmentScoreBinding
import com.codeliner.clickerwithtimer.domains.scores.ScoreDatabase

class ScoreFragment: Fragment() {

    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentScoreBinding.inflate(inflater)
        val application = requireNotNull(activity).application
        binding.lifecycleOwner = this

        // note. init score database source
        val dataSourceDao = ScoreDatabase.getInstance(application).scoreDatabaseDao
        viewModelFactory = ScoreViewModelFactory(
                application,
                ScoreFragmentArgs.fromBundle(requireArguments()).score,
                dataSourceDao
        )
        viewModel = ViewModelProvider(this, viewModelFactory).get(ScoreViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.eventPlayAgain.observe(viewLifecycleOwner, Observer { playAgain ->
            if (playAgain) {
                findNavController().navigate(ScoreFragmentDirections.actionScoreFragmentToClickFragment())
                viewModel.playAgainComplete()
            }
        })

        return binding.root
    }
}