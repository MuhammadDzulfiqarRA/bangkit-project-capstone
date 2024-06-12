package com.dicoding.abai.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.dicoding.abai.R
import com.dicoding.abai.databinding.ActivityAccountBinding
import com.dicoding.abai.databinding.ActivitySettingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

class AccountActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mAuth = com.google.firebase.ktx.Firebase.auth
        val firebaseUser = mAuth.currentUser

        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            binding.tvAccountUsername.text = firebaseUser.displayName
            binding.tvAccountEmail.text = firebaseUser.email
            if (firebaseUser.photoUrl != null) {
                Glide.with(binding.imgUserAccount)
                    .load(firebaseUser.photoUrl)
                    .into(binding.imgUserAccount)
            } else {
                Glide.with(binding.imgUserAccount)
                    .load(R.drawable.user_icon)
                    .into(binding.imgUserAccount)
            }
        }
    }
}