package com.example.newsblock

import java.util.*

data class News(
    var success: String? = null,
    var data: () -> Unit = {
        var count: Int? = null
        var news: () -> Unit = {
            var id: Int? = null
            var type: Byte? = null
            var title: String? = null
            var img: String? = null
            var newsDate: String? = null
            var newsDateUts: String? = null
            var annotation: String? = null
            var mobileUrl: String? = null
        }
    }
)
