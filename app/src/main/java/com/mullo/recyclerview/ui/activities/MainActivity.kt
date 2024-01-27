package com.mullo.recyclerview.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mullo.recyclerview.logic.usercases.jikan.JikanGetTopAnimesUserCase
import com.mullo.recyclerview.databinding.ActivityMainBinding
import com.mullo.recyclerview.logic.entities.FullInfoAnimeLG
import com.mullo.recyclerview.ui.adapters.UsersAdapterDifUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


}