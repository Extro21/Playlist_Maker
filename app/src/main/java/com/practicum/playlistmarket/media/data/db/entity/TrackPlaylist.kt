package com.practicum.playlistmarket.media.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "tracks_playlist", primaryKeys = ["trackId", "playListId"],
    foreignKeys = [
        ForeignKey(
            entity = TrackPlayListEntity::class,
            parentColumns = ["trackId"],
            childColumns = ["trackId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PlayListEntity::class,
            parentColumns = ["playListId"],
            childColumns = ["playListId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class TrackPlaylist(
    val playListId: Int,
    val trackId: String,
)