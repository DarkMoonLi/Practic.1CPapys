package com.example.newsblock.scenes.newsBlock

import androidx.lifecycle.*
import com.example.newsblock.api.NewsApi
import com.example.newsblock.models.NewX
import com.example.newsblock.models.News
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val newsApi = NewsApi()
    var newsList:MutableLiveData<Response<News>> = MutableLiveData<Response<News>>()

    fun getData()
    {
        viewModelScope.launch {
            newsList.setValue(newsApi.getNews())
        }
    }
}