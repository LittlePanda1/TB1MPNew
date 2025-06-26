package com.example.crudadmin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudadmin.databinding.ItemUserBinding

class UserAdapter(
    private val onItemClick: (UserData) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val userList = mutableListOf<UserData>()

    fun submitList(data: List<UserData>) {
        userList.clear()
        userList.addAll(data)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserData) {
            binding.itemName.text = item.name
            binding.itemStore.text = item.storeName
            binding.itemLocation.text = item.location

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size
}