package com.practicum.playlistmarket.media.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_for_tracks")
data class TrackPlayListEntity(
    @PrimaryKey
    val trackId: String,
    @ColumnInfo(name = "artworkUrl100")
    val artworkUrl100: String?,
    @ColumnInfo(name = "trackName")
    val trackName: String,
    @ColumnInfo(name = "artistName")
    val artistName: String,
    @ColumnInfo(name = "trackTimeMillis")
    val trackTimeMillis: String?,
    @ColumnInfo(name = "collectionName")
    val collectionName: String?,
    @ColumnInfo("releaseDate")
    val releaseDate: String?,
    @ColumnInfo("primaryGenreName")
    val primaryGenreName: String?,
    @ColumnInfo("country")
    val country: String?,
    @ColumnInfo("previewUrl")
    val previewUrl: String?,
    @ColumnInfo("isFavorite")
    val isFavorite : Boolean
)