package com.example.newsblock.database

import android.provider.BaseColumns

object FeedReaderContract {
    object FeedEntry : BaseColumns {
        const val ID = "_id"
        const val TABLE_NAME = "news"
        const val NEWS_TITLE = "title"
        const val NEWS_ANNOTATION = "annotation"
        const val NEWS_ID = "news_id"
        const val NEWS_ID_RESOURCE = "id_resource"
        const val NEWS_IMG = "image"
        const val NEWS_LOCAL_IMG = "local_image"
        const val NEWS_MOBILE_URL = "mobile_url"
        const val NEWS_DATE = "date"
        const val NEWS_DATE_UTS = "date_uts"
        const val NEWS_TYPE = "type"
    }
}