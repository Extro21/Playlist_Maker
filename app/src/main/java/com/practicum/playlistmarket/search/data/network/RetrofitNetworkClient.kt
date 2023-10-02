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


//    override suspend fun doRequest(dto: Any): Response {
//        val iTunesService = retrofit.create(TrackApi::class.java)
//        if (isConnected() == false) {
//            return Response().apply { resultCode = -1 }
//        }
//
//        if (dto !is TrackSearchRequest) {
//            return Response().apply { resultCode = 400 }
//        }
//
//        return withContext(Dispatchers.IO) {
//            try {
//                val response = iTunesService.search(dto.expression)
//                response.apply { resultCode = 200 }
//            } catch (e: Throwable) {
//                Response().apply { resultCode = 500 }
//            }
//        }
//
//    }

    override suspend fun doRequest(dto: Any): Response {
        try {
            val iTunesService = retrofit.create(TrackApi::class.java)
            if (dto is TrackSearchRequest) {
                val resp = iTunesService.search(dto.expression)
                return resp.apply{resultCode= 200}
            }
            else {
                return Response().apply{resultCode=500}
            }
        } catch (e : Exception){
            return Response().apply{resultCode=500}
        }

    }

//    override fun doRequest(dto: Any): Response {
//        try {
//            val iTunesService = retrofit.create(TrackApi::class.java)
//            if (dto is TrackSearchRequest) {
//                val resp = iTunesService.search(dto.expression).execute()
//                val body = resp.body()?: Response()
//                return body.apply{resultCode=resp.code()}
//            }
//            else {
//                return Response().apply{resultCode=400}
//            }
//        } catch (e : Exception){
//            return Response().apply{resultCode=400}
//        }
//
//    }

}



