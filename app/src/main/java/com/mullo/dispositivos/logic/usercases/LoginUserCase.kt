package com.mullo.dispositivos.logic.usercases

import android.content.Context
import android.content.ServiceConnection
import android.util.Log
import com.mullo.dispositivos.data.entities.Users
import com.mullo.dispositivos.data.repositories.DBConnection
import com.mullo.dispositivos.data.repositories.DBRepository
import com.mullo.dispositivos.data.repositories.UserRepository
import com.mullo.dispositivos.ui.core.Constants

class LoginUserCase(val connection: DBRepository) {


    fun checkLogin(username: String, password: String): Int {
        var ret = -1

        val users = UserRepository().getUserList()
        val lstUsers = users.filter {
            it.password == password && it.username == username
        }
        if (lstUsers.isNotEmpty()) {
            ret = lstUsers.first().userId
        }
        return ret
        // return  users.contains(Users(username, password))
    }

    fun getUserName1(userId: Int): Users =
        connection.getUsersDAO().getOneUser(userId)


    fun getUserName(userId: Int): Users =

        UserRepository().getUserList().first {
            it.userId == userId
        }

    fun insertUsers() =
        if (connection.getUsersDAO().getAllUsers().isNotEmpty()) {
            connection.getUsersDAO().insertUser(
                UserRepository().getUserList()
            )
        } else {

        }

}