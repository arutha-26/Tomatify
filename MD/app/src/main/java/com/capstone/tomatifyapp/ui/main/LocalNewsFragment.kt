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
        val view = inflater.inflate(R.layout.fragment_news_international, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rvNews)

        setupRecyclerView()
        val dummyTitles = listOf(
            "Melihat 'Pasukan Tandur' dari Bandung",
            "Strategi Wilmar Padi Bantu Produktivitas Petani Sumsel",
            "Lebih Produktif, Petani Sumsel Antusias Jadi Mitra Wilmar Padi",
            "Wilmar Padi Targetkan Program Kemitraan Petani Naik Jadi 20 Ribu Ha",
            "Lahan Sawah Terancam Kering, Petani KBB Disarankan Tanam Palawija",
            "Pertumbuhan Ekonomi Bali di Bawah Nasional, Golkar: Pertanian Perlu Perhatian",
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
            "Bandung - Kamis (15/6/2023), tepat pukul 06.00 WIB, emak-emak di Desa Bojongemas, Kecamatan Solokanjeruk, Kabupaten Bandung, turun ke persawahan untuk menanam padi.",
            "Bandung - Kamis (15/6/2023), tepat pukul 06.00 WIB, emak-emak di Desa Bojongemas, Kecamatan Solokanjeruk, Kabupaten Bandung, turun ke persawahan untuk menanam padi.",
            "Bandung - Kamis (15/6/2023), tepat pukul 06.00 WIB, emak-emak di Desa Bojongemas, Kecamatan Solokanjeruk, Kabupaten Bandung, turun ke persawahan untuk menanam padi.",
            "Bandung - Kamis (15/6/2023), tepat pukul 06.00 WIB, emak-emak di Desa Bojongemas, Kecamatan Solokanjeruk, Kabupaten Bandung, turun ke persawahan untuk menanam padi.",
            "Bandung - Kamis (15/6/2023), tepat pukul 06.00 WIB, emak-emak di Desa Bojongemas, Kecamatan Solokanjeruk, Kabupaten Bandung, turun ke persawahan untuk menanam padi.",
            "Bandung - Kamis (15/6/2023), tepat pukul 06.00 WIB, emak-emak di Desa Bojongemas, Kecamatan Solokanjeruk, Kabupaten Bandung, turun ke persawahan untuk menanam padi.",
        )

        val dummyCategories = listOf(
            "detikJabar",
            "detikFinance",
            "detikJabar",
            "detikFinance",
            "detikJabar",
            "detikFinance",
        )

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

        newsViewModel.getInternationalNews().observe(viewLifecycleOwner) { listNews ->
            observeNewsData(listNews)
        }

//        newsViewModel.getLocalNews().observe(viewLifecycleOwner){ listNews ->
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

