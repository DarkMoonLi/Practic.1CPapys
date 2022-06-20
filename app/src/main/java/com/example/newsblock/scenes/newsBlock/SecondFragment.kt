package com.example.newsblock.scenes.newsBlock

import NetworkStatus
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsblock.R
import com.example.newsblock.database.FeedReaderDbHelper
import com.example.newsblock.databinding.FragmentSecondBinding
import com.example.newsblock.models.NewX
import com.example.newsblock.models.News
import retrofit2.Response


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var searchView: SearchView
    private var listNews: MutableList<NewX> = mutableListOf()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        val dbHelper = context?.let { FeedReaderDbHelper(it, null) }
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        recyclerView = binding.itemsContainer
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = NewsAdapter(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val cursor = dbHelper?.getSelectedNews(position + 1)

                val bundle = Bundle()

                cursor?.moveToFirst()

                if (cursor?.count != 0) {
                    Log.v("ItemClick", "This is work")
                    bundle.putString(
                        "title", cursor?.getString(
                            cursor.getColumnIndex(
                                dbHelper.COL_NEWS_TITLE
                            )
                        )
                    )

                    bundle.putString(
                        "annotation", cursor?.getString(
                            cursor.getColumnIndex(
                                dbHelper.COL_NEWS_ANNOTATION
                            )
                        )
                    )

                    bundle.putString(
                        "date", cursor?.getString(
                            cursor.getColumnIndex(
                                dbHelper.COL_NEWS_DATE
                            )
                        )
                    )

                    bundle.putString(
                        "type", cursor?.getString(
                            cursor.getColumnIndex(
                                dbHelper.COL_NEWS_TYPE
                            )
                        )
                    )

                    bundle.putString(
                        "img", cursor?.getString(
                            cursor.getColumnIndex(
                                dbHelper.COL_IMG
                            )
                        )
                    )

                    bundle.putString(
                        "mobileUrl", cursor?.getString(
                            cursor.getColumnIndex(
                                dbHelper.COL_NEWS_MOBILE_URL
                            )
                        )
                    )
                }

                findNavController().navigate(R.id.action_SecondFragment_to_news, bundle)
            }
        })
        recyclerView.adapter = adapter

        fun cursorConverter(cursor: Cursor, listNews: MutableList<NewX>): MutableList<NewX> {
            this.listNews = mutableListOf()
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getString(cursor.getColumnIndex(dbHelper?.COL_NEWS_ID))
                    val title =
                        cursor.getString(cursor.getColumnIndex(dbHelper?.COL_NEWS_TITLE))
                    val annotation =
                        cursor.getString(cursor.getColumnIndex(dbHelper?.COL_NEWS_ANNOTATION))
                    val idResource =
                        cursor.getString(cursor.getColumnIndex(dbHelper?.COL_NEWS_ID_RESOURCE))
                    val img = cursor.getString(cursor.getColumnIndex(dbHelper?.COL_IMG))
                    val localImg =
                        cursor.getString(cursor.getColumnIndex(dbHelper?.COL_LOCAL_IMG))
                    val mobileUrl =
                        cursor.getString(cursor.getColumnIndex(dbHelper?.COL_NEWS_MOBILE_URL))
                    val newsDate =
                        cursor.getString(cursor.getColumnIndex(dbHelper?.COL_NEWS_DATE))
                    val newsDateUts =
                        cursor.getString(cursor.getColumnIndex(dbHelper?.COL_NEWS_DATE_UTS))
                    val type =
                        cursor.getString(cursor.getColumnIndex(dbHelper?.COL_NEWS_TYPE))

                    val news = NewX(
                        annotation,
                        id,
                        idResource,
                        img,
                        localImg,
                        mobileUrl,
                        newsDate,
                        newsDateUts,
                        title,
                        type
                    )

                    listNews.add(news)

                } while (cursor.moveToNext())
            }

            return listNews
        }

        val networkStatus = NetworkStatus()
        if (context?.let { networkStatus.isOnline(it) } == true) {
            viewModel.getData()
        } else {
            val cursor = dbHelper?.getNews()

            if (cursor != null && cursor.count > 0) {
                this.listNews = cursorConverter(cursor, listNews)
                adapter.setList(listNews)
                cursor.close();
            } else {
                binding.textviewSecond.text = "Ошибка, нет доступа к сети. Попробуйте позже."
            }
        }

        searchView = binding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val cursor = query?.let { dbHelper?.getSearchNews(it.lowercase()) }
                listNews = mutableListOf()
                if (cursor != null && cursor.count > 0) {
                    listNews = cursorConverter(cursor, listNews)
                    adapter.setList(listNews)
                }
                else
                {
                    adapter.setList(listNews)
                }
                cursor?.close();
                return false
            }

            override fun onQueryTextChange(newString: String?): Boolean {
                val cursor = newString?.let { dbHelper?.getSearchNews(it.lowercase()) }
                if (cursor != null && cursor.count > 0) {
                    listNews = mutableListOf()
                    listNews = cursorConverter(cursor, listNews)
                }
                return false
            }
        })

        fun selectRequest(list: Response<News>) {
            for (news in list.body()?.data?.news!!) {
                val cursor = dbHelper?.getOneNews(news.id)
                if (cursor?.count == 0) {
                    dbHelper.insertItem(news)
                } else {
                    dbHelper?.updateItem(news)
                }
            }
        }

        viewModel.newsList.observe(viewLifecycleOwner) { list ->
            list.body()?.data?.let {
                adapter.setList(it.news)
            }
            selectRequest(list)
        }

        binding.update.setOnClickListener {
            if (context?.let { networkStatus.isOnline(it) } == true) {
                viewModel.getData()

                viewModel.newsList.observe(viewLifecycleOwner) { list ->
                    list.body()?.data?.let {
                        adapter.setList(it.news)
                    }

                    selectRequest(list)
                }
            } else {
                val cursor = dbHelper?.getNews()

                if (cursor != null && cursor.count > 0) {
                    this.listNews = cursorConverter(cursor, listNews)
                    adapter.setList(listNews)
                    cursor.close();
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
