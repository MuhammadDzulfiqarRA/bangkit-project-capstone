package com.dicoding.abai.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.abai.adapter.StoryAdapter.MyViewHolder.Companion.DIFF_CALLBACK
import com.dicoding.abai.databinding.ItemRvBinding
import com.dicoding.abai.response.ItemsItem
import com.dicoding.abai.ui.activity.DetailStoryActivity

class StoryAdapter : ListAdapter<ItemsItem, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var onItemClickListener: ((ItemsItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ItemsItem) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataItem = getItem(position)
        holder.bind(dataItem)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailStoryActivity::class.java)
            intent.putExtra("title", dataItem.login)
            holder.itemView.context.startActivity(intent)
        }
    }

    class MyViewHolder(val binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bindItem: ItemsItem) {
            binding.apply {
                tvItemTitleName.text = bindItem.login
                tvItemDescription.text = bindItem.login
                Glide.with(itemView)
                    .load(bindItem.avatarUrl)
                    .into(imgCoverStory)
            }
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

}