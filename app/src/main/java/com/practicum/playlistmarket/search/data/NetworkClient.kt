package com.practicum.playlistmarket.search.data

import com.practicum.playlistmarket.search.data.dto.Response


interface NetworkClient {
    suspend fun doRequest(dto: Any): Response


}