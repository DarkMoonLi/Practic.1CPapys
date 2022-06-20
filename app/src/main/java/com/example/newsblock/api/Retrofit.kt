package com.example.newsblock.api

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit : Application() {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://ws-tszh-1c-test.vdgb-soft.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: QuestApi by lazy{
        retrofit.create(QuestApi::class.java)
    }
}