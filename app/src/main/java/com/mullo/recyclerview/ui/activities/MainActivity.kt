package com.mullo.recyclerview.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mullo.recyclerview.data.entities.Users
import com.mullo.recyclerview.databinding.ActivityMainBinding
import com.mullo.recyclerview.ui.adapters.UsersAdapter
import com.mullo.recyclerview.ui.adapters.UsersAdapterDifUtil

class MainActivity : AppCompatActivity() {

    private var usersList: MutableList<Users> = ArrayList<Users>()
    private var count: Int = 1

    private lateinit var binding: ActivityMainBinding
    private var usersAdapter = UsersAdapter({ deleteUsers(it) }, { selectUser(it) })
    private var userDiffAdapter = UsersAdapterDifUtil({ deleteUsersDiff(it) }, { selectUser(it) })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initListeners()
    }

    private fun initRecyclerView() {
        binding.rvUsers.adapter = userDiffAdapter
        binding.rvUsers.layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
    }

    private fun initListeners() {
        binding.btnInsert.setOnClickListener {
            val us = Users(
                1, "Bryan $count", "Estudiante",
                "https://previews.123rf.com/images/alexpokusay/alexpokusay1609/alexpokusay160900028/62223260-alumno-en-la-escuela-aprendizaje-en-la-escuela-ilustración-de-dibujos-animados-de-vectores-de.jpg"
            )
            count++
            insertUsersDiff(us)

        }
    }


    private fun insertUsersDiff(us: Users) {
        usersList.add(us)
        userDiffAdapter.submitList(usersList.toList())

    }

    private fun deleteUsersDiff(position: Int) {
        usersList.removeAt(position)
        userDiffAdapter.submitList(usersList.toList())

    }





    private fun insertUsers(us: Users) {
        usersList.add(us)

        usersAdapter.listUsers = usersList
        usersAdapter.notifyDataSetChanged()
        usersAdapter.notifyItemInserted(usersList.size)
    }

    private fun deleteUsers(position: Int) {
        usersList.removeAt(position)
        usersAdapter.listUsers = usersList
        usersAdapter.notifyItemRemoved(position)
    }

    private fun selectUser(user: Users) {
        Snackbar
            .make(this, binding.btnInsert, user.name, Snackbar.LENGTH_LONG)
            .show()

        /*cambiar a otro activity
        val i = Intent(this, llegada)
        i.putExtra("usuarioID", user.id)
        startActivity(i)*/
    }
}