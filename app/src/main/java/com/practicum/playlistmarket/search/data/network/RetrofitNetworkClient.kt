package com.practicum.playlistmarket.search.data.network


import com.practicum.playlistmarket.search.data.NetworkClient
import com.practicum.playlistmarket.search.data.dto.Response
import com.practicum.playlistmarket.search.data.dto.TrackSearchRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitNetworkClient : NetworkClient {
    private val iTunesBaseURL = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(iTunesBaseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    override suspend fun doRequest(dto: Any): Response {
        return try {
            val iTunesService = retrofit.create(TrackApi::class.java)
            if (dto is TrackSearchRequest) {
                val resp = iTunesService.search(dto.expression)
                resp.apply { resultCode = 200 }
            } else {
                Response().apply { resultCode = 500 }
            }
        } catch (e: Exception) {
            Response().apply { resultCode = 500 }
        }

    }


}



