package com.ninpou.stormotiontesttask.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ninpou.stormotiontesttask.R
import com.ninpou.stormotiontesttask.databinding.FragmentMainListBinding
import com.ninpou.stormotiontesttask.di.DependencyInjectionTemplate
import com.ninpou.stormotiontesttask.model.Data
import com.ninpou.stormotiontesttask.view.adapters.SuggestionListAdapter
import com.ninpou.stormotiontesttask.viewmodel.MainListViewModel

class MainListFragment : Fragment(), SuggestionListAdapter.ClickItem {

    private var fragmentMainListBinding: FragmentMainListBinding? = null
    private lateinit var adapter: SuggestionListAdapter

    private val viewModel: MainListViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(
            this,
            MainListViewModel.ViewModelFactory(
                DependencyInjectionTemplate.providerRepository(),
                activity.application
            )
        )
            .get(MainListViewModel::class.java)
    }

    private val mainListBinding get() = fragmentMainListBinding!!

    companion object {
        const val TAG = "ASD"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentMainListBinding = FragmentMainListBinding.inflate(inflater, container, false)
        val view = mainListBinding.root

        setUpToolbar()
        setupUI()
        setupViewModel()
        return view
    }

    private fun setUpToolbar() {
        val toolbar = mainListBinding.baseToolbar.toolbarLayout
        toolbar.setTitle(R.string.main_screen_title)
        //setSupportActionBar(toolbar)
    }

    //ui
    private fun setupUI() {
        adapter = createAdapter()
        mainListBinding.listRecycler.layoutManager = LinearLayoutManager(requireActivity())
        mainListBinding.listRecycler.adapter = adapter
    }

    //view model
    private fun setupViewModel() {

        viewModel.suggestionListData.observe(viewLifecycleOwner, renderData)

        viewModel.isViewLoading.observe(viewLifecycleOwner, isViewLoadingObserver)
        viewModel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)
        viewModel.isEmptyList.observe(viewLifecycleOwner, emptyListObserver)
    }

    //observers
    private val renderData = Observer<List<Data>> {
        Log.v(TAG, "data updated $it")
        mainListBinding.layoutError.layoutErrorRoot.visibility = View.GONE
        mainListBinding.layoutEmpty.layoutEmptyRoot.visibility = View.GONE
        adapter.update(it)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.v(TAG, "isViewLoading $it")
        val visibility = if (it) View.VISIBLE else View.GONE
        mainListBinding.progressBar.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.v(TAG, "onMessageError $it")
        mainListBinding.layoutError.layoutErrorRoot.visibility = View.VISIBLE
        mainListBinding.layoutEmpty.layoutEmptyRoot.visibility = View.GONE
        mainListBinding.layoutError.textViewError.text = getString(R.string.an_error_occurred)
    }

    private val emptyListObserver = Observer<Boolean> {
        Log.v(TAG, "emptyListObserver $it")
        mainListBinding.layoutEmpty.layoutEmptyRoot.visibility = View.VISIBLE
        mainListBinding.layoutError.layoutErrorRoot.visibility = View.GONE
    }

    //If we require to updated data, we can call the method "loadData" here
    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }


    private fun createAdapter(): SuggestionListAdapter {
        return SuggestionListAdapter(
            this,
            requireContext(),
            viewModel.suggestionListData.value ?: emptyList()
        )
    }

    override fun onClickListener(test: String) {
        val dialogName = AlertDialog.Builder(requireActivity())
        dialogName.setTitle("TEST CLICK")
        dialogName.setMessage(" $test --- $test")
        dialogName.setIcon(R.mipmap.ic_launcher)
        val dialog = dialogName.create()
        dialog.show()
    }
}