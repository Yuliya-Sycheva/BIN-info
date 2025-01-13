package com.example.bin_info.history.ui

import androidx.recyclerview.widget.RecyclerView
import com.example.bin_info.R
import com.example.bin_info.common.util.Functions
import com.example.bin_info.databinding.ItemListBinding
import com.example.bin_info.info.domain.model.Info

class HistoryViewHolder(private val binding: ItemListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(info: Info) {
        with(binding) {
            tvScheme.text = root.context.getString(R.string.card_type, info.scheme)
            tvCardType.text = root.context.getString(R.string.card_type, info.type)
            tvCardBrand.text = root.context.getString(R.string.card_brand, info.brand)
            tvPrepaid.text =
                root.context.getString(
                    R.string.prepaid,
                    if (info.prepaid != null) {
                        if (info.prepaid == true) root.context.getString(R.string.yes) else root.context.getString(
                            R.string.no
                        )
                    } else {
                        "-"
                    }
                )
            tvCountry.text = root.context.getString(R.string.country, info.countryName)
            tvCoordinates.text =
                root.context.getString(
                    R.string.coordinates,
                    Functions.formatCoordinates(info.countryLatitude, info.countryLongitude)
                )
            tvBankName.text = root.context.getString(R.string.bank, info.bankName)
            tvBankUrl.text = root.context.getString(R.string.bank_url, info.bankUrl ?: "-")
            tvBankPhone.text = root.context.getString(R.string.bank_phone, info.bankPhone ?: "-")
            tvBankCity.text = root.context.getString(R.string.bank_city, info.bankCity)
        }
    }
}