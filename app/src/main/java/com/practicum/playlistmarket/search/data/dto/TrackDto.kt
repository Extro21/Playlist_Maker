package com.practicum.playlistmarket.search.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackDto(
    val trackId: String,
    val artworkUrl100: String,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: String,
    val collectionName:String,
    val releaseDate: String,
    val primaryGenreName: String,
    val country: String,
    val previewUrl: String,
    ) : Parcelable

