package com.mullo.dispositivos.data.repositories

import com.mullo.dispositivos.data.entities.Users

class UserRepository {

    fun getUserList(): List<Users> {
        return listOf<Users>(
            Users(
                "bryan", "bryan", 1,
                "Bryan", "Mullo"
            ),
            Users("juan", "juan", 2),
            Users("marco", "marco", 3),
            Users("maria", "maria", 4)


        )
    }
}