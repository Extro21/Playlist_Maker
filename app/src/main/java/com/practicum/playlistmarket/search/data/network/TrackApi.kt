package com.practicum.playlistmarket.search.data.network

import com.practicum.playlistmarket.search.data.dto.TrackSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackApi {


    @GET("/search?entity=song ")
    suspend fun search(@Query("term") text: String): TrackSearchResponse


}