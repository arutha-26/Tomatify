package com.capstone.tomatifyapp.ui.main

import android.annotation.SuppressLint
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
import com.capstone.tomatifyapp.model.NewsItem
import com.capstone.tomatifyapp.ui.auth.viewmodel.NewsViewModel

class LocalNewsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter

    private val newsViewModel: NewsViewModel by activityViewModels()
    private var listUser = ArrayList<NewsItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rvNews)

        setupRecyclerView()
        newsViewModel.getLocalNews().observe(viewLifecycleOwner){ listNews ->
            observeNewsData(listNews)
        }

    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeNewsData(listNews: List<NewsItem>) {
        val adapter = NewsAdapter(listNews)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}

