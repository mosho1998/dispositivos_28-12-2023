package com.mullo.recyclerview.core


import com.mullo.dispositivos.data.network.entities.jikan.top.Data
import com.mullo.recyclerview.data.network.entities.jikan.FullInfoAnime
import com.mullo.recyclerview.logic.entities.FullInfoAnimeLG

fun FullInfoAnime.getFullInfoAnimeLG() = FullInfoAnimeLG(

    this.data.mal_id,
    this.data.title_english,
    this.data.images.jpg.small_image_url,
    this.data.images.jpg.large_image_url,
    this.data.synopsis
)


fun Data.getFullInfoAnimeLG() = FullInfoAnimeLG(

    this.mal_id,
    this.title_english,
    this.images.jpg.small_image_url,
    this.images.jpg.large_image_url,
    this.synopsis
)