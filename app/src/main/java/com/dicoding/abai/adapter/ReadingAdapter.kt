package com.dicoding.abai.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.abai.databinding.ItemReadingBinding
import com.dicoding.abai.databinding.ItemRvBinding
import com.dicoding.abai.helper.ConstantsObject
import com.dicoding.abai.response.ItemsItem
import com.dicoding.abai.ui.activity.DetailStoryActivity

class ReadingAdapter : ListAdapter<ItemsItem, ReadingAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(val binding: ItemReadingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(users: ItemsItem) {
            binding.tvReadingDesc.text = "${users.login}"
            Glide.with(binding.imgReading)
                .load("${users.avatarUrl}")
                .into(binding.imgReading)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemReadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val users = getItem(position)
        holder.bind(users)
//        holder.itemView.setOnClickListener {
//            val intent = Intent(holder.itemView.context, DetailActivity::class.java).apply {
//                putExtra(DetailActivity.EXTRA_LOGIN, "${users.login}")
//            }
//            holder.itemView.context.startActivity(intent)
//        }
    }

        companion object {
            val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
                override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }