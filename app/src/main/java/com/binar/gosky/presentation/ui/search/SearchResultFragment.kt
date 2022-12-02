package com.binar.gosky.presentation.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.gosky.databinding.FragmentSearchResultBinding
import com.binar.gosky.presentation.ui.search.adapter.SearchResultAdapter
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchResultViewModel by viewModels()
    private val args: SearchResultFragmentArgs by navArgs()

    private val adapter: SearchResultAdapter by lazy {
        SearchResultAdapter {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.searchTickets.let {
            viewModel.getTickets(
                it.category,
                it.from,
                it.to,
                it.departureTime,
                it.returnTime,
            )
        }
        Log.d("args", args.searchTickets.toString())

        initList()
        observeData()
    }

    private fun initList() {
        binding.rcvTrip.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SearchResultFragment.adapter
        }
    }

    private fun observeData() {
        viewModel.ticketsResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    adapter.submitList(it.payload)
                }
                is Resource.Loading -> {}
                is Resource.Error -> {}
                is Resource.Empty -> TODO()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}