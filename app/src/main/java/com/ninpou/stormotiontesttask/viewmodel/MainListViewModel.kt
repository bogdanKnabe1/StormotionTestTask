package com.ninpou.stormotiontesttask.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.ninpou.stormotiontesttask.data.network.OperationResult
import com.ninpou.stormotiontesttask.model.Data
import com.ninpou.stormotiontesttask.model.SuggestionItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainListViewModel(private val repository: SuggestionItemRepository, app: Application) : AndroidViewModel(app) {

    private val _data = MutableLiveData<List<Data>>().apply { value = emptyList() }
    val suggestionListData: LiveData<List<Data>> = _data

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    fun loadData() {
        _isViewLoading.value = true
        viewModelScope.launch {
            var result: OperationResult<Data> = withContext(Dispatchers.IO) {
                repository.fetchData()
            }
            _isViewLoading.value = false
            when (result) {
                is OperationResult.Success -> {
                    if (result.data.isNullOrEmpty()) {
                        _isEmptyList.value = true
                    } else {
                        _data.value = result.data
                    }
                }
                is OperationResult.Error -> {
                    _onMessageError.value = result.exception!!
                }
            }
        }
    }

    class ViewModelFactory(private val repository: SuggestionItemRepository, val app: Application) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainListViewModel(repository, app) as T
        }
    }
}