package com.example.bin_info.info.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bin_info.R
import com.example.bin_info.common.util.Functions
import com.example.bin_info.databinding.FragmentInfoBinding
import com.example.bin_info.databinding.ItemListBinding
import com.example.bin_info.info.domain.model.Info
import com.example.bin_info.info.presentation.InfoViewModel
import com.example.bin_info.info.ui.models.InfoScreenState
import org.koin.androidx.viewmodel.ext.android.viewModel

class InfoFragment : Fragment() {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private val infoViewModel by viewModel<InfoViewModel>()
    private lateinit var searchResultBinding: ItemListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchResultBinding = ItemListBinding.bind(binding.includeSearchResult.root)
        initQueryChangeListener()
        initClickListeners()
        binding.tvCardsInHistory.setOnClickListener {
            openHistory()
        }

        infoViewModel.getStateLiveData().observe(viewLifecycleOwner) { renderState(it) }
    }

    private fun initQueryChangeListener() {
        binding.etSearchRequest.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // Empty
            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
                // Empty
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isEmpty()) {
                    binding.ivClearRequest.isVisible = false
                } else {
                    binding.ivClearRequest.isVisible = true

                }
                infoViewModel.searchDebounce(s.toString())
            }
        })
    }

    private fun initClickListeners() {
        binding.etSearchRequest.setOnEditorActionListener { textview, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val query = binding.etSearchRequest.text.toString()
                if (query.isNotEmpty()) {
                    infoViewModel.searchDebounce(query)
                }
                textview.clearFocus()
            }
            false
        }
        binding.ivClearRequest.setOnClickListener {
            with(binding) {
                etSearchRequest.apply {
                    setText("")
                }
                searchResultBinding.cvSearchResult.isVisible = false
                tvError.isVisible = false
            }
        }
    }

    private fun renderState(state: InfoScreenState) {
        when (state) {
            is InfoScreenState.InternetError -> showError(
                R.string.no_internet
            )

            is InfoScreenState.ServerError -> showError(
                R.string.server_error
            )

            is InfoScreenState.NothingFound -> {
                showError(R.string.nothing_found)
            }

            is InfoScreenState.LimitError -> {
                showError(R.string.limit_error)
            }

            is InfoScreenState.Loading -> showLoading()
            is InfoScreenState.Default -> showDefault()
            is InfoScreenState.Content -> showContent(state.info)
            else -> {}
        }
    }

    private fun showLoading() {
        with(binding) {
            progressBar.isVisible = true
            searchResultBinding.cvSearchResult.isVisible = false
            tvError.isVisible = false
        }
    }

    private fun showDefault() {
        with(binding) {
            progressBar.isVisible = false
            ivClearRequest.isVisible = false
            searchResultBinding.cvSearchResult.isVisible = false
            tvError.isVisible = false
        }
    }

    private fun showError(text: Int? = null) {
        with(binding) {
            progressBar.isVisible = false
            searchResultBinding.cvSearchResult.isVisible = false
            tvError.isVisible = true
            if (text == null) {
                tvError.text = ""
            } else {
                tvError.setText(text)
            }
        }
    }

    private fun showContent(info: Info) {
        with(binding) {
            progressBar.isVisible = false
            searchResultBinding.cvSearchResult.isVisible = true
            searchResultBinding.tvBin.isVisible = false
            tvError.isVisible = false
            searchResultBinding.tvScheme.text = getString(R.string.card_type, info.scheme)
            searchResultBinding.tvCardType.text = getString(R.string.card_type, info.type)
            searchResultBinding.tvCardBrand.text = getString(R.string.card_brand, info.brand)
            searchResultBinding.tvPrepaid.text =
                getString(
                    R.string.prepaid,
                    if (info.prepaid != null) {
                        if (info.prepaid == true) getString(R.string.yes) else getString(R.string.no)
                    } else {
                        "-"
                    }
                )
            searchResultBinding.tvCountry.text = getString(R.string.country, info.countryName)
            searchResultBinding.tvCoordinates.text =
                getString(
                    R.string.coordinates,
                    Functions.formatCoordinates(info.countryLatitude, info.countryLongitude)
                )
            searchResultBinding.tvCoordinates.setOnClickListener {
                infoViewModel.handleCoordinatesClick(requireContext(), info)
            }
            searchResultBinding.tvBankName.text = getString(R.string.bank, info.bankName)
            searchResultBinding.tvBankUrl.text = getString(R.string.bank_url, info.bankUrl ?: "-")
            searchResultBinding.tvBankPhone.text =
                getString(R.string.bank_phone, info.bankPhone ?: "-")
            searchResultBinding.tvBankCity.text = getString(R.string.bank_city, info.bankCity)
        }
    }

    private fun openHistory() {
        val action = InfoFragmentDirections.actionInfoFragmentToHistoryFragment()
        findNavController().navigate(action)
    }
}