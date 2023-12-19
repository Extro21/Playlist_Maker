package com.practicum.playlistmarket.media.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.playlistmarket.media.data.db.entity.TrackEntity

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackFavorite(track: TrackEntity)

    @Query("SELECT * FROM favorite_tracks")
    suspend fun getTrackFavorite(): List<TrackEntity>

    @Query("SELECT trackId FROM favorite_tracks")
    suspend fun getTrackFavoriteId(): List<String>

    @Query("SELECT * FROM favorite_tracks WHERE trackId = :idTrack")
    suspend fun getTrackFavoriteForId(idTrack : String): TrackEntity

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_tracks WHERE trackId = :trackId)")
    suspend fun doesTrackExist(trackId: String): Boolean

    @Query("DELETE FROM favorite_tracks WHERE trackId is :id")
    suspend fun deleteTrackFavorite(id: String)

}