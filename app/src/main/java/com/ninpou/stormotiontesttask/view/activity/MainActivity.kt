package com.ninpou.stormotiontesttask.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ninpou.stormotiontesttask.R
import com.ninpou.stormotiontesttask.databinding.ActivityMainBinding
import com.ninpou.stormotiontesttask.di.Injection
import com.ninpou.stormotiontesttask.model.Data
import com.ninpou.stormotiontesttask.view.adapters.SuggestionListAdapter
import com.ninpou.stormotiontesttask.viewmodel.MainListViewModel
import com.ninpou.stormotiontesttask.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityBinding: ActivityMainBinding
    private lateinit var viewModel: MainListViewModel
    private lateinit var adapter: SuggestionListAdapter

    companion object {
        const val TAG = "ASD"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainActivityBinding.root
        setContentView(view)

        setUpToolbar()
        setupViewModel()
        setupUI()
    }

    private fun setUpToolbar() {
        val toolbar = mainActivityBinding.baseToolbar.toolbarLayout
        toolbar.setTitle(R.string.main_screen_title)
        setSupportActionBar(toolbar)

    }

    //ui
    private fun setupUI() {
        adapter = SuggestionListAdapter(this, viewModel.suggestionListData.value ?: emptyList())
        mainActivityBinding.listRecycler.layoutManager = LinearLayoutManager(this)
        mainActivityBinding.listRecycler.adapter = adapter
    }

    //view model
    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory(Injection.providerRepository()))
            .get(MainListViewModel::class.java)
        viewModel.suggestionListData.observe(this, renderData)

        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.isEmptyList.observe(this, emptyListObserver)
    }

    //observers
    private val renderData = Observer<List<Data>> {
        Log.v(TAG, "data updated $it")
        mainActivityBinding.layoutError.layoutErrorRoot.visibility = View.GONE
        mainActivityBinding.layoutEmpty.layoutEmptyRoot.visibility = View.GONE
        adapter.update(it)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.v(TAG, "isViewLoading $it")
        val visibility = if (it) View.VISIBLE else View.GONE
        mainActivityBinding.progressBar.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.v(TAG, "onMessageError $it")
        mainActivityBinding.layoutError.layoutErrorRoot.visibility = View.VISIBLE
        mainActivityBinding.layoutEmpty.layoutEmptyRoot.visibility = View.GONE
        mainActivityBinding.layoutError.textViewError.text = getString(R.string.an_error_occurred)
    }

    private val emptyListObserver = Observer<Boolean> {
        Log.v(TAG, "emptyListObserver $it")
        mainActivityBinding.layoutEmpty.layoutEmptyRoot.visibility = View.VISIBLE
        mainActivityBinding.layoutError.layoutErrorRoot.visibility = View.GONE
    }

    //If we require to updated data, we can call the method "loadData" here
    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }
}
