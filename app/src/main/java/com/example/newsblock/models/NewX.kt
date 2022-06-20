package com.example.newsblock.models

import com.google.gson.annotations.SerializedName

data class NewX(
    val annotation: String,
    val id: String,
    val id_resource: String,
    val img: String,
    val local_img: String,
    val mobile_url: String,
    val news_date: String,
    val news_date_uts: String,
    val title: String,
    val type: String
)