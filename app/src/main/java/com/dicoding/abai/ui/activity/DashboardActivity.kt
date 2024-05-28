package com.dicoding.abai.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.abai.R
import com.dicoding.abai.adapter.SectionsPagerAdapter
import com.dicoding.abai.databinding.ActivityDashboardBinding
import com.dicoding.abai.viewmodel.DashboardViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDashboardBinding
    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("title")
        var sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username ?: ""
        binding.viewPager.adapter = sectionsPagerAdapter

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

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Semua"
                1 -> "Rekomendasi"
                2 -> "Sudah Dibaca"
                3 -> "Tema"
                else -> ""
            }
        }.attach()

        binding.toolbar.setOnMenuItemClickListener {  menuItem ->
            when (menuItem.itemId) {
//                R.id.user_profile_dashboard -> {
//                    val intent = Intent(this, ProfileActivity::class.java)
//                    startActivity(intent)
//                    true
//                }
                R.id.setting_dashboard -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}