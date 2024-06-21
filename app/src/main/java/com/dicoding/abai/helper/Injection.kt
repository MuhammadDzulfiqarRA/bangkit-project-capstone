package com.dicoding.abai.helper

import android.content.Context
import com.dicoding.abai.database.AppDatabase
import com.dicoding.abai.database.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val database = AppDatabase.getDatabase(context)
        return UserRepository.getInstance(database, pref)
    }
}