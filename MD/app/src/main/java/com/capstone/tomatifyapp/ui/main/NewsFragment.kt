package com.capstone.tomatifyapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.tomatifyapp.R
import com.capstone.tomatifyapp.adapter.NewsAdapter
import com.capstone.tomatifyapp.ui.auth.viewmodel.NewsViewModel

class NewsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter

    private val newsViewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        recyclerView = view.findViewById(R.id.rvNews)
        setupRecyclerView()
        observeNewsData()
        return view
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = newsAdapter
    }

    private fun observeNewsData() {
        newsViewModel.getLocalNews().observe(viewLifecycleOwner) { newsList ->
            newsAdapter.setNewsItems(newsList)
        }
    }
}

