package com.dicoding.abai.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MissionClaimDao {
    @Insert
    suspend fun insert(missionClaim: MissionClaim)

    @Query("SELECT * FROM mission_claim WHERE userId = :userId")
    suspend fun getMissionClaimsByUserId(userId: Int): List<MissionClaim>

    // Add other necessary queries as needed
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMissionClaim(missionClaim: MissionClaim)

    @Update
    suspend fun updateMissionClaim(missionClaim: MissionClaim)
}
