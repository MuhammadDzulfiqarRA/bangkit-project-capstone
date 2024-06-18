package com.dicoding.abai.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.abai.Event
import com.dicoding.abai.response.DataItem
import com.dicoding.abai.response.MissionItem
import com.dicoding.abai.response.MissionResponse
import com.dicoding.abai.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AchievementViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> = _errorMessage

    private val _mission = MutableLiveData<List<MissionItem?>?>()
    val mission: LiveData<List<MissionItem?>?> = _mission


//    init {
//        // Simulate fetching data
//        _mission.value = listOf(
//            MissionItem(id = 1, title = "Mission 1"),
//            MissionItem(id = 2, title = "Mission 2"))
//    }
    fun getMissionData(userId: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiServiceForUser().getMissionData(userId)
        client.enqueue(object : Callback<MissionResponse> {
            override fun onResponse(call: Call<MissionResponse>, response: Response<MissionResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _mission.value = response.body()?.data
                    Log.d("ACHIEVEMENT", "onResponse: ${response.body()?.message} ")

                } else {
                    _errorMessage.value = Event("Failed to fetch users: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MissionResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = Event("Error: ${t.message}")
            }
        })
    }
}