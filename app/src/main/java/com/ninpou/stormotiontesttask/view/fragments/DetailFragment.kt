package com.ninpou.stormotiontesttask.view.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.ninpou.stormotiontesttask.R
import com.ninpou.stormotiontesttask.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private var fragmentDetailBinding: FragmentDetailBinding? = null
    lateinit var navController: NavController

    // This property is only valid between onCreateView and onDestroyView.
    private val detailBinding get() = fragmentDetailBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentDetailBinding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = detailBinding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        navController = Navigation.findNavController(view)
        setUpToolbar()
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