package com.codeliner.clickerwithtimer.clicks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.codeliner.clickerwithtimer.databinding.FragmentClickBinding


class ClickFragment : Fragment() {

    private val viewModel: ClickViewModel by lazy {
        ViewModelProvider(this).get(ClickViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentClickBinding.inflate(inflater)
        // note. connect view model with layout
        binding.viewModel = viewModel
        // note. for view model lifecycle
        binding.lifecycleOwner = this

        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer { isFinished ->
            if (isFinished) {
                findNavController().navigate(ClickFragmentDirections.actionClickFragmentToScoreFragment(viewModel.valueScore.value ?: 0))
                viewModel.onEventGameFinishComplete()
            }
        })

        return binding.root
    }
}