package com.mullo.recyclerview.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mullo.recyclerview.R
import com.mullo.recyclerview.data.entities.Users
import com.mullo.recyclerview.databinding.ItemsUsersBinding

class UsersAdapterDifUtil(
    private val onDeleteItem: (Int) -> Unit,
    private val onSelectItem: (Users) -> Unit

) : ListAdapter<Users, UsersAdapterDifUtil.UsersVH>(DiffUtilUserCallback) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapterDifUtil.UsersVH {
        val inflater = LayoutInflater.from(parent.context)
        return UsersVH(inflater.inflate(R.layout.items_users, parent, false))
    }

    override fun onBindViewHolder(holder: UsersAdapterDifUtil.UsersVH, position: Int) {
        holder.render(getItem(position), onDeleteItem, onSelectItem)
    }

}

private object DiffUtilUserCallback : DiffUtil.ItemCallback<Users>() {
    override fun areItemsTheSame(oldItem: Users, newItem: Users): Boolean {
        return (oldItem.id == newItem.id)
    }

    override fun areContentsTheSame(oldItem: Users, newItem: Users): Boolean {
        return (oldItem == newItem)
    }

}
