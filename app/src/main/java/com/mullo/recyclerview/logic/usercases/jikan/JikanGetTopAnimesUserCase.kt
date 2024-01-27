package com.mullo.recyclerview.logic.usercases.jikan

import android.util.Log
import com.mullo.recyclerview.core.Constants
import com.mullo.recyclerview.core.getFullInfoAnimeLG
import com.mullo.recyclerview.data.network.endpoints.TopAnimesEndpoint
import com.mullo.recyclerview.data.network.repository.RetrofitBase
import com.mullo.recyclerview.logic.entities.FullInfoAnimeLG


class JikanGetTopAnimesUserCase {

    suspend fun invoke(): Result<List<FullInfoAnimeLG>> {

        var result: Result<List<FullInfoAnimeLG>>? = null
        val items = ArrayList<FullInfoAnimeLG>()


        try {

            val baseService = RetrofitBase.getRetrofitJikanConnection()
            val service = baseService.create(TopAnimesEndpoint::class.java)
            val call = service.getAllTopAnimes()

            if (call.isSuccessful) {
                val infoAnime = call.body()!!
                infoAnime.data.forEach {
                    items.add(it.getFullInfoAnimeLG())
                }

                result = Result.success(items)


            } else {
                Log.e(Constants.TAG, "Error en el llamado a la API de Jikan")
                result = Result.failure(Exception("Error en el llamado a la API de Jikan"))

            }
        } catch (ex: Exception) {
            Log.e(Constants.TAG, ex.stackTraceToString())
            result = Result.failure(Exception(ex))

        }
        return result!!
    }
}