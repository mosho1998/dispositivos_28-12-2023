package com.mullo.dispositivos.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.mullo.dispositivos.R
import com.mullo.dispositivos.core.My_Application
import com.mullo.dispositivos.databinding.ActivityPrincipalMainBinding
import com.mullo.dispositivos.logic.usercases.LoginUserCase
import com.mullo.dispositivos.ui.core.Constants
import com.mullo.dispositivos.ui.fragments.FavoritesFragment
import com.mullo.dispositivos.ui.fragments.ListFragment

class PrincipalMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrincipalMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listFragment = ListFragment()
        val favoritesFragment = FavoritesFragment()




        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.page_1 -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(binding.frmContainer.id, listFragment)
                    transaction.commit()
                    true
                }
                R.id.page_2 -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(binding.frmContainer.id, favoritesFragment)
                    transaction.commit()
                    true
                }
                else -> false
            }
        }


        /*
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
        }*/
    }
}