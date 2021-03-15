package com.ninpou.stormotiontesttask.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.ninpou.stormotiontesttask.R
import com.ninpou.stormotiontesttask.databinding.FragmentDetailBinding
import com.ninpou.stormotiontesttask.di.DependencyInjectionTemplate
import com.ninpou.stormotiontesttask.model.Data
import com.ninpou.stormotiontesttask.model.DataVideo
import com.ninpou.stormotiontesttask.view.fragments.MainListFragment.Companion.TAG
import com.ninpou.stormotiontesttask.viewmodel.SharedViewModel
import java.util.*


class DetailFragment : Fragment() {

    private var fragmentDetailBinding: FragmentDetailBinding? = null
    lateinit var navController: NavController

    // passed arguments from recycler view
    lateinit var title: String
    lateinit var subTitle: String
    lateinit var image: String
    private var position: Int = 0

    // view model
    private val sharedViewModel: SharedViewModel by activityViewModels {
        val application = requireActivity().application
        SharedViewModel.ViewModelFactory(
            DependencyInjectionTemplate.providerRepository(),
            application
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = requireArguments().getString("title").toString()
        subTitle = requireArguments().getString("subtitle").toString()
        image = requireArguments().getString("image").toString()
        position = requireArguments().getInt("position")
    }

    // This property is only valid between onCreateView and onDestroyView.
    private val detailBinding get() = fragmentDetailBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentDetailBinding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = detailBinding.root

        setupViewModel()
        loadDataUIFromRecycler()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        navController = Navigation.findNavController(view)
        setUpToolbar()
    }

    private fun loadDataUIFromRecycler() {
        detailBinding.titleInDetails.text = title.capitalize(Locale.ROOT)
        detailBinding.subtitleInDetails.text = subTitle.capitalize(Locale.ROOT)
        detailBinding.detailsImageView.load(image) {
            crossfade(true)
            crossfade(700)
            placeholder(R.drawable.ic_launcher_foreground)
            transformations(CircleCropTransformation())
        }
    }

    private fun loadVideoAndDescriptionUI(position: Int, videoList: List<DataVideo>) {
        // crash -> empty list
        // detailBinding.textDescription.text = videoList[position].description

        val mediaController = MediaController(requireActivity())
        mediaController.setAnchorView(detailBinding.videoView)
        //detailBinding.videoView.setVideoPath(videoList[position].videoUrl)
        // hardcoded
        detailBinding.videoView.setVideoPath("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
        detailBinding.videoView.start()
        detailBinding.videoView.setMediaController(mediaController)
    }

    private fun setupViewModel() {
        sharedViewModel.videoListData.observe(viewLifecycleOwner, videoData)

        sharedViewModel.isViewLoading.observe(viewLifecycleOwner, isViewLoadingObserver)
        sharedViewModel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)
        sharedViewModel.isEmptyList.observe(viewLifecycleOwner, emptyListObserver)
    }

    private val videoData = Observer<List<DataVideo>> {
        Log.v(TAG, "data updated $it")
        detailBinding.layoutError.layoutErrorRoot.visibility = View.GONE
        detailBinding.layoutEmpty.layoutEmptyRoot.visibility = View.GONE

        loadVideoAndDescriptionUI(position, sharedViewModel.videoListData.value ?: emptyList())
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.v(TAG, "isViewLoading $it")
        val visibility = if (it) View.VISIBLE else View.GONE
        detailBinding.progressBarDetails.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.v(TAG, "onMessageError $it")
        detailBinding.layoutError.layoutErrorRoot.visibility = View.VISIBLE
        detailBinding.layoutEmpty.layoutEmptyRoot.visibility = View.GONE
        detailBinding.layoutError.textViewError.text = getString(R.string.an_error_occurred)
    }

    private val emptyListObserver = Observer<Boolean> {
        Log.v(TAG, "emptyListObserver $it")
        detailBinding.layoutEmpty.layoutEmptyRoot.visibility = View.VISIBLE
        detailBinding.layoutError.layoutErrorRoot.visibility = View.GONE
    }


    override fun onResume() {
        super.onResume()
        sharedViewModel.loadVideoData()
    }

    private fun setUpToolbar() {
        with(detailBinding) {
            detailsToolbar.toolbarLayout.setupWithNavController(navController)
            detailsToolbar.toolbarLayout.setTitle(R.string.details_screen_title)
            detailsToolbar.toolbarLayout.inflateMenu(R.menu.menu_details)
            detailsToolbar.toolbarLayout.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_settings -> {
                        Toast.makeText(
                            requireActivity(),
                            "Setting item clicked !",
                            Toast.LENGTH_SHORT
                        ).show()
                        true
                    }
                    else -> false
                }
            }
        }
    }
}