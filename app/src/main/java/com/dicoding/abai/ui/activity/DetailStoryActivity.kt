package com.dicoding.abai.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.dicoding.abai.R
import com.dicoding.abai.databinding.ActivityDetailStoryBinding
import com.dicoding.abai.helper.ConstantsObject
import com.dicoding.abai.response.ItemsItem

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val storyData = intent.getParcelableExtra<ItemsItem>(ConstantsObject.DASHBOARD_TO_DETAIL)
        if (storyData != null) {
            binding.tvStoryTitle.text = storyData.login
            binding.tvStoryDesc.text = storyData.login
            Glide.with(this)
                .load(storyData.avatarUrl)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(binding.imgDetailStory)
        } else {
            Toast.makeText(this, getString(R.string.data_invalid), Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnLetsReading.setOnClickListener {
            startActivity(Intent(this@DetailStoryActivity, ReadingActivity::class.java))
        }

    }
}