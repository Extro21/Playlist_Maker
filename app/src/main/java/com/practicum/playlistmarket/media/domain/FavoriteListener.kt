package com.practicum.playlistmarket.media.domain

interface FavoriteListener {
    fun onFavoriteUpdate(isLiked: Boolean)
}