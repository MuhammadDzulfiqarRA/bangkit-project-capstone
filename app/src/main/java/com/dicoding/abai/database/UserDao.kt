package com.dicoding.abai.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM user WHERE userId = :userId")
    suspend fun getUserById(userId: Int): User?

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<User>

    @Query("DELETE FROM user")
    suspend fun deleteAllUsers()

    @Query("SELECT rank FROM user WHERE userId = :userId")
    suspend fun getUserRank(userId: Int): Int

    @Query("SELECT userId FROM user WHERE username = :username")
    suspend fun getUserIdByUsername(username: String): Int?

    @Query("SELECT storyCount FROM user WHERE userId = :userId")
    suspend fun getUserStoryCount(userId: Int): Int

    @Query("SELECT missionCompleted FROM user WHERE userId = :userId")
    suspend fun getUserMissionCompleted(userId: Int): Int

    @Query("UPDATE user SET storyCount = storyCount + 1 WHERE userId = :userId")
    suspend fun incrementStoryCount(userId: Int)

    @Query("UPDATE user SET missionCompleted = missionCompleted + 1 WHERE userId = :userId")
    suspend fun incrementMissionCompleted(userId: Int)





}
