package com.example.bin_info.info.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.bin_info.R
import com.example.bin_info.databinding.ActivityMainBinding
import com.example.bin_info.info.domain.model.Info
import com.example.bin_info.info.presentation.InfoViewModel
import com.example.bin_info.info.ui.models.InfoScreenState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val infoViewModel by viewModel<InfoViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализация обработчиков
        initQueryChangeListener()
        initClickListeners()

        infoViewModel.getStateLiveData().observe(this) { renderState(it) }
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
                infoViewModel.searchDebounce(s.toString())  //searchDebounce вместо onQueryChange
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
            binding.etSearchRequest.apply {
                setText("")
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
        }
    }

    private fun showLoading() {
        with(binding) {
            progressBar.isVisible = true
            cvSearchResult.isVisible = false
        }
    }

    private fun showDefault() {
        with(binding) {
            progressBar.isVisible = false
            ivClearRequest.isVisible = false
            cvSearchResult.isVisible = false
            tvError.isVisible = false
        }
    }

    private fun showError(text: Int? = null) {
        with(binding) {
            progressBar.isVisible = false
            cvSearchResult.isVisible = false
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
            cvSearchResult.isVisible = true
            tvError.isVisible = false
            tvScheme.setFieldText(info.scheme)
            tvCardType.setFieldText(info.type)
            tvCardBrand.setFieldText(info.brand)
            tvPrepaid.setFieldText(info.prepaid.toString()) //переделать
            tvCountry.setFieldText(info.countryName)
            tvCoordinates.setFieldText(info.countryLatitude.toString()) //исправить
            tvBankName.setFieldText(info.bankName)
            tvBankUrl.setFieldText(info.bankUrl)
            tvBankCity.setFieldText(info.bankCity)
        }
    }

    private fun TextView.setFieldText(text: String?): String {
        return text ?: "-"
    }
}