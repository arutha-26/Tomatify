package com.capstone.tomatifyapp.ui.main

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.capstone.tomatifyapp.adapter.SectionPagerAdapter
import com.capstone.tomatifyapp.api.ApiConfig
import com.capstone.tomatifyapp.databinding.ActivityHomeBinding
import com.capstone.tomatifyapp.helper.ViewModelFactory
import com.capstone.tomatifyapp.viewmodel.NewsViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val newsViewModel by viewModels<NewsViewModel> {
        ViewModelFactory.getInstance(application)
    }



    private fun hideActionBar() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra(EXTRA_TITLE)

        if (title != null) {
            showLoading(true)
            lifecycleScope.launch {
                newsViewModel.getNational(ApiConfig.NEWS_URL + title)
            }
            newsViewModel.international.observe(this) {
                with(binding) {
//                    tvNameUser.text =
//                        showLoading(false).toString()
                }
            }

        }

        hideActionBar()
        setUpViewPager(title)
    }



    private fun setUpViewPager(username: String?) {
        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.title = username.toString()
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            if (position == 0) {
                tab.text = "National"
            } else {
                tab.text = "International"
            }
        }.attach()
    }

    companion object {
        const val EXTRA_TITLE = "extra_username"
    }
}
