package com.dicoding.abai.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.abai.R
import com.dicoding.abai.adapter.MissionAdapter
import com.dicoding.abai.adapter.ReadingAdapter
import com.dicoding.abai.databinding.ActivityAchievmentBinding
import com.dicoding.abai.response.DataItemStory
import com.dicoding.abai.response.MissionItem
import com.dicoding.abai.viewmodel.AchievementViewModel
import com.dicoding.abai.viewmodel.ReadingViewModel

class AchievementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAchievmentBinding
    private val achievementViewModel by viewModels<AchievementViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAchievmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvMissionItem.layoutManager = layoutManager

//        val adapter = MissionAdapter()
//        binding.rvMissionItem.adapter = adapter

        achievementViewModel.getMissionData(1)

        achievementViewModel.mission.observe(this) {
           setMissionData(it)
        }
    }

    private fun setMissionData(missionData: List<MissionItem?>?) {
        val adapter = MissionAdapter()
        binding.rvMissionItem.adapter = adapter
        adapter.submitList(missionData)

    }
}