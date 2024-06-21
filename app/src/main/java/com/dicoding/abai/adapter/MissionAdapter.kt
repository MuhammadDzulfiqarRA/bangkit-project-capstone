package com.dicoding.abai.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.abai.R
import com.dicoding.abai.adapter.StoryAdapter.MyViewHolder.Companion.DIFF_CALLBACK
import com.dicoding.abai.databinding.ItemMissionBinding
import com.dicoding.abai.response.MissionItem

class MissionAdapter (private val userStoryCount: Int) : ListAdapter<MissionItem, MissionAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(val binding: ItemMissionBinding, private val userStoryCount: Int) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bindItem: MissionItem) {
            binding.apply {
                tvMissionName.text = bindItem.title
                imgMission.setImageResource(R.drawable.ic_mission)

                missionProgressBar.progress = userStoryCount
                if (userStoryCount >= 100) {
                    missionProgressBar.visibility = View.GONE
                    btnClaim.visibility = View.VISIBLE
                } else {
                    missionProgressBar.visibility = View.VISIBLE
                    btnClaim.visibility = View.GONE
                }

                btnClaim.setOnClickListener {
                    // Handle claim button click
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMissionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, userStoryCount)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataItem = getItem(position)
        holder.bind(dataItem)
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MissionItem>() {
            override fun areItemsTheSame(oldItem: MissionItem, newItem: MissionItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MissionItem, newItem: MissionItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}