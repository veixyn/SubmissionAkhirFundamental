package com.dicoding.submissionawal.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissionawal.data.response.ItemsItem
import com.dicoding.submissionawal.databinding.ItemUserBinding
import com.dicoding.submissionawal.ui.UserAdapter.Companion.DIFF_CALLBACK

class UserFollowAdapter : ListAdapter<ItemsItem, UserFollowAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            Glide.with(binding.root)
                .load(user.avatarUrl)
                .into(binding.imgUserPhoto)
            binding.tvUserName.text = user.login
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val follows = getItem(position)
        holder.bind(follows)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
}