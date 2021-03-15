package com.ninpou.stormotiontesttask.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ninpou.stormotiontesttask.R
import com.ninpou.stormotiontesttask.databinding.FragmentMainListBinding
import com.ninpou.stormotiontesttask.di.DependencyInjectionTemplate
import com.ninpou.stormotiontesttask.model.Data
import com.ninpou.stormotiontesttask.view.adapters.SuggestionListAdapter
import com.ninpou.stormotiontesttask.viewmodel.SharedViewModel

class MainListFragment : Fragment() {

    private var fragmentMainListBinding: FragmentMainListBinding? = null
    private lateinit var adapter: SuggestionListAdapter
    lateinit var navController: NavController

    /*private val viewModel: SharedViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "viewModel can be accessed after onActivityCreated()"
        }
        // check deprecate
        ViewModelProviders.of(
            this,
            SharedViewModel.ViewModelFactory(
                DependencyInjectionTemplate.providerRepository(),
                activity.application
            )
        )
            .get(SharedViewModel::class.java)
    }*/

    private val viewModel: SharedViewModel by activityViewModels {
        val application = requireActivity().application
        SharedViewModel.ViewModelFactory(
            DependencyInjectionTemplate.providerRepository(),
            application)
    }

    private val mainListBinding get() = fragmentMainListBinding!!

    companion object {
        const val TAG = "ASD"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setUpToolbar()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentMainListBinding = FragmentMainListBinding.inflate(inflater, container, false)
        val view = mainListBinding.root

        setupUI()
        setupViewModel()
        return view
    }

    private fun setUpToolbar() {
        with(mainListBinding) {
            baseToolbar.toolbarLayout.setupWithNavController(navController)
            baseToolbar.toolbarLayout.setTitle(R.string.main_screen_title)
        }
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
            requireContext(),
            viewModel.suggestionListData.value ?: emptyList(),
        )
    }
}