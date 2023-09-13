package com.practicum.playlistmarket.media.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.practicum.playlistmarket.databinding.FragmentSelectedTracksBinding
import com.practicum.playlistmarket.media.ui.view_model.SelectedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentSelectedTracks : Fragment() {

    private val viewModel: SelectedViewModel by viewModel()


    private lateinit var binding: FragmentSelectedTracksBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectedTracksBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentSelectedTracks()
    }
}