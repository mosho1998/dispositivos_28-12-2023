package com.mullo.dispositivos.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mullo.dispositivos.R
import com.mullo.dispositivos.data.entities.Users
import com.mullo.dispositivos.databinding.UsersItemsBinding

class UsersAdapter(private val listUsers: List<Users>) : RecyclerView.Adapter<UsersAdapter.UsersVH>() {
    class UsersVH(view: View) : RecyclerView.ViewHolder(view) {

        private var binding: UsersItemsBinding = UsersItemsBinding.bind(view)
        fun render(item: Users) {
            binding.avatarImg.load("https://c0.klipartz.com/pngpicture/340/946/gratis-png-avatar-usuario-computadora-iconos-desarrollador-de-software-avatar-thumbnail.png"){
                placeholder(R.drawable.avatar)
                crossfade(true)
            }
            binding.txtID.text = item.userId.toString()
            binding.txtName.text = item.firstName.toString() + " " + item.lastName.toString()


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapter.UsersVH {
        val inflater = LayoutInflater.from(parent.context)
        return UsersVH(inflater.inflate(R.layout.users_items, parent, false))
    }

    override fun onBindViewHolder(holder: UsersAdapter.UsersVH, position: Int) {
       holder.render(listUsers[position])
    }

    override fun getItemCount(): Int = listUsers.size

}