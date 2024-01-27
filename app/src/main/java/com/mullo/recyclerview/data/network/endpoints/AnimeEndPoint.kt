package com.mullo.recyclerview.data.network.endpoints

import com.mullo.recyclerview.data.network.entities.jikan.FullInfoAnime
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeEndPoint {

    @GET("anime/{id}/full")
    fun getAnimeFullInfo(@Path("id") name: Int): Response<FullInfoAnime>
}