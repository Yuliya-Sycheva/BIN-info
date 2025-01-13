package com.example.bin_info.history.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.bin_info.R
import com.example.bin_info.databinding.FragmentHistoryBinding
import com.example.bin_info.history.presentation.HistoryState
import com.example.bin_info.history.presentation.HistoryViewModel
import com.example.bin_info.info.domain.model.Info
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment(R.layout.fragment_history) {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val historyViewModel by viewModel<HistoryViewModel>()
    private var historyAdapter: HistoryAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyAdapter = HistoryAdapter()
        binding.rvHistoryList.adapter = historyAdapter
        historyViewModel.fillData()
        historyViewModel.observeState().observe(viewLifecycleOwner) { render(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        historyAdapter = null
        binding.rvHistoryList.adapter = null
    }

//    override fun onResume() {
//        super.onResume()
//        viewModel.fillData()
//    }

    private fun render(state: HistoryState) {
        when (state) {
            is HistoryState.Empty -> showEmpty()
            is HistoryState.Content -> showContent(state.history)
            is HistoryState.Error -> showNothingFound()
        }
    }

    private fun showEmpty() {
        binding.apply {
            rvHistoryList.isVisible = false
            tvError.isVisible = true
            tvError.text = requireContext().getText(R.string.list_is_empty)
        }
    }

    private fun showNothingFound() {
        binding.apply {
            rvHistoryList.isVisible = false
            tvError.isVisible = true
            tvError.text = requireContext().getText(R.string.no_info)
        }
    }

    private fun showContent(cardsInfo: List<Info>) {
        binding.rvHistoryList.isVisible = true
        binding.tvError.isVisible = false
        historyAdapter?.notifyDataSetChanged()

      historyAdapter?.cards?.clear()
      historyAdapter?.cards?.addAll(cardsInfo)
      historyAdapter?.notifyDataSetChanged()
    }
}