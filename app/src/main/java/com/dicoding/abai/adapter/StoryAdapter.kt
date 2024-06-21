package com.dicoding.abai.adapter

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.abai.R
import com.dicoding.abai.adapter.StoryAdapter.MyViewHolder.Companion.DIFF_CALLBACK
import com.dicoding.abai.databinding.ItemRvBinding
import com.dicoding.abai.helper.ConstantsObject
import com.dicoding.abai.response.DataItem
import com.dicoding.abai.ui.activity.DetailStoryActivity

class StoryAdapter : ListAdapter<DataItem, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var onItemClickListener: ((DataItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (DataItem) -> Unit) {
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
            intent.putExtra(ConstantsObject.DASHBOARD_TO_DETAIL, dataItem)
            holder.itemView.context.startActivity(intent)
        }
    }

    class MyViewHolder(val binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bindItem: DataItem) {
            binding.apply {
                tvItemTitleName.text = bindItem.title
                tvItemDescription.text = bindItem.overview
                Log.d(TAG, "STORY THUMBNAIL: ${bindItem.thumbnail} ")
                Glide.with(itemView)
                    .load(bindItem.thumbnail)
                    .placeholder(R.drawable.ic_baseline_broken_image_24)
                    .error(R.drawable.ic_baseline_broken_image_24)
                    .into(imgCoverStory)
            }
        }
        companion object {
            val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
                override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }

}