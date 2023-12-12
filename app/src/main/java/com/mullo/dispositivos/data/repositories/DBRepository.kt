package com.mullo.dispositivos.data.repositories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mullo.dispositivos.data.dao.UsersDAO
import com.mullo.dispositivos.data.entities.Users


@Database(
    entities = [Users::class],
    version = 1
)
abstract class DBRepository : RoomDatabase() {

    abstract fun getUsersDAO(): UsersDAO
}

//CLASE PARA LA CONEXION
class DBConnection() {
    fun getConnection(context: Context): DBRepository =
        Room.databaseBuilder(
            context,
            DBRepository::class.java,
            "DBTest"
        ).build()

}


