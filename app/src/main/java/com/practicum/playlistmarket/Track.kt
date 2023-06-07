package com.practicum.playlistmarket

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Track(
    val trackId: String,
    val artworkUrl100: String,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: String
) : Parcelable {

//    override fun hashCode(): Int {
//
//        return this.trackId.hashCode()
//    }
//
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (other !is Track) return false
//        return this.trackId == other.trackId
//    }
}