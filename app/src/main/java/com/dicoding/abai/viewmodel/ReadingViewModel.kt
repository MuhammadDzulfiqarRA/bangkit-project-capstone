package com.dicoding.abai.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.abai.Event
import com.dicoding.abai.response.DataItemStory
import com.dicoding.abai.response.DummyResponseAPI
import com.dicoding.abai.response.ItemsItem
import com.dicoding.abai.response.StoryDetailsResponse
import com.dicoding.abai.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadingViewModel: ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _storyReading = MutableLiveData<List<DataItemStory?>?>()
    val storyReading: MutableLiveData<List<DataItemStory?>?> = _storyReading

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> = _errorMessage


//    init {
//        storyDataReading()
//    }

//    private fun userViewer() {
//        _isLoading.value = true
//        val client = ApiConfig.getApiServiceForUser().getUser("H")
//        client.enqueue(object : Callback<DummyResponseAPI> {
//            override fun onResponse(call: Call<DummyResponseAPI>, response: Response<DummyResponseAPI>) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    _user.value = response.body()?.items
//
//                } else {
//                    _errorMessage.value = Event("Failed to fetch users: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<DummyResponseAPI>, t: Throwable) {
//                _isLoading.value = false
//                _errorMessage.value = Event("Error: ${t.message}")
//            }
//        })
//    }

    fun storyDataReading( storyId : Int, userId : Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiServiceForUser().getStoriesData(storyId, userId)
        client.enqueue(object : Callback<StoryDetailsResponse> {
            override fun onResponse(call: Call<StoryDetailsResponse>, response: Response<StoryDetailsResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _storyReading.value = response.body()?.data

                } else {
                    _errorMessage.value = Event("Failed to fetch users: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<StoryDetailsResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = Event("Error: ${t.message}")
            }
        })
    }


}