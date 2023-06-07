package com.capstone.tomatifyapp.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.tomatifyapp.ui.main.NewsFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var title: String =""

    override fun createFragment(position: Int): Fragment {
        return NewsFragment().apply {
            arguments = Bundle().apply {
                putInt(NewsFragment.ARG_POSITION, position + 1)
                putString(NewsFragment.ARG_USERNAME, title)
            }
        }
    }

    override fun getItemCount(): Int = 2
}