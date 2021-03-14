package com.ninpou.stormotiontesttask.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.ninpou.stormotiontesttask.R
import com.ninpou.stormotiontesttask.databinding.RowHeaderBinding
import com.ninpou.stormotiontesttask.databinding.RowSuggestionBinding
import com.ninpou.stormotiontesttask.model.Data
import java.util.*


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class SuggestionListAdapter(
    private val context: Context,
    private var data: List<Data>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == VIEW_TYPE_ONE) {
            return MViewHolderHeaderItem(
                LayoutInflater.from(context)
                    .inflate(R.layout.row_header, parent, false)
            )
        }
        return MViewHolderDataItem(
            LayoutInflater.from(context).inflate(R.layout.row_suggestion, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (data[position].type === VIEW_TYPE_ONE) {
            (holder as MViewHolderHeaderItem).bind(position)
        } else {
            (holder as MViewHolderDataItem).bind(position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun update(data: List<Data>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class MViewHolderHeaderItem(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RowHeaderBinding.bind(view)

        fun bind(position: Int) {
            val recyclerViewModelHeaderItem = data[position]
            binding.headerTextView.text =
                recyclerViewModelHeaderItem.headerTitle.capitalize(Locale.ROOT)
        }
    }

    inner class MViewHolderDataItem(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RowSuggestionBinding.bind(view)

        fun bind(position: Int) {
            with(binding) {
                val recyclerViewModelDataItem = data[position]
                itemView.setOnClickListener {
                    val navController: NavController?
                    navController = Navigation.findNavController(itemView)
                    navController.navigate(R.id.action_mainFragment_to_detailsFragment)

                }
                textViewTitle.text = recyclerViewModelDataItem.title.capitalize(Locale.ROOT)
                textViewSubtitle.text = recyclerViewModelDataItem.subTitle.capitalize(Locale.ROOT)
                mainListImageView.load(recyclerViewModelDataItem.image) {
                    crossfade(true)
                    crossfade(1000)
                    placeholder(R.drawable.ic_launcher_foreground)
                    transformations(CircleCropTransformation())
                }
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return data[position].type
    }
}
