package com.example.newsblock.api

import com.example.newsblock.models.NewX
import com.example.newsblock.models.News
import retrofit2.Call;
import retrofit2.Response
import retrofit2.http.GET;

interface QuestApi {
        @GET("/api/mobile/news/list")
        suspend fun getData(): Response<News>
}