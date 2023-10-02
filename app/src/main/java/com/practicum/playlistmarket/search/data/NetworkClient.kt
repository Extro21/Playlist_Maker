package com.practicum.playlistmarket.search.data

import com.practicum.playlistmarket.search.data.dto.Response


interface NetworkClient {
 //   fun doRequest (dto:Any) : Response
   suspend fun doRequest (dto:Any) : Response

  //  suspend fun doRequestSuspend(dto: Any): Response

}