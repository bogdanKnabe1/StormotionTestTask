package com.ninpou.stormotiontesttask.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.ninpou.stormotiontesttask.R
import com.ninpou.stormotiontesttask.databinding.RowSuggestionBinding
import com.ninpou.stormotiontesttask.model.Data

class SuggestionListAdapter(private var data: List<Data>) :
    RecyclerView.Adapter<SuggestionListAdapter.MViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_suggestion, parent, false)
        return MViewHolder(view)
    }

    override fun onBindViewHolder(vh: MViewHolder, position: Int) {
        //render
        vh.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun update(data: List<Data>) {
        this.data = data
        notifyDataSetChanged()
    }

    class MViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RowSuggestionBinding.bind(view)

        fun bind(data: Data) {
            with(binding) {
                textViewTitle.text = data.title
                textViewSubtitle.text = data.subTitle
                mainListImageView.load(data.image) {
                    crossfade(true)
                    crossfade(1000)
                    placeholder(R.drawable.ic_launcher_foreground)
                    transformations(CircleCropTransformation())
                }
            }
        }
    }
}