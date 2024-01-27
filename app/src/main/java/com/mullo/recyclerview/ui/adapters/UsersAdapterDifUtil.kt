package com.mullo.recyclerview.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mullo.recyclerview.R
import com.mullo.recyclerview.databinding.ItemsUsersBinding
import com.mullo.recyclerview.logic.entities.FullInfoAnimeLG

class UsersAdapterDifUtil(
    private val onDeleteItem: (Int) -> Unit,
    private val onSelectItem: (FullInfoAnimeLG) -> Unit

) : ListAdapter<FullInfoAnimeLG, UsersAdapterDifUtil.UsersVH>(DiffUtilUserCallback) {

    class UsersVH(view: View) : RecyclerView.ViewHolder(view) {

        private var binding: ItemsUsersBinding = ItemsUsersBinding.bind(view)
        fun render(
            item: FullInfoAnimeLG,
            onDeleteItem: (Int) -> Unit,
            onSelectItem: (FullInfoAnimeLG) -> Unit
        ) {
            binding.txtUserName.text = item.name
            binding.txtUserDesc.text = item.synapsis
            binding.imgUser.load(item.big_image)

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

private object DiffUtilUserCallback : DiffUtil.ItemCallback<FullInfoAnimeLG>() {
    override fun areItemsTheSame(oldItem: FullInfoAnimeLG, newItem: FullInfoAnimeLG): Boolean {
        return (oldItem.id == newItem.id)
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: FullInfoAnimeLG, newItem: FullInfoAnimeLG): Boolean {
        return (oldItem == newItem)
    }

}
