package com.mullo.dispositivos.core

import android.app.Application
import com.mullo.dispositivos.data.repositories.DBConnection
import com.mullo.dispositivos.data.repositories.DBRepository
import com.mullo.dispositivos.logic.usercases.LoginUserCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class My_Application : Application() {


    override fun onCreate() {
        super.onCreate()
        con = DBConnection().getConnection(applicationContext)

        GlobalScope.launch(Dispatchers.IO) {

            LoginUserCase(con).insertUsers()
        }

    }


    companion object {
        private lateinit var con: DBRepository

        fun getConnectionDB(): DBRepository? {
            return con
        }
    }


}