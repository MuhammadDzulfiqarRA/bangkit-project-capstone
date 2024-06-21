package com.dicoding.abai.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.abai.Event
import com.dicoding.abai.database.MissionClaim
import com.dicoding.abai.database.UserHistory
import com.dicoding.abai.database.UserRepository
import com.dicoding.abai.helper.UserModel
import com.dicoding.abai.helper.UserPreference
import com.dicoding.abai.response.DataItem
import com.dicoding.abai.response.MissionItem
import com.dicoding.abai.response.MissionResponse
import com.dicoding.abai.retrofit.ApiConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AchievementViewModel (
    private val repository: UserRepository,
    private val userPreference: UserPreference
): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> = _errorMessage

    private val _mission = MutableLiveData<List<MissionItem?>?>()
    val mission: LiveData<List<MissionItem?>?> = _mission

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> = _userId

    private val _userRank = MutableLiveData<Int>()
    val userRank: LiveData<Int> = _userRank

    private val _userStoryCount = MutableLiveData<Int>()
    val userStoryCount: LiveData<Int> = _userStoryCount

    private val _userMissionCompleted = MutableLiveData<Int>()
    val userMissionCompleted: LiveData<Int> = _userMissionCompleted


    private val _userMissionClaims = MutableLiveData<List<MissionClaim>>()
    val userMissionClaims: LiveData<List<MissionClaim>> get() = _userMissionClaims

    init {
        loadButtonStates0()
    }

    fun loadButtonStates0() : LiveData<Boolean> {
        val index = 0
        return userPreference.getButtonClaimState0(index).asLiveData()
    }


    private fun saveButtonState(buttonIndex: Int, isClaimed: Boolean) {
        if (buttonIndex == 0) {
            Log.d(TAG, "saveButtonState: ButtonIndex ${buttonIndex}")
            viewModelScope.launch {
                userPreference.saveButtonClaimState0(buttonIndex, isClaimed)
            }
        } else if (buttonIndex == 1) {
            Log.d(TAG, "saveButtonState: ButtonIndex ${buttonIndex}")
            viewModelScope.launch {
                userPreference.saveButtonClaimState1(buttonIndex, isClaimed)
            }
        } else if (buttonIndex == 2) {
            Log.d(TAG, "saveButtonState: ButtonIndex ${buttonIndex}")
            viewModelScope.launch {
                userPreference.saveButtonClaimState2(buttonIndex, isClaimed)
            }
        } else if (buttonIndex == 3) {
            Log.d(TAG, "saveButtonState: ButtonIndex ${buttonIndex}")
            viewModelScope.launch {
                userPreference.saveButtonClaimState3(buttonIndex, isClaimed)
            }
        } else if (buttonIndex == 4) {
            Log.d(TAG, "saveButtonState: ButtonIndex ${buttonIndex}")
            viewModelScope.launch {
                userPreference.saveButtonClaimState4(buttonIndex, isClaimed)
            }
        } else if (buttonIndex == 5) {
            Log.d(TAG, "saveButtonState: ButtonIndex ${buttonIndex}")
            viewModelScope.launch {
                userPreference.saveButtonClaimState5(buttonIndex, isClaimed)
            }
        }
    }



    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getUserIdByUsername(username: String) {
        viewModelScope.launch {
            val userId = repository.getUserIdByUsername(username)
            _userId.postValue(userId!!)
        }
    }

    fun getUserRank(userRanking: Int) {
        viewModelScope.launch {
            val userRank = repository.getUserRank(userRanking)
            _userRank.postValue(userRank)
        }
    }

    fun getUserMissionCompleted(userId : Int) {
        viewModelScope.launch {
            val missionCompleted = repository.getUserMissionCompleted( userId)
            _userMissionCompleted.postValue(missionCompleted)
        }
    }

    fun getUserStoryCount(userId: Int) {
        viewModelScope.launch {
            val storyCount = repository.getUserStoryCount(userId)
            _userStoryCount.postValue(storyCount)
        }
    }

    fun getMissionClaimByUserId(userId: Int) {
        viewModelScope.launch {
            val missionClaim = repository.getMissionClaimByUserId(userId)
            _userMissionClaims.postValue(missionClaim)
        }
    }

    fun incrementMissionCompleted(userId: Int) {
        viewModelScope.launch {
            repository.incrementMissionCompleted(userId)
        }
    }

    fun insertMissionClaim(missionClaim: MissionClaim) {
        viewModelScope.launch {
            repository.insertMissionClaim(missionClaim)
        }
    }

    fun updateMissionClaim(missionClaim: MissionClaim) {
        viewModelScope.launch {
            repository.updateMissionClaim(missionClaim)
        }
    }

}