package com.dicoding.abai.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.lifecycleScope
import com.dicoding.abai.databinding.ActivitySettingBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import kotlinx.coroutines.launch

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val action = supportActionBar
//        action!!.title = "Pengaturan"

        mAuth = com.google.firebase.ktx.Firebase.auth

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
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
            startActivity(Intent(this@SettingActivity, LoginActivity::class.java))
            finish()
        }
    }
}