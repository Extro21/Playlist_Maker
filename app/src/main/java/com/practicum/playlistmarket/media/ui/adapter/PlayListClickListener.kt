package com.practicum.playlistmarket.media.ui.adapter

import com.practicum.playlistmarket.media.domain.module.PlayList

fun interface PlayListClickListener {

    fun onPlayListClick(playList: PlayList)

}