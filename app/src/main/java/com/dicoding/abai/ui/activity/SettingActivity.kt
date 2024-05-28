package com.dicoding.abai.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.abai.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val action = supportActionBar
//        action!!.title = "Pengaturan"

    }
}