package com.codeliner.clickerwithtimer.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.codeliner.clickerwithtimer.databinding.FragmentDetailBinding
import com.codeliner.clickerwithtimer.domains.scores.ScoreDatabase

class DetailFragment: Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModelFactory: DetailViewModelFactory
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        initViewModel()

        viewModel.score.observe(viewLifecycleOwner, Observer { score ->
            if (score == null) {
                this.findNavController().popBackStack()
            }
        })

        return binding.root
    }

    private fun initViewModel() {
        val application = requireNotNull(activity).application
        val dataSourceDao = ScoreDatabase.getInstance(requireContext().applicationContext).scoreDatabaseDao
        viewModelFactory = DetailViewModelFactory(
                application,
                dataSourceDao,
                DetailFragmentArgs.fromBundle(requireArguments()).scoreId
        )
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        binding.viewModel = viewModel
    }
}