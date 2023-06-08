package com.capstone.tomatifyapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.capstone.tomatifyapp.R
import com.capstone.tomatifyapp.adapter.NewsAdapter
import com.capstone.tomatifyapp.ui.auth.viewmodel.NewsViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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
    }
}
