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

class InternationalNewsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter

    private val newsViewModel: NewsViewModel by activityViewModels()
    private var listUser = ArrayList<NewsItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_international, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rvNews)

        setupRecyclerView()
        val dummyTitles = listOf(
            "GM tomatoes are coming back, but this time they’re purple, and packed with new health benefits",
            "Brief: Japanese company brings ‘world’s first genome-edited tomato’ to market",
            "Brief: Infarm tops up Series C to \$270m; moves into chilis, mushrooms & tomatoes",
            "Tencent, others plow \$15m+ into ag automator FJ Dynamics",
            "No more rotten tomatoes: Improving agricultural logistics through technology",
            "MetoMotion raises \$1.5m seed round for zany greenhouse robot that spots ripeness in tomatoes",
        )

        val dummyUrls = listOf(
            "https://www.detik.com/jabar/foto/d-6774456/melihat-pasukan-tandur-dari-bandung",
            "https://finance.detik.com/berita-ekonomi-bisnis/d-6774210/strategi-wilmar-padi-bantu-produktivitas-petani-sumsel",
            "https://finance.detik.com/berita-ekonomi-bisnis/d-6773846/lebih-produktif-petani-sumsel-antusias-jadi-mitra-wilmar-padi",
            "https://www.detik.com/jabar/foto/d-6774456/melihat-pasukan-tandur-dari-bandung",
            "https://finance.detik.com/berita-ekonomi-bisnis/d-6774210/strategi-wilmar-padi-bantu-produktivitas-petani-sumsel",
            "https://finance.detik.com/berita-ekonomi-bisnis/d-6773846/lebih-produktif-petani-sumsel-antusias-jadi-mitra-wilmar-padi",
        )

        val dummyImages = listOf(
            "https://akcdn.detik.net.id/community/media/visual/2023/06/15/pasukan-tandur-dari-bandung_43.jpeg?w=250&q=",
            "https://akcdn.detik.net.id/community/media/visual/2023/06/15/pasukan-tandur-dari-bandung_43.jpeg%3Fw%3D250%26q%3D",  "https://akcdn.detik.net.id/community/media/visual/2023/06/15/pasukan-tandur-dari-bandung_43.jpeg%3Fw%3D250%26q%3D",  "https://akcdn.detik.net.id/community/media/visual/2023/06/15/pasukan-tandur-dari-bandung_43.jpeg%3Fw%3D250%26q%3D",  "https://akcdn.detik.net.id/community/media/visual/2023/06/15/pasukan-tandur-dari-bandung_43.jpeg%3Fw%3D250%26q%3D",  "https://akcdn.detik.net.id/community/media/visual/2023/06/15/pasukan-tandur-dari-bandung_43.jpeg%3Fw%3D250%26q%3D",  "https://akcdn.detik.net.id/community/media/visual/2023/06/15/pasukan-tandur-dari-bandung_43.jpeg%3Fw%3D250%26q%3D",

            )

        val dummyExcerpts = listOf(
            "GMOs still have an image problem when it comes to food. But attitudes are evolving, insists one startup on a mission to change hearts and minds, one purple tomato at a time.",
            "The Japanese government has affirmed its “determination” that Sanatech Seed’s new tomato will be regulated as a GE, rather than a GMO, product",
            "Infarm CEO Erez Galonska declined to comment on rumors that the startup has retained perennial IPO underwriter Goldman Sachs to help it raise more funds",
            "The Shenzhen-based startup – which produces autonomous rice transplanters, among other things – is also backed by drone company DJI and automaker Dongfeng",
            "GMOs still have an image problem when it comes to food. But attitudes are evolving, insists one startup on a mission to change hearts and minds, one purple tomato at a time.",
            "The Japanese government has affirmed its “determination” that Sanatech Seed’s new tomato will be regulated as a GE, rather than a GMO, product",
            "Infarm CEO Erez Galonska declined to comment on rumors that the startup has retained perennial IPO underwriter Goldman Sachs to help it raise more funds",
            "The Shenzhen-based startup – which produces autonomous rice transplanters, among other things – is also backed by drone company DJI and automaker Dongfeng"
        )

        val dummyCategories = listOf(
            "detikJabar",
            "detikFinance",
            "detikJabar",
            "detikFinance",
            "detikJabar",
            "detikFinance",
        )

        // Tambahkan data dummy ke listUser menggunakan loop for
        for (i in dummyTitles.indices) {
            listUser.add(
                NewsItem(
                    title = dummyTitles[i],
                    url = dummyUrls[i],
                    image = dummyImages[i],
                    date = "2023-06-16",
                    excerpt = dummyExcerpts[i],
                    category = dummyCategories[i]
                )
            )
        }

        observeNewsData(listUser)

        newsViewModel.getLocalNews().observe(viewLifecycleOwner) { listNews ->
            observeNewsData(listNews)
        }

//        newsViewModel.getInternationalNews().observe(viewLifecycleOwner){ listNews ->
//            observeNewsData(listNews)
//        }

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

