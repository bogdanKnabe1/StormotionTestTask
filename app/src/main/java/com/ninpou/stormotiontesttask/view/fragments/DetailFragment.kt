package com.ninpou.stormotiontesttask.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.ninpou.stormotiontesttask.R
import com.ninpou.stormotiontesttask.databinding.FragmentDetailBinding
import java.util.*


class DetailFragment : Fragment() {

    private var fragmentDetailBinding: FragmentDetailBinding? = null
    lateinit var navController: NavController

    // passed arguments from recycler view
    lateinit var title: String
    lateinit var subTitle: String
    lateinit var image: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = requireArguments().getString("title").toString()
        subTitle = requireArguments().getString("subtitle").toString()
        image = requireArguments().getString("image").toString()

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


        loadUIFromRecycler()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        navController = Navigation.findNavController(view)
        setUpToolbar()
    }

    fun loadUIFromRecycler() {
        detailBinding.titleInDetails.text = title.capitalize(Locale.ROOT)
        detailBinding.subtitleInDetails.text = subTitle.capitalize(Locale.ROOT)
        detailBinding.detailsImageView.load(image) {
            crossfade(true)
            crossfade(700)
            placeholder(R.drawable.ic_launcher_foreground)
            transformations(CircleCropTransformation())
        }
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