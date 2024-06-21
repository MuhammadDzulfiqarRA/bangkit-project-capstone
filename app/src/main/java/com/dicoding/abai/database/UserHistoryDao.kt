package com.dicoding.abai.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserHistoryDao {
    @Insert
    suspend fun insert(userHistory: UserHistory)

    @Query("SELECT * FROM user_history WHERE userId = :userId")
    suspend fun getUserHistoryByUserId(userId: Int): List<UserHistory>

    @Query("DELETE FROM user_history WHERE userId = :userId")
    suspend fun deleteUserHistoryByUserId(userId: Int)
}
