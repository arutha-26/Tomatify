package com.capstone.tomatifyapp.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.tomatifyapp.ui.main.LocalNewsFragment
import com.capstone.tomatifyapp.ui.main.InternationalNewsFragment

class SectionPagerAdapter(activity:AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position){
            0 -> fragment = InternationalNewsFragment()
            1 -> fragment = LocalNewsFragment()
        }
        return fragment as Fragment
    }

}