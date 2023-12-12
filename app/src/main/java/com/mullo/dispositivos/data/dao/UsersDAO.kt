package com.mullo.dispositivos.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mullo.dispositivos.data.entities.Users

@Dao
interface UsersDAO {

    @Query("SELECT *FROM Users")
    fun getAllUsers() : List<Users>

    @Query("SELECT *FROM Users where userId = :userId")
    fun getOneUser(userId : Int) : Users

    @Insert
    fun insertUser(users: List<Users>)

    @Update
    fun updateUsers(users: List<Users>)
}