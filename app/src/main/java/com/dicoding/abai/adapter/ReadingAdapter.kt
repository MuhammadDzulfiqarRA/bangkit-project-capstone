package com.dicoding.abai.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.abai.R
import com.dicoding.abai.databinding.ItemReadingBinding
import com.dicoding.abai.databinding.ItemRvBinding
import com.dicoding.abai.helper.ConstantsObject
import com.dicoding.abai.response.DataItemStory
import com.dicoding.abai.ui.activity.DetailStoryActivity

class ReadingAdapter : ListAdapter<DataItemStory, ReadingAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(val binding: ItemReadingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(storyData: DataItemStory) {
            binding.tvReadingDesc.text = storyData.story
            Glide.with(binding.imgReading)
                . load("http://192.168.0.100:3000/api/v1/thumbnails/display/${storyData.thumbnail}")
                .placeholder(R.drawable.ic_baseline_broken_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
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
    }


        companion object {
            val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemStory>() {
                override fun areItemsTheSame(oldItem: DataItemStory, newItem: DataItemStory): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: DataItemStory, newItem: DataItemStory): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }