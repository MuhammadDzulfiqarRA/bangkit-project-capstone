package com.dicoding.abai.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.abai.Event
import com.dicoding.abai.response.DataItem
import com.dicoding.abai.response.DummyResponseAPI
import com.dicoding.abai.response.ItemsItem
import com.dicoding.abai.response.StoriesResponse
import com.dicoding.abai.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class DashboardViewModel: ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> = _errorMessage

    private val _user = MutableLiveData<List<ItemsItem?>?>()
    val user: MutableLiveData<List<ItemsItem?>?> = _user

    private val _stories = MutableLiveData<List<DataItem?>?>()
    val stories: MutableLiveData<List<DataItem?>?> = _stories


    init {
        storiesViewer()
    }

    private fun userViewer() {
        _isLoading.value = true
        val client = ApiConfig.getApiServiceForUser().getUser("H")
        client.enqueue(object : Callback<DummyResponseAPI> {
            override fun onResponse(call: Call<DummyResponseAPI>, response: Response<DummyResponseAPI>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()?.items

                } else {
                    _errorMessage.value = Event("Failed to fetch users: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DummyResponseAPI>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = Event("Error: ${t.message}")
            }
        })
    }

    fun searchUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiServiceForUser().getUser(query)
        client.enqueue(object : Callback<DummyResponseAPI> {
            override fun onResponse(call: Call<DummyResponseAPI>, response: Response<DummyResponseAPI>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()?.items
                } else {
                    _errorMessage.value = Event("Failed to fetch users: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DummyResponseAPI>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = Event("Error: ${t.message}")
            }
        })
    }

    private fun storiesViewer() {
        _isLoading.value = true
        val client = ApiConfig.getApiServiceForUser().getStories()
        client.enqueue(object : Callback<StoriesResponse> {
            override fun onResponse(call: Call<StoriesResponse>, response: Response<StoriesResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _stories.value = response.body()?.data
                    Log.d("DASHBOARD", "onResponse: ${response.body()?.message} ")

                } else {
                    _errorMessage.value = Event("Failed to fetch users: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = Event("Error: ${t.message}")
            }
        })
    }

}