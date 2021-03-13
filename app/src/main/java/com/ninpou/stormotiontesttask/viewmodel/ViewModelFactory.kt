package com.ninpou.stormotiontesttask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ninpou.stormotiontesttask.model.SuggestionItemRepository

class ViewModelFactory(private val repository: SuggestionItemRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainListViewModel(repository) as T
    }
}