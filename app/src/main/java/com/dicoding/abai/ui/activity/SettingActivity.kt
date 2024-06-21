package com.dicoding.abai.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.lifecycleScope
import com.dicoding.abai.database.UserDao
import com.dicoding.abai.databinding.ActivitySettingBinding
import com.dicoding.abai.viewmodel.LoginViewModel
import com.dicoding.abai.viewmodel.SettingViewModel
import com.dicoding.abai.viewmodel.ViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var mAuth: FirebaseAuth

    private lateinit var userDao: UserDao

    private val settingViewModel by viewModels<SettingViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val action = supportActionBar
//        action!!.title = "Pengaturan"

        mAuth = com.google.firebase.ktx.Firebase.auth

        settingViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        binding.userAccountLine.setOnClickListener {
            startActivity(Intent(this@SettingActivity, AccountActivity::class.java))
        }

        binding.achievementLine.setOnClickListener {
            startActivity(Intent(this@SettingActivity, AchievementActivity::class.java))
        }

        binding.logoutLine.setOnClickListener{
            signOut()
        }
    }


    private fun signOut() {
        lifecycleScope.launch {
            val credentialManager = CredentialManager.create(this@SettingActivity)
            mAuth.signOut()
            settingViewModel.logout()
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
            startActivity(Intent(this@SettingActivity, LoginActivity::class.java))
            finish()
        }
    }

}