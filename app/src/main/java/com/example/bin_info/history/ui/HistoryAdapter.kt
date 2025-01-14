package com.example.bin_info.history.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bin_info.databinding.ItemListBinding
import com.example.bin_info.info.domain.model.Info

class HistoryAdapter() :
    RecyclerView.Adapter<HistoryViewHolder>() {

    val cards: MutableList<Info> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding =
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(cards[position])
    }
}