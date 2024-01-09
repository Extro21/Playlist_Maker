package com.practicum.playlistmarket.media.data.repository

import android.content.Context
import android.content.Intent
import android.util.Log
import com.practicum.playlistmarket.media.data.db.AppDataBase
import com.practicum.playlistmarket.media.data.db.PlayListConvector
import com.practicum.playlistmarket.media.data.db.entity.PlayListEntity
import com.practicum.playlistmarket.media.data.db.entity.TrackPlayListEntity
import com.practicum.playlistmarket.media.data.db.entity.TrackPlaylist
import com.practicum.playlistmarket.media.domain.module.PlayList
import com.practicum.playlistmarket.media.domain.repository.PlayListRepository
import com.practicum.playlistmarket.player.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Locale

class PlayListRepositoryImpl(
    private val database: AppDataBase, private val playListConvector: PlayListConvector, private val context: Context
) :
    PlayListRepository {


    override suspend fun deletePlaylist(playlistId: Int): Boolean {
        val tracks = trackConvectorTracks(database.playListDao().getTrackByPlaylist(playlistId))
        database.playListDao().deletePlayListJoinTable(playlistId)
        database.playListDao().deletePlayList(playlistId)
        Log.d("deletePlaylist", "deletePlaylist")
        for (track in tracks) {
            if (!database.playListDao().doesTrackPlayList(track.trackId)) {
                database.playListDao().deleteTrackPlayList(track.trackId)
            }
        }
        return true
    }

    override suspend fun deleteTrackPlaylist(trackId: String, playlistId: Int): Boolean {
        if (!database.playListDao().doesTrackExistPlayList(trackId, playlistId)) {
            database.playListDao().deleteTrackPlayList(trackId)
        }
        database.playListDao().deleteTrackPlayListJoinTable(trackId, playlistId)
        return true
    }

    override suspend fun addTrackPlaylist(track: Track, playList: PlayList): Boolean {
        if (!database.playListDao().doesTrackExist(trackId = track.trackId)) {
            database.playListDao().addTrack(trackConvectorEntity(track))
        }
        return if (!database.playListDao()
            .doesTrackExistPlayList(trackId = track.trackId, playList.playListId)
            ) {
            database.playListDao()
                .addTrackForPlaylist(
                    TrackPlaylist(
                        trackId = track.trackId,
                        playListId = playList.playListId
                    )
                )
            Log.d("addTrackPlaylist", "true ")
            true
        } else {
            Log.d("addTrackPlaylist", "false")
            false
        }
    }


    override suspend fun getTracksForPlaylist(playListId: Int): Flow<List<Track>> = flow {
        val tracks = database.playListDao().getTrackByPlaylist(playListId)
        Log.e("getTrack", "${trackConvectorTracks(tracks)}")
        emit(trackConvectorTracks(tracks))
    }

    override suspend fun getTracksForPlaylistCount(playList: PlayList): Int {
        return database.playListDao().getTrackByPlaylist(playList.playListId).size
    }


    private fun trackConvectorEntity(track: Track): TrackPlayListEntity {
        return playListConvector.map(track)
    }

    private fun trackConvectorTracks(tracks: List<TrackPlayListEntity>): List<Track> {
        return tracks.map { tracks -> playListConvector.map(tracks) }
    }

    override suspend fun addPlayList(name: String, description: String, uri: String) {
        val playlist = PlayList(name = name, description = description, uri = uri, playListId = 0)

        database.playListDao().insertPlayList(playListConvectorEntity(playlist))
    }

    override suspend fun updatePlaylist(name: String, description: String, uri: String, id: Int) {
        val playlist = PlayList(name = name, description = description, uri = uri, playListId = id)
        database.playListDao().updatePlaylist(playListConvectorEntity(playlist))
    }

     override suspend fun getPlayList(id: Int): PlayList {
        val playList = database.playListDao().getPlayList(id)
        return playListConvector(playList)
    }

    override fun getPlaylist(): Flow<List<PlayList>> = flow {
        val playList = playListConvector(database.playListDao().getPlayLists())
        playList.map {
            it.quantityTracks = getTracksForPlaylistCount(it)
        }
        emit(playList)
    }

    private fun playListConvectorEntity(playList: PlayList): PlayListEntity {
        return playListConvector.map(playList)
    }

    private fun playListConvector(playList: PlayListEntity): PlayList {
        return playListConvector.map(playList)
    }

    private fun playListConvector(playlist: List<PlayListEntity>): List<PlayList> {
        return playlist.map { playlist -> playListConvector.map(playlist) }
    }


    override fun sharePlaylist(tracks : List<Track>, nameTrack : String, description: String, quantityTracks : String) {
        var listTrack = ""
        var count = 1
        for (track in tracks) {
            val timeTrack =
                SimpleDateFormat(
                    "mm:ss",
                    Locale.getDefault()
                ).format(track.trackTimeMillis?.toInt())
            listTrack += "$count. ${track.artistName} - ${track.trackName} (${timeTrack})\n"
            count++
        }
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT, "$nameTrack\n" +
                        "$description\n $quantityTracks:\n" +
                        listTrack
            )
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            Intent.createChooser(this, null)
        }
        context.startActivity(intent, null)
    }


}