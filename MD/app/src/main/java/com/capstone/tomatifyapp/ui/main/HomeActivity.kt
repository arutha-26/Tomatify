package com.capstone.tomatifyapp.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.capstone.tomatifyapp.R
import com.capstone.tomatifyapp.adapter.NewsAdapter
import com.capstone.tomatifyapp.adapter.SectionPagerAdapter
import com.capstone.tomatifyapp.ui.auth.viewmodel.NewsViewModel
import com.capstone.tomatifyapp.ui.predict.PredictActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        hideActionBar()
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        val sectionPagerAdapter = SectionPagerAdapter(this)

        viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
           tab.text = resources.getString(tab_title[position])
        }.attach()


        val fabPredict = findViewById<FloatingActionButton>(R.id.fabPredict)
        fabPredict.setOnClickListener {
            val intent = Intent(this, PredictActivity::class.java)
            startActivity(intent)
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.miSettings -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }R.id.miHome -> {
                // Tindakan yang ingin dilakukan saat item miHome diklik
                true
            }
                else -> true
            }
        }

        onResume()
    }
    private fun hideActionBar() {
        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    companion object{
        @StringRes
        private val tab_title = intArrayOf(R.string.international ,R.string.national)

    }

}
