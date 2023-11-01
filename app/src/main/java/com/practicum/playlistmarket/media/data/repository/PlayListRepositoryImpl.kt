package com.practicum.playlistmarket.media.data.repository

import android.util.Log
import androidx.room.Database
import com.practicum.playlistmarket.media.data.db.AppDataBase
import com.practicum.playlistmarket.media.data.db.PlayListConvector
import com.practicum.playlistmarket.media.data.db.TrackDbConvertor
import com.practicum.playlistmarket.media.data.db.entity.PlayListEntity
import com.practicum.playlistmarket.media.data.db.entity.TrackEntity
import com.practicum.playlistmarket.media.data.db.entity.TrackPlayListEntity
import com.practicum.playlistmarket.media.data.db.entity.TrackPlaylist
import com.practicum.playlistmarket.media.domain.module.PlayList
import com.practicum.playlistmarket.media.domain.repository.PlayListRepository
import com.practicum.playlistmarket.player.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlayListRepositoryImpl(
    val database: AppDataBase, val playListConvector: PlayListConvector,
) :
    PlayListRepository {

    override suspend fun addTrackPlaylist(track: Track, playList: PlayList) : Boolean {
       // Log.e("addTrackPlaylist", "${track.trackId} ${playList.playListId}")
        if(!database.playListDao().doesTrackExist(trackId = track.trackId)){
            database.playListDao().addTrack(trackConvectorEntity(track))
        }
        return if(!database.playListDao().doesTrackExistPlayList(trackId = track.trackId, playList.playListId)){
            Log.e("addTrackPlaylist", "${track.trackId} ${playList.playListId}")
            database.playListDao()
                .addTrackForPlaylist(TrackPlaylist(trackId = track.trackId, playListId = playList.playListId))
            Log.e("addTrackPlaylist", "true")
            true
        } else {
            Log.e("addTrackPlaylist", "false")
            false
        }
    }



    override suspend fun getTracksForPlaylist(playList: PlayList): Flow<List<Track>> = flow {
        val tracks = database.playListDao().getTrackByPlaylist(playList.playListId)
        Log.e("getTrack", "${trackConvectorTracks(tracks)}")
        emit(trackConvectorTracks(tracks))
    }

    override suspend fun getTracksForPlaylistCount(playList: PlayList) : Int {
      return  database.playListDao().getTrackByPlaylist(playList.playListId).size
    }

    private fun trackConvectorEntity(track: Track): TrackPlayListEntity {
        return playListConvector.map(track)
    }

    private fun trackConvectorTracks(tracks: List<TrackPlayListEntity>): List<Track> {
        return tracks.map { tracks -> playListConvector.map(tracks) }
    }

    override suspend fun addPlayList(playList: PlayList) {
        database.playListDao().insertPlayList(playListConvectorEntity(playList))
    }

    override fun getPlaylist(): Flow<List<PlayList>> = flow {
        val playList = database.playListDao().getPlayList()
        emit(playListConvector(playList))
    }

    private fun playListConvectorEntity(playList: PlayList): PlayListEntity {
        return playListConvector.map(playList)
    }

    private fun playListConvector(playlist: List<PlayListEntity>): List<PlayList> {
        return playlist.map { playlist -> playListConvector.map(playlist) }
    }


}