package com.practicum.playlistmarket.data.dto

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

    )