package com.mullo.dispositivos.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity
data class Users(

    var username: String? = null,
    var password: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var userId: Int = -1
    var firstName: String = "No registrado"
    var lastName: String = "No registrado"
    var profile: String = ""

    constructor(username: String?, password: String?, userId: Int)
            : this(username, password) {
        this.userId = userId
    }

    constructor(
        id: Int, username: String?,
        password: String?, profile: String
    ) : this(

        username,
        password
    ) {
        this.profile = profile
    }

    constructor(
        username: String?,
        password: String?, userId: Int,
        firstName: String,
        lastName: String
    ) : this(

        username,
        password
    ) {
        this.userId = userId
        this.firstName = firstName
        this.lastName
    }
}