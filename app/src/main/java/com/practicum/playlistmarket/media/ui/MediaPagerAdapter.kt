package com.practicum.playlistmarket.media.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MediaPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {


    private val fragments = listOf(
        FragmentSelectedTracks.newInstance(),
        FragmentPlayList.newInstance()
    )

    override fun getItemCount(): Int = fragments.size


    override fun createFragment(position: Int): Fragment = fragments[position]

}