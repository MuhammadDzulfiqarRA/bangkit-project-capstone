package com.dicoding.abai.ui.activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.abai.R
import com.dicoding.abai.adapter.MissionAdapter
import com.dicoding.abai.adapter.ReadingAdapter
import com.dicoding.abai.database.MissionClaim
import com.dicoding.abai.database.UserHistory
import com.dicoding.abai.databinding.ActivityAchievmentBinding
import com.dicoding.abai.response.DataItemStory
import com.dicoding.abai.response.MissionItem
import com.dicoding.abai.viewmodel.AchievementViewModel
import com.dicoding.abai.viewmodel.LoginViewModel
import com.dicoding.abai.viewmodel.ReadingViewModel
import com.dicoding.abai.viewmodel.ViewModelFactory
import kotlin.math.log

class AchievementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAchievmentBinding
//    private val achievementViewModel by viewModels<AchievementViewModel>()

    private val achievementViewModel by viewModels<AchievementViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var userName: String
    private var currenUserId = 0
    private var userStoryCount: Int = 0
    private var userMissionCompleted: Int = 0
    private var missionClaimed : List<MissionClaim> = emptyList()

    private lateinit var levelName: String  // Declare levelName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAchievmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButtonView(currenUserId)

        achievementViewModel.getSession().observe(this) { session ->
            userName = session.username
            Log.d(TAG, "onCreate Achievement :  username = $userName")

            achievementViewModel.getUserIdByUsername(userName)
        }

        achievementViewModel.userId.observe(this) { userId ->
            // Handle userId yang diperoleh di sini
            Log.d(TAG, "UserId diperoleh: $userId")
            currenUserId = userId
            // Lakukan operasi sesuai kebutuhan dengan userId ini
            achievementViewModel.getUserRank(userId)
            achievementViewModel.getUserStoryCount(userId)
            achievementViewModel.getUserMissionCompleted(userId)
            achievementViewModel.getMissionClaimByUserId(userId)
        }

        achievementViewModel.userMissionCompleted.observe(this) {
            userMissionCompleted = it
            Log.d(TAG, "UserMissionCompleted diperoleh: ${userMissionCompleted} ")
            updateRankUI(it)
        }

        achievementViewModel.userMissionClaims.observe(this) {
            missionClaimed = it
            Log.d(TAG, "UserMissionClaimed diperoleh: ${missionClaimed} ")
            setupButtonView(achievementViewModel.userId.value ?: return@observe)
        }

        setupButtonListeners()



        achievementViewModel.userRank.observe(this) {userRank ->
            Log.d(TAG, "UserRank diperoleh: $userRank")
            if (userRank == 0) {
                binding.imgRanking.setImageResource(R.drawable.ic_unranked)
                binding.imgCurrentRank.setImageResource(R.drawable.ic_unranked)
                binding.imgNextRank.setImageResource(R.drawable.ic_rank_silver)
                binding.rankProgressBar.progress = 100
            } else if (userRank == 1) {
                binding.imgRanking.setImageResource(R.drawable.ic_rank_silver)
                binding.imgCurrentRank.setImageResource(R.drawable.ic_rank_silver)
                binding.imgNextRank.setImageResource(R.drawable.ic_rank_gold)

            } else {
                binding.imgRanking.setImageResource(R.drawable.ic_rank_gold)
                binding.imgCurrentRank.setImageResource(R.drawable.ic_rank_gold)
                binding.imgNextRank.setImageResource(R.drawable.ic_rank_diamond)
            }
        }

        achievementViewModel.userStoryCount.observe(this) {count ->

            userStoryCount = count
//            userStoryCount = 49
            Log.d(TAG, "UserStoryCount diperoleh: $userStoryCount")

            if (userStoryCount < 5) {
                binding.missionProgressBar1.progress = userStoryCount * 20
            } else if (userStoryCount == 5) {
                setMissionProgressBar1()
            } else if (userStoryCount in 6..9) {
                setMissionProgressBar1()
                val storyCount = userStoryCount % 5
                binding.missionProgressBar2.progress = storyCount * 20
            } else if (userStoryCount == 10) {
                setMissionProgressBar1()
                setMissionProgressBar2()
            } else if (userStoryCount in 11..14) {
                setMissionProgressBar1()
                setMissionProgressBar2()
                val storyCount = userStoryCount % 10
                binding.missionProgressBar3.progress = storyCount * 20
            } else if (userStoryCount == 15) {
                setMissionProgressBar1()
                setMissionProgressBar2()
                setMissionProgressBar3()
            } else if (userStoryCount in 16..24) {
                setMissionProgressBar1()
                setMissionProgressBar2()
                setMissionProgressBar3()
                val storyCount = userStoryCount % 15
                binding.missionProgressBar4.progress = storyCount * 10
            } else if (userStoryCount == 25) {
                setMissionProgressBar1()
                setMissionProgressBar2()
                setMissionProgressBar3()
                setMissionProgressBar4()
            } else if (userStoryCount in 26..34) {
                setMissionProgressBar1()
                setMissionProgressBar2()
                setMissionProgressBar3()
                setMissionProgressBar4()
                val storyCount = userStoryCount % 25
                binding.missionProgressBar5.progress = storyCount * 10
            } else if (userStoryCount == 35) {
                setMissionProgressBar1()
                setMissionProgressBar2()
                setMissionProgressBar3()
                setMissionProgressBar4()
                setMissionProgressBar5()
            } else if (userStoryCount in 36..44) {
                setMissionProgressBar1()
                setMissionProgressBar2()
                setMissionProgressBar3()
                setMissionProgressBar4()
                setMissionProgressBar5()
                val storyCount = userStoryCount % 35
                binding.missionProgressBar6.progress = storyCount * 10
            } else {
                setMissionProgressBar1()
                setMissionProgressBar2()
                setMissionProgressBar3()
                setMissionProgressBar4()
                setMissionProgressBar5()
                setMissionProgressBar6()
            }
        }

    }

    private fun setupButtonView(userId: Int) {
        for (missionId in 1..6) {
            val userMissionClaimed = missionClaimed.find { it.userId == userId && it.idMissionClaimed == missionId }
            Log.d(TAG, "setupButtonView: ${userId} , ${missionId}")
            when (missionId) {
                1 -> {
                    if (userMissionClaimed != null) {
                        // Mission 1 is claimed
                        Log.d(TAG, "setupButtonView: userMissionClaimed ${userMissionClaimed}")
                        binding.btnClaim1.isEnabled = false
                        binding.btnClaim1.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
                    }
                }
                2 -> {
                    if (userMissionClaimed != null) {
                        // Mission 2 is claimed
                        binding.btnClaim2.isEnabled = false
                        binding.btnClaim2.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
                    }
                }
                3 -> {
                    if (userMissionClaimed != null) {
                        // Mission 3 is claimed
                        binding.btnClaim3.isEnabled = false
                        binding.btnClaim3.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
                    }
                }
                4 -> {
                    if (userMissionClaimed != null) {
                        // Mission 4 is claimed
                        binding.btnClaim4.isEnabled = false
                        binding.btnClaim4.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
                    }
                }
                5 -> {
                    if (userMissionClaimed != null) {
                        // Mission 5 is claimed
                        binding.btnClaim5.isEnabled = false
                        binding.btnClaim5.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
                    }
                }
                6 -> {
                    if (userMissionClaimed != null) {
                        // Mission 6 is claimed
                        binding.btnClaim6.isEnabled = false
                        binding.btnClaim6.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
                    }
                }
            }
        }
    }

    private fun updateRankUI(missionClaimed: Int) {
        when (missionClaimed) {
            0 -> {
                binding.imgRanking.setImageResource(R.drawable.ic_unranked)
                binding.imgCurrentRank.setImageResource(R.drawable.ic_unranked)
                binding.imgNextRank.setImageResource(R.drawable.ic_rank_silver)
                binding.rankProgressBar.progress = 0
                binding.tvLevelName.text = getString(R.string.level_name_0).toUpperCase()
                levelName = getString(R.string.level_name_0)
            }
            1 -> {
                binding.imgRanking.setImageResource(R.drawable.ic_unranked)
                binding.imgCurrentRank.setImageResource(R.drawable.ic_unranked)
                binding.imgNextRank.setImageResource(R.drawable.ic_rank_silver)
                binding.rankProgressBar.progress = missionClaimed * 50
                binding.tvLevelName.text = getString(R.string.level_name_0).toUpperCase()
                levelName = getString(R.string.level_name_0)
            }
            2 -> {
                binding.imgRanking.setImageResource(R.drawable.ic_rank_silver)
                binding.imgCurrentRank.setImageResource(R.drawable.ic_rank_silver)
                binding.imgNextRank.setImageResource(R.drawable.ic_rank_gold)
                binding.rankProgressBar.progress = (missionClaimed % 2) * 50
                binding.tvLevelName.text = getString(R.string.level_name_1).toUpperCase()
                levelName = getString(R.string.level_name_1)
            }
            3 -> {
                binding.imgRanking.setImageResource(R.drawable.ic_rank_silver)
                binding.imgCurrentRank.setImageResource(R.drawable.ic_rank_silver)
                binding.imgNextRank.setImageResource(R.drawable.ic_rank_gold)
                binding.rankProgressBar.progress = (missionClaimed % 2) * 50
                binding.tvLevelName.text = getString(R.string.level_name_1).toUpperCase()
                levelName = getString(R.string.level_name_1)
            }
            4 -> {
                binding.imgRanking.setImageResource(R.drawable.ic_rank_gold)
                binding.imgCurrentRank.setImageResource(R.drawable.ic_rank_gold)
                binding.imgNextRank.setImageResource(R.drawable.ic_rank_diamond)
                binding.rankProgressBar.progress = (missionClaimed % 4) * 50
                binding.tvLevelName.text = getString(R.string.level_name_2).toUpperCase()
                levelName= getString(R.string.level_name_2)
            }
            5 -> {
                binding.imgRanking.setImageResource(R.drawable.ic_rank_gold)
                binding.imgCurrentRank.setImageResource(R.drawable.ic_rank_gold)
                binding.imgNextRank.setImageResource(R.drawable.ic_rank_diamond)
                binding.rankProgressBar.progress = (missionClaimed % 4) * 50
                binding.tvLevelName.text = getString(R.string.level_name_2).toUpperCase()
                levelName = getString(R.string.level_name_2)
            }
            6 -> {
                binding.imgRanking.setImageResource(R.drawable.ic_rank_diamond)
                binding.imgCurrentRank.setImageResource(R.drawable.ic_rank_diamond)
                binding.imgNextRank.setImageResource(R.drawable.ic_rank_diamond)
                binding.rankProgressBar.progress = 100
                binding.tvLevelName.text = getString(R.string.level_name_3).toUpperCase()
                levelName = getString(R.string.level_name_3)
            }
        }
//        // Store levelName in SharedPreferences
//        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.putString("levelName", levelName)
//        editor.apply()
    }


    private fun setupButtonListeners() {
        binding.btnClaim1.setOnClickListener {
            val userId = achievementViewModel.userId.value ?: return@setOnClickListener
            handleMissionClaim(userId, 1)
            binding.btnClaim1.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        }

        binding.btnClaim2.setOnClickListener {
            val userId = achievementViewModel.userId.value ?: return@setOnClickListener
            handleMissionClaim(userId, 2)
            binding.btnClaim2.isEnabled = false
            binding.btnClaim2.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        }

        binding.btnClaim3.setOnClickListener {
            val userId = achievementViewModel.userId.value ?: return@setOnClickListener
            handleMissionClaim(userId, 3)
            binding.btnClaim3.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        }

        binding.btnClaim4.setOnClickListener {
            val userId = achievementViewModel.userId.value ?: return@setOnClickListener
            handleMissionClaim(userId, 4)
            binding.btnClaim4.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        }

        binding.btnClaim5.setOnClickListener {
            val userId = achievementViewModel.userId.value ?: return@setOnClickListener
            handleMissionClaim(userId, 5)
            binding.btnClaim5.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        }

        binding.btnClaim6.setOnClickListener {
            val userId = achievementViewModel.userId.value ?: return@setOnClickListener
            handleMissionClaim(userId, 6)
            binding.btnClaim6.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        }
    }

    private fun handleMissionClaim(userId: Int, missionId: Int) {
        achievementViewModel.incrementMissionCompleted(userId)
        val missionClaim = MissionClaim(userId = userId, idMissionClaimed = missionId)
        achievementViewModel.insertMissionClaim(missionClaim)

        userMissionCompleted += 1
        updateRankUI(userMissionCompleted)
    }


    private fun setMissionProgressBar1() {
        binding.missionProgressBar1.visibility = View.GONE
        binding.btnClaim1.visibility = View.VISIBLE
    }

    private fun setMissionProgressBar2() {
        binding.missionProgressBar2.visibility = View.GONE
        binding.btnClaim2.visibility = View.VISIBLE
    }

    private fun setMissionProgressBar3() {
        binding.missionProgressBar3.visibility = View.GONE
        binding.btnClaim3.visibility = View.VISIBLE
    }

    private fun setMissionProgressBar4() {
        binding.missionProgressBar4.visibility = View.GONE
        binding.btnClaim4.visibility = View.VISIBLE
    }

    private fun setMissionProgressBar5() {
        binding.missionProgressBar5.visibility = View.GONE
        binding.btnClaim5.visibility = View.VISIBLE
    }

    private fun setMissionProgressBar6() {
        binding.missionProgressBar6.visibility = View.GONE
        binding.btnClaim6.visibility = View.VISIBLE
    }

    companion object {
        var LEVEL_NAME: String? = null
    }

}