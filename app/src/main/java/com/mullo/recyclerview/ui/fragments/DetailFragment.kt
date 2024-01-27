package com.mullo.recyclerview.ui.fragments

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.mullo.recyclerview.R
import com.mullo.recyclerview.databinding.FragmentDetailBinding
import com.mullo.recyclerview.databinding.ItemsUsersBinding


class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    val args : DetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            FragmentDetailBinding.bind(inflater.inflate(R.layout.fragment_detail, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtIdAnime.text = args.idAnime.toString()
        binding.txtNameAnime.text = args.name
    }

}