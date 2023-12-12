package com.mullo.dispositivos.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.mullo.dispositivos.core.My_Application
import com.mullo.dispositivos.databinding.ActivityPrincipalMainBinding
import com.mullo.dispositivos.logic.usercases.LoginUserCase
import com.mullo.dispositivos.ui.core.Constants

class PrincipalMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrincipalMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras.let {

            My_Application
                .getConnectionDB()!!
                .getUsersDAO()
                .getOneUser(1)

            val userId = it?.getInt(Constants.USER_ID)
            if (userId != null) {
                val user = LoginUserCase(My_Application.getConnectionDB()!!)
                    .getUserName(userId)
                binding.txtUserName.text = user.firstName.toString()
            } else {
                Snackbar.make(binding.txtUserName, "Ocurrio un error", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}