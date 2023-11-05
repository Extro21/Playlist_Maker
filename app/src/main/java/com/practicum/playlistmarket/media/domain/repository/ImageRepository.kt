package com.practicum.playlistmarket.media.domain.repository

import android.net.Uri

interface ImageRepository {

    fun saveImageToPrivateStorage(uri: Uri)

    fun getUri(uriPlaylist: String): String
}