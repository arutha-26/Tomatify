package com.capstone.tomatifyapp.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.capstone.tomatifyapp.R
import com.capstone.tomatifyapp.adapter.NewsAdapter
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

        // Mendapatkan data berita lokal
        newsViewModel.getLocalNews().observe(this, Observer { newsItems ->
            // Mengupdate UI dengan data berita lokal
            // Contoh: Menggunakan adapter untuk ViewPager2
            val newsAdapter = NewsAdapter()
            viewPager.adapter = newsAdapter

            // Lampirkan TabLayoutMediator setelah adapter ditetapkan
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                // Konfigurasi tab sesuai dengan posisi
                if (position == 0) {
                    tab.text = "Local News"
                } else {
                    tab.text = "International News"
                }
            }.attach()
        })

        // Mendapatkan data berita internasional
        newsViewModel.getInternationalNews().observe(this, Observer { newsItems ->
            // Mengupdate UI dengan data berita internasional
            // Contoh: Menggunakan adapter untuk ViewPager2
            val newsAdapter = NewsAdapter()
            viewPager.adapter = newsAdapter

            // Lampirkan TabLayoutMediator setelah adapter ditetapkan
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                // Konfigurasi tab sesuai dengan posisi
                if (position == 0) {
                    tab.text = "Local News"
                } else {
                    tab.text = "International News"
                }
            }.attach()
        })

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
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }

    }
    private fun hideActionBar() {
        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

}
