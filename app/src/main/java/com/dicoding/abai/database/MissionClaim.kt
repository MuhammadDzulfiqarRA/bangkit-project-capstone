package com.dicoding.abai.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "mission_claim",
    foreignKeys = [ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MissionClaim(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val idMissionClaimed: Int
)
