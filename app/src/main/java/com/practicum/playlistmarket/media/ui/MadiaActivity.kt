package com.practicum.playlistmarket.media.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.databinding.ActivityMadiaBinding

class MediaActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMadiaBinding
    private lateinit var tabMediator : TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMadiaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPageMedia.adapter = MediaPagerAdapter(supportFragmentManager, lifecycle)

        tabMediator = TabLayoutMediator(binding.mediaMenu, binding.viewPageMedia) {tab, position ->
            when(position) {
                0 -> tab.text = getString(R.string.selected_tracks)
                1 -> tab.text = getString(R.string.play_list)
            }
        }
        tabMediator.attach()

        binding.mediaToolbar.setNavigationOnClickListener{
            finish()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
    }

}