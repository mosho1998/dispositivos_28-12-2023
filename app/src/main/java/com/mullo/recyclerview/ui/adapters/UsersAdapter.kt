package com.mullo.recyclerview.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mullo.recyclerview.R
import com.mullo.recyclerview.data.entities.Users
import com.mullo.recyclerview.databinding.ItemsUsersBinding

class UsersAdapter(
    private val onDeleteItem: (Int) -> Unit,
    private val onSelectItem: (Users) -> Unit

) : RecyclerView.Adapter<UsersAdapter.UsersVH>() {

    var listUsers: List<Users> = listOf()

    class UsersVH(view: View) : RecyclerView.ViewHolder(view) {

        private var binding: ItemsUsersBinding = ItemsUsersBinding.bind(view)
        fun render(
            item: Users,
            onDeleteItem: (Int) -> Unit,
            onSelectItem: (Users) -> Unit
        ) {
            binding.txtUserName.text = item.name
            binding.txtUserDesc.text = item.desc
            binding.imgUser.load(item.img)

            binding.btnBorrar.setOnClickListener {
                onDeleteItem(adapterPosition)
            }

            binding.imgUser.setOnClickListener {
                onSelectItem(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapter.UsersVH {
        val inflater = LayoutInflater.from(parent.context)
        return UsersVH(inflater.inflate(R.layout.items_users, parent, false))
    }

    override fun onBindViewHolder(holder: UsersAdapter.UsersVH, position: Int) {
        holder.render(listUsers[position], onDeleteItem, onSelectItem)
    }

    override fun getItemCount(): Int = listUsers.size

}