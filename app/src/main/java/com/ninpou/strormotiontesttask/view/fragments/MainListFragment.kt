package com.ninpou.strormotiontesttask.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ninpou.strormotiontesttask.databinding.FragmentMainListBinding


class MainListFragment : Fragment() {

    private var fragmentListBinding: FragmentMainListBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = fragmentListBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentListBinding = FragmentMainListBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

}