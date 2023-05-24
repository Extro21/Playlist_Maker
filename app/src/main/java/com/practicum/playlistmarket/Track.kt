package com.practicum.playlistmarket

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Track(
    val artworkUrl100: String,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: String
) : Parcelable {
}