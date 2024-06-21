package com.dicoding.abai.database

import com.dicoding.abai.helper.UserModel
import com.dicoding.abai.helper.UserPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class UserRepository private constructor(
    private val appDatabase: AppDatabase,
    private val userPreference: UserPreference
) {

    private val userDao = appDatabase.userDao()
    private val userHistoryDao = appDatabase.userHistoryDao()
    private val missionClaimDao = appDatabase.missionClaimDao()

    suspend fun saveUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.insert(user)
        }
    }

    suspend fun getUserIdByUsername(username: String): Int? {
        return userDao.getUserIdByUsername(username)
    }

    suspend fun getUserRank(userId: Int): Int {
        return userDao.getUserRank(userId)
    }

    suspend fun getUserStoryCount(userId: Int): Int {
        return userDao.getUserStoryCount(userId)
    }

    suspend fun saveSession (user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun getUserHistoryByUserId(userId: Int): List<UserHistory> {
        return userHistoryDao.getUserHistoryByUserId(userId)
    }

    suspend fun getMissionClaimByUserId(userId: Int): List<MissionClaim> {
        return missionClaimDao.getMissionClaimsByUserId(userId)
    }

    suspend fun getUserMissionCompleted(userId: Int): Int {
        return userDao.getUserMissionCompleted(userId)
    }

    suspend fun insertUserHistory(userHistory: UserHistory) {
        userHistoryDao.insert(userHistory)
    }

//    suspend fun insertUserMissionClaim(missionClaim: MissionClaim) {
//        missionClaimDao.insert(missionClaim)
//    }

    suspend fun insertMissionClaim(missionClaim: MissionClaim) {
        missionClaimDao.insertMissionClaim(missionClaim)
    }

    suspend fun updateMissionClaim(missionClaim: MissionClaim) {
        missionClaimDao.updateMissionClaim(missionClaim)
    }

    suspend fun incrementStoryCount(userId: Int) {
        userDao.incrementStoryCount(userId)
    }

    suspend fun incrementMissionCompleted(userId: Int) {
        userDao.incrementMissionCompleted(userId)
    }

    suspend fun deleteAllUsers() {
        withContext(Dispatchers.IO) {
            userDao.deleteAllUsers()
        }
    }


    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            storyDatabase: AppDatabase,
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(storyDatabase, userPreference)
            }.also { instance = it }
    }
}
