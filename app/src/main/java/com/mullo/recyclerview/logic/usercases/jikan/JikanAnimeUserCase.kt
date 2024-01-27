package com.mullo.recyclerview.logic

import android.util.Log
import com.mullo.recyclerview.core.Constants
import com.mullo.recyclerview.core.getFullInfoAnimeLG
import com.mullo.recyclerview.data.network.endpoints.AnimeEndPoint
import com.mullo.recyclerview.data.network.repository.RetrofitBase
import com.mullo.recyclerview.logic.entities.FullInfoAnimeLG


class JikanAnimeUserCase {

    fun getResponse(nameAnime: Int): FullInfoAnimeLG {

        var infoAnime = FullInfoAnimeLG()

        try{

        val baseService = RetrofitBase.getRetrofitJikanConnection()
        val service = baseService.create(AnimeEndPoint::class.java)
        val call = service.getAnimeFullInfo(nameAnime)

        if(call.isSuccessful){

            val a = call.body()!!
           infoAnime =  a.getFullInfoAnimeLG()

        }else{
            Log.d(Constants.TAG, "Error en el llamado a la API de Jikan")
        }
    }
        catch(ex: Exception){
            Log.e(Constants.TAG, ex.stackTraceToString())
        }
        return infoAnime
    }


}