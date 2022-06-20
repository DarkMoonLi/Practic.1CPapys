package com.example.newsblock.models

data class Data(
    val Here: String,
    val count: String,
    val error_msg: String,
    var news: MutableList<NewX>
)