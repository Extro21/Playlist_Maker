package com.practicum.playlistmarket.media.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.practicum.playlistmarket.databinding.FragmentPlayListBinding
import com.practicum.playlistmarket.media.ui.view_model.PlayListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentPlayList : Fragment() {

    private val viewModel: PlayListViewModel by viewModel()

    private lateinit var binding: FragmentPlayListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayListBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    companion object {
        fun newInstance() = FragmentPlayList()
    }
}