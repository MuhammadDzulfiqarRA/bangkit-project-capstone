package com.dicoding.abai.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.abai.Event
import com.dicoding.abai.response.DummyResponseAPI
import com.dicoding.abai.response.ItemsItem
import com.dicoding.abai.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel: ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> = _errorMessage

    private val _user = MutableLiveData<List<ItemsItem?>?>()
    val user: MutableLiveData<List<ItemsItem?>?> = _user


    init {
        userViewer()
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
}