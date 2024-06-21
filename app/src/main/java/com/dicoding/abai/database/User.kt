package com.dicoding.abai.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val username: String,
    val email: String,
    val photoUrl: String?,
    val rank: Int,
    val storyCount: Int,
    val missionCompleted: Int // Add this new column
)

