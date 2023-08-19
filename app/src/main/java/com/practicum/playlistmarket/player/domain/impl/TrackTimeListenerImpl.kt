package com.practicum.playlistmarket.player.domain.impl

import com.practicum.playlistmarket.player.domain.api.TrackTimeListener
import com.practicum.playlistmarket.player.domain.repository.PlayerRepository
import com.practicum.playlistmarket.search.domain.api.TrackRepository

class TrackTimeListenerImpl(private val repository: PlayerRepository) : TrackTimeListener{

//    var time = "00:00"
//
//    override fun setTimeListener(time : String) {
//        this.time = time
//        onTimeChanged()
//
//    }
//
//
//   fun onTimeChanged() : String {
//        return time
//    }
//
//    override fun onTimeChanged(time: String): String {
//
//    }
}