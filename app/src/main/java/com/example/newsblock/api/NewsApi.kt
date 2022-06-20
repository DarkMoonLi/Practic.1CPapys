package com.example.newsblock.api

import com.example.newsblock.models.NewX
import com.example.newsblock.models.News
import retrofit2.Call
import retrofit2.Response

class NewsApi{
    suspend fun getNews(): Response<News>
    {
        return Retrofit.api.getData()
    }
}