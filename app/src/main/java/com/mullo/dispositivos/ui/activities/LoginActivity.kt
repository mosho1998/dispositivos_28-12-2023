package com.mullo.dispositivos.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.mullo.dispositivos.core.My_Application
import com.mullo.dispositivos.databinding.ActivityLoginBinding
import com.mullo.dispositivos.logic.usercases.LoginUserCase
import com.mullo.dispositivos.logic.usercases.SingIn
import com.mullo.dispositivos.ui.core.Constants

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val singIn: SingIn = SingIn()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
    }

    private fun initListeners() {
        binding.buttonLogin.setOnClickListener() {
            val check = LoginUserCase(My_Application.getConnectionDB()!!).checkLogin(
                binding.txtUsuario.text.toString(),
                binding.txtPassword.text.toString()
            )
            if (check > 0) {
                val intent = Intent(this, PrincipalMainActivity::class.java)
                intent.putExtra(Constants.USER_ID ,check)
                startActivity(intent)
            } else {
                Snackbar.make(
                    binding.txtUsuario,
                    "Nombre de usuario o contraseña incorrecta",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
}