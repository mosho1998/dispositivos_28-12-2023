package com.mullo.dispositivos.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mullo.dispositivos.data.entities.Users

@Dao
interface UsersDAO {

    @Query("SELECT *FROM Users")
    suspend fun getAllUsers() : List<Users>

    @Query("SELECT *FROM Users where userId = :userId")
    suspend fun getOneUser(userId : Int) : Users

    @Insert
    suspend fun insertUser(users: List<Users>)

    @Update
    suspend fun updateUsers(users: List<Users>)
}