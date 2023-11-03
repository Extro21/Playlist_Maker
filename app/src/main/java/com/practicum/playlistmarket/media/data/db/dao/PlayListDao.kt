package com.practicum.playlistmarket.media.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.playlistmarket.media.data.db.entity.PlayListEntity
import com.practicum.playlistmarket.media.data.db.entity.TrackPlayListEntity
import com.practicum.playlistmarket.media.data.db.entity.TrackPlaylist


@Dao
interface PlayListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayList(playListEntity: PlayListEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTrackForPlaylist(trackPlaylist : TrackPlaylist)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTrack(trackPlaylist: TrackPlayListEntity)


    @Query("SELECT * FROM playlist")
    suspend fun getPlayList(): List<PlayListEntity>

    @Query("SELECT * FROM playlist_for_tracks INNER JOIN tracks_playlist ON playlist_for_tracks.trackId == tracks_playlist.trackId WHERE tracks_playlist.playListId == :playlistId")
    suspend fun getTrackByPlaylist(playlistId : Int): List<TrackPlayListEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM playlist_for_tracks WHERE trackId = :trackId)")
    suspend fun doesTrackExist(trackId: String): Boolean

    @Query("SELECT count(playListId) > 0 FROM tracks_playlist WHERE trackId = :trackId AND playListId = :playlistId")
    suspend fun doesTrackExistPlayList(trackId: String, playlistId : Int): Boolean



}