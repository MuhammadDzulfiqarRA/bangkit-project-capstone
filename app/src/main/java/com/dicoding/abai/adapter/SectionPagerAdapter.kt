package com.dicoding.abai.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.abai.ui.fragment.DisplayDashboardFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String = ""

    override fun createFragment(position: Int): Fragment {
        val fragment = DisplayDashboardFragment()
        fragment.arguments = Bundle().apply {
            putInt(DisplayDashboardFragment.ARG_POSITION, position + 1)
            putString(DisplayDashboardFragment.ARG_USERNAME, username)
        }
        return fragment
    }
    override fun getItemCount(): Int {
        return 4
    }
}
