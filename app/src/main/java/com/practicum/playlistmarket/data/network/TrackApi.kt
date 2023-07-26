package com.practicum.playlistmarket.data.network

import com.practicum.playlistmarket.data.dto.SongSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackApi {


    @GET("/search?entity=song")
    fun search(
        @Query("term") text: String
    ): Call<SongSearchResponse>

}