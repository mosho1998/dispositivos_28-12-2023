package com.mullo.dispositivos.data.network.entities.jikan.top

data class TopAnimes(
    val `data`: List<Data> = listOf(),
    val pagination: Pagination? = null
)