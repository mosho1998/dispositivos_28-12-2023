package com.mullo.dispositivos.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mullo.dispositivos.R
import com.mullo.dispositivos.core.My_Application
import com.mullo.dispositivos.data.entities.Users
import com.mullo.dispositivos.databinding.ActivityPrincipalMainBinding
import com.mullo.dispositivos.logic.usercases.LoginUserCase
import com.mullo.dispositivos.ui.adapters.UsersAdapter
import com.mullo.dispositivos.ui.core.Constants
import com.mullo.dispositivos.ui.fragments.FavoritesFragment
import com.mullo.dispositivos.ui.fragments.ListFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Character.getName

class PrincipalMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrincipalMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
        checkDataBase()

        initRecyclerView()
    }

    private fun initRecyclerView() {
        lifecycleScope.launch(Dispatchers.Main) {
            val usrs = withContext(Dispatchers.IO){getUsersList()}
            val adapter: UsersAdapter = UsersAdapter(usrs)
            binding.rvUsers.adapter = adapter
            binding.rvUsers.layoutManager = LinearLayoutManager(this@PrincipalMainActivity,
                LinearLayoutManager.VERTICAL,
                false)

            binding.animationView.visibility = View.GONE
        }
    }

    suspend private fun getUsersList(): List<Users>{
        delay(7000)
        return LoginUserCase(My_Application.getConnectionDB()!!)
            .getAllUsers()
    }

    private fun checkDataBase(){
        lifecycleScope.launch(Dispatchers.Main) {
            val usrs = withContext(Dispatchers.IO) {
                getUsersList()
            }
        }

    }

    private fun initListeners() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val manager = supportFragmentManager

            when (item.itemId) {
                R.id.page_1 -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(binding.frmContainer.id, ListFragment())
                    transaction.commit()
                    true
                }

                R.id.page_2 -> {
                    val transaction = manager.beginTransaction()
                    transaction.replace(binding.frmContainer.id, FavoritesFragment())
                    transaction.commit()
                    true
                }

                else -> {
                    false
                }
            }

        }
    }
}

