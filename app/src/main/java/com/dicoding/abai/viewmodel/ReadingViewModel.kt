package com.dicoding.abai.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.abai.Event
import com.dicoding.abai.database.UserHistory
import com.dicoding.abai.database.UserRepository
import com.dicoding.abai.helper.UserModel
import com.dicoding.abai.response.DataItemStory
import com.dicoding.abai.response.StoryDetailsResponse
import com.dicoding.abai.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadingViewModel (private val repository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _storyReading = MutableLiveData<List<DataItemStory?>?>()
    val storyReading: MutableLiveData<List<DataItemStory?>?> = _storyReading

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> = _errorMessage

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> = _userId

    private val _userHistory = MutableLiveData<List<UserHistory>>()
    val userHistory: LiveData<List<UserHistory>> = _userHistory


    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getUserIdByUsername(username: String) {
        viewModelScope.launch {
            val userId = repository.getUserIdByUsername(username)
            _userId.postValue(userId!!)
        }
    }

    fun getUserHistoryByUserId(userId: Int) {
        viewModelScope.launch {
            val userHistory = repository.getUserHistoryByUserId(userId)
            _userHistory.postValue(userHistory)
        }
    }

    fun insertUserHistory(userId: Int, storyId: Int) {
        val newUserHistory = UserHistory(userId = userId, storyId = storyId)
        viewModelScope.launch {
            repository.insertUserHistory(newUserHistory)
        }
    }

    fun incrementStoryCount(userId: Int) {
        viewModelScope.launch {
            repository.incrementStoryCount(userId)
        }
    }


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