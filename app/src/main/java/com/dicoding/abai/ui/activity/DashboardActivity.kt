package com.dicoding.abai.ui.activity

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dicoding.abai.R
import com.dicoding.abai.adapter.SectionsPagerAdapter
import com.dicoding.abai.databinding.ActivityDashboardBinding
import com.dicoding.abai.viewmodel.DashboardViewModel
import com.dicoding.abai.viewmodel.ViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDashboardBinding
//    private val dashboardViewModel: DashboardViewModel by viewModels()


    private val dashboardViewModel by viewModels<DashboardViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth

    private lateinit var levelName: String
    private lateinit var userName: String
    private var currenUserId = 0
    private var userMissionCompleted: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("title")
        var sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username ?: ""
        binding.viewPager.adapter = sectionsPagerAdapter


        dashboardViewModel.getSession().observe(this) {session ->
            userName = session.username
            Log.d("DASHBOARD ACTIVITY", " Session Username = ${session.username}")

            dashboardViewModel.getUserIdByUsername(userName)
        }

        dashboardViewModel.userId.observe(this) { userId ->
            // Handle userId yang diperoleh di sini
            Log.d("DASHBOARD ACTIVITY", "UserId diperoleh: $userId")
            if (userId != null) {
                currenUserId = userId
            }

            if (userId != null) {
                dashboardViewModel.getUserMissionCompleted(userId)
            }
        }

        dashboardViewModel.userMissionCompleted.observe(this) {mission ->
            userMissionCompleted = mission
            Log.d("DASHBOARD ACTIVITY", "UserMissionCompleted diperoleh: ${userMissionCompleted} ")
            updateRankUI(mission)
        }

        dashboardViewModel.errorMessage.observe(this) {
            Toast.makeText(
                this@DashboardActivity,
                "username null",
                Toast.LENGTH_SHORT
            ).show()
        }


        mAuth = com.google.firebase.ktx.Firebase.auth
        val firebaseUser = mAuth.currentUser

        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            val userName = firebaseUser.displayName
            binding.textViewUsername.text = "${userName}"
            if (firebaseUser.photoUrl != null) {
                Glide.with(binding.imgUserProfile)
                    .load(firebaseUser.photoUrl)
                    .into(binding.imgUserProfile)
            } else {
                Glide.with(binding.imgUserProfile)
                    .load(R.drawable.user_icon)
                    .into(binding.imgUserProfile)
            }

        }


        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.setText(searchView.text)
                searchView.hide()
                // Memanggil searchUser di DashboardViewModel
                dashboardViewModel.searchUser(searchView.text.toString())
                Toast.makeText(this@DashboardActivity, searchView.text, Toast.LENGTH_SHORT).show()
                false
            }
        }

        binding.imgUserProfile.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, SettingActivity::class.java))
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Semua"
                1 -> "Rekomendasi"
                2 -> "Histori"
                3 -> "Tema"
                else -> ""
            }
        }.attach()

    }

    private fun  updateRankUI (missionClaimed: Int) {
        when (missionClaimed) {
            0 -> {
                binding.textViewLevelUser.text = getString(R.string.level_name_0)
                binding.imgUserRank.setImageResource(R.drawable.ic_unranked)
            }
            1 -> {
                binding.textViewLevelUser.text = getString(R.string.level_name_0)
                binding.imgUserRank.setImageResource(R.drawable.ic_unranked)
            }
            2 -> {
                binding.textViewLevelUser.text = getString(R.string.level_name_1)
                binding.imgUserRank.setImageResource(R.drawable.ic_rank_silver)
            }
            3 -> {
                binding.textViewLevelUser.text = getString(R.string.level_name_1)
                binding.imgUserRank.setImageResource(R.drawable.ic_rank_silver)
            }
            4 -> {
                binding.textViewLevelUser.text = getString(R.string.level_name_2)
                binding.imgUserRank.setImageResource(R.drawable.ic_rank_gold)
            }
            5 -> {
                binding.textViewLevelUser.text = getString(R.string.level_name_2)
                binding.imgUserRank.setImageResource(R.drawable.ic_rank_gold)
            }
            6 -> {
                binding.textViewLevelUser.text = getString(R.string.level_name_3)
                binding.imgUserRank.setImageResource(R.drawable.ic_rank_diamond)
            }
        }
    }

}