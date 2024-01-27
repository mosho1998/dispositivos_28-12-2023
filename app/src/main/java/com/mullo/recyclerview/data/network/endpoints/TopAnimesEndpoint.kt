package com.mullo.recyclerview.data.network.endpoints


import com.mullo.dispositivos.data.network.entities.jikan.top.TopAnimes
import retrofit2.Response
import retrofit2.http.GET

interface TopAnimesEndpoint {
    @GET("top/anime")
    suspend fun getAllTopAnimes(): Response<TopAnimes>
}