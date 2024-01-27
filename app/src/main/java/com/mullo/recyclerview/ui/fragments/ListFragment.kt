package com.mullo.recyclerview.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mullo.recyclerview.R
import com.mullo.recyclerview.databinding.FragmentDetailBinding
import com.mullo.recyclerview.databinding.FragmentListBinding
import com.mullo.recyclerview.logic.entities.FullInfoAnimeLG
import com.mullo.recyclerview.logic.usercases.jikan.JikanGetTopAnimesUserCase
import com.mullo.recyclerview.ui.adapters.UsersAdapterDifUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    private var usersList: MutableList<FullInfoAnimeLG> = ArrayList()
    private var userDiffAdapter = UsersAdapterDifUtil({ deleteUsersDiff(it) }, { selectAnime(it) })
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentListBinding.bind(inflater.inflate(R.layout.fragment_list, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvUsers.adapter = userDiffAdapter
        binding.rvUsers.layoutManager =
            LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
            )
        loadDataRecyclerView()
    }


    private fun loadDataRecyclerView() {

        lifecycleScope.launch(Dispatchers.Main) {
            binding.progresBar.visibility = View.VISIBLE

            //Llamado a una base de datos debe ser siempre desde una IO
            val resp = withContext(Dispatchers.IO) {
                JikanGetTopAnimesUserCase().invoke()
            }

            resp.onSuccess { listAnime ->
                usersList.addAll(listAnime)
                insertUsersDiff(usersList)
            }
            resp.onFailure { ex ->
                Snackbar.make(
                    requireActivity(),
                    binding.rvUsers,
                    ex.message.toString(),
                    Snackbar.LENGTH_LONG
                )
                    .show()
            }
            binding.progresBar.visibility = View.GONE

        }

    }


    private fun insertUsersDiff(animes: List<FullInfoAnimeLG>) {
        usersList.addAll(animes)
        userDiffAdapter.submitList(usersList.toList())

    }

    private fun deleteUsersDiff(position: Int) {
        usersList.removeAt(position)
        userDiffAdapter.submitList(usersList.toList())

    }

    private fun selectAnime(anime: FullInfoAnimeLG) {
        findNavController()
            .navigate(
                ListFragmentDirections
                    .actionListFragmentToDetailFragment(name = anime.name, idAnime = anime.id)

            )


        /* Snackbar
             .make(requireActivity(), binding.rvUsers, anime.name, Snackbar.LENGTH_LONG)
             .show()*/

        /*cambiar a otro activity
        val i = Intent(this, llegada)
        i.putExtra("usuarioID", user.id)
        startActivity(i)*/
    }


}