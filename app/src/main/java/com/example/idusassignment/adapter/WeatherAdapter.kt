package com.example.idusassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.idusassignment.R
import com.example.idusassignment.databinding.RowItemContentBinding
import com.example.idusassignment.viewmodel.MainActivityViewModel

class WeatherAdapter(viewModel: MainActivityViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }
    private val viewModel = viewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == TYPE_HEADER){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item_header, parent, false)
            return HeaderViewHolder(view)
        }
        val binding: RowItemContentBinding = RowItemContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ContentViewHolder){
            holder.bind(viewModel, position)
        }
    }

    override fun getItemCount(): Int {
        return viewModel.getCityItems().size
    }

    override fun getItemViewType(position: Int): Int {
        if(position == 0){
            return TYPE_HEADER
        }
        return TYPE_ITEM
    }

    inner class ContentViewHolder(binding: RowItemContentBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding = binding
        fun bind(viewModel: MainActivityViewModel, pos: Int) {
            binding.pos = pos
            binding.mainActivityViewModel = viewModel
            binding.executePendingBindings()
        }
    }
    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}