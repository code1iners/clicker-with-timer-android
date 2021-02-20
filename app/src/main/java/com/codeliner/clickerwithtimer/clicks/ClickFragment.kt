package com.codeliner.clickerwithtimer.clicks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codeliner.clickerwithtimer.R
import com.codeliner.clickerwithtimer.databinding.FragmentChangeBinding


class ClickFragment : Fragment() {

    private val viewModel: ChangeViewModel by lazy {
        ViewModelProvider(this).get(ChangeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentChangeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_change, container, false)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        viewModel.number.observe(viewLifecycleOwner, Observer { number ->
            binding.number.text = number.toString()
        })

        return binding.root
    }
}