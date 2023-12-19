package com.practicum.playlistmarket.media.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.databinding.FragmentMediaBinding
import com.practicum.playlistmarket.media.ui.MediaPagerAdapter

class MediaFragment : Fragment() {

    //private lateinit var binding : FragmentMediaBinding

    private var _binding : FragmentMediaBinding? = null
    private val binding get() = _binding!!

    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMediaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPageMedia.adapter = MediaPagerAdapter(this)

        tabMediator = TabLayoutMediator(binding.mediaMenu, binding.viewPageMedia) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.selected_tracks)
                1 -> tab.text = getString(R.string.play_lists)
            }
        }
        tabMediator.attach()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabMediator.detach()
        _binding = null
    }
}