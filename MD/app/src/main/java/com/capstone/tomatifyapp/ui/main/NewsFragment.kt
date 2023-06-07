//package com.capstone.tomatifyapp.ui.main
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.lifecycleScope
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.capstone.tomatifyapp.adapter.NewsAdapter
//import com.capstone.tomatifyapp.api.ApiConfig
//import com.capstone.tomatifyapp.databinding.FragmentNewsBinding
//import com.capstone.tomatifyapp.helper.ViewModelFactory
//import com.capstone.tomatifyapp.viewmodel.NewsViewModel
//import kotlinx.coroutines.launch
//
//class NewsFragment : Fragment() {
//    private lateinit var binding: FragmentNewsBinding
//    private val newsViewModel: NewsViewModel by viewModels {
//        ViewModelFactory.getInstance(requireActivity().application)
//    }
//    private lateinit var adapter: NewsAdapter
//    private var position = 0
//    private var title = ""
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentNewsBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        adapter = NewsAdapter()
////        { newsModel -> onClickNewsItem(newsModel) }
//        binding.rvNews.layoutManager = LinearLayoutManager(requireActivity())
//        binding.rvNews.adapter = adapter
//        arguments?.let {
//            position = it.getInt(ARG_POSITION)
//            title = it.getString(ARG_USERNAME).toString()
//        }
//
//        lifecycleScope.launch {
//            if (position == 1) {
//                newsViewModel.getNational(ApiConfig.NEWS_URL)
//                newsViewModel.national.observe(viewLifecycleOwner) { national ->
//                    adapter.submitList(national)
//                }
//            } else {
//                newsViewModel.getInternational()
//                newsViewModel.international.observe(viewLifecycleOwner) { international ->
//                    adapter.submitList(international)
//                }
//            }
//        }
//
//        newsViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
//            showLoading(isLoading)
//        }
//    }
//
//    private fun showLoading(isLoading: Boolean) {
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }
//
//
//
//    companion object {
//        const val ARG_POSITION = "position"
//        const val ARG_USERNAME = "username"
//    }
//}
