package com.dicoding.abai.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListenerAdapter
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.abai.R
import com.dicoding.abai.adapter.ReadingAdapter
import com.dicoding.abai.adapter.StoryAdapter
import com.dicoding.abai.databinding.ActivityReadingBinding
import com.dicoding.abai.response.ItemsItem
import com.dicoding.abai.viewmodel.ReadingViewModel

class ReadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReadingBinding

    private val readingViewModel by viewModels<ReadingViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvReading.layoutManager = layoutManager



        readingViewModel.user.observe(this) {
            setUsersData(it)

        }
    }

    private fun setUsersData(users: List<ItemsItem?>?) {
        val adapter = ReadingAdapter()
        adapter.submitList(users)
        binding.rvReading.adapter = adapter
    }
}