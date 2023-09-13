package com.practicum.playlistmarket.media.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MediaPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {


    private val fragments = listOf(
        FragmentSelectedTracks.newInstance(),
        FragmentPlayList.newInstance()
    )

    override fun getItemCount(): Int = fragments.size


    override fun createFragment(position: Int): Fragment = fragments[position]

}