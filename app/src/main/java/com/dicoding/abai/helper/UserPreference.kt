package com.dicoding.abai.helper

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.abai.database.AppDatabase
import com.dicoding.abai.database.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {



    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = user.username
            preferences[IS_LOGIN_KEY] = true
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[USERNAME_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun saveButtonClaimState0(buttonIndex: Int, isClaimed: Boolean) {
        val key = booleanPreferencesKey("button_claimed_$buttonIndex")
        dataStore.edit { preferences ->
            preferences[key] = isClaimed
        }
    }

    suspend fun saveButtonClaimState1(buttonIndex: Int, isClaimed: Boolean) {
        val key = booleanPreferencesKey("button_claimed_$buttonIndex")
        dataStore.edit { preferences ->
            preferences[key] = isClaimed
        }
    }

    suspend fun saveButtonClaimState2(buttonIndex: Int, isClaimed: Boolean) {
        val key = booleanPreferencesKey("button_claimed_$buttonIndex")
        dataStore.edit { preferences ->
            preferences[key] = isClaimed
        }
    }

    suspend fun saveButtonClaimState3(buttonIndex: Int, isClaimed: Boolean) {
        val key = booleanPreferencesKey("button_claimed_$buttonIndex")
        dataStore.edit { preferences ->
            preferences[key] = isClaimed
        }
    }

    suspend fun saveButtonClaimState4(buttonIndex: Int, isClaimed: Boolean) {
        val key = booleanPreferencesKey("button_claimed_$buttonIndex")
        dataStore.edit { preferences ->
            preferences[key] = isClaimed
        }
    }

    suspend fun saveButtonClaimState5(buttonIndex: Int, isClaimed: Boolean) {
        val key = booleanPreferencesKey("button_claimed_$buttonIndex")
        dataStore.edit { preferences ->
            preferences[key] = isClaimed
        }
    }

    fun getButtonClaimState0(buttonIndex: Int): Flow<Boolean> {
        val key = booleanPreferencesKey("button_claimed_$buttonIndex")
        Log.d(TAG, "getButtonClaimState: ${buttonIndex}")
        return dataStore.data.map { preferences ->
            preferences[key] ?: false
        }
    }

    fun getButtonClaimState1(buttonIndex: Int): Flow<Boolean> {
        val key = booleanPreferencesKey("button_claimed_$buttonIndex")
        Log.d(TAG, "getButtonClaimState: ${buttonIndex}")
        return dataStore.data.map { preferences ->
            preferences[key] ?: false
        }
    }

    fun getButtonClaimState2(buttonIndex: Int): Flow<Boolean> {
        val key = booleanPreferencesKey("button_claimed_$buttonIndex")
        Log.d(TAG, "getButtonClaimState: ${buttonIndex}")
        return dataStore.data.map { preferences ->
            preferences[key] ?: false
        }
    }

    fun getButtonClaimState3(buttonIndex: Int): Flow<Boolean> {
        val key = booleanPreferencesKey("button_claimed_$buttonIndex")
        Log.d(TAG, "getButtonClaimState: ${buttonIndex}")
        return dataStore.data.map { preferences ->
            preferences[key] ?: false
        }
    }

    fun getButtonClaimState4(buttonIndex: Int): Flow<Boolean> {
        val key = booleanPreferencesKey("button_claimed_$buttonIndex")
        Log.d(TAG, "getButtonClaimState: ${buttonIndex}")
        return dataStore.data.map { preferences ->
            preferences[key] ?: false
        }
    }

    fun getButtonClaimState5(buttonIndex: Int): Flow<Boolean> {
        val key = booleanPreferencesKey("button_claimed_$buttonIndex")
        Log.d(TAG, "getButtonClaimState: ${buttonIndex}")
        return dataStore.data.map { preferences ->
            preferences[key] ?: false
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val USERNAME_KEY = stringPreferencesKey("username")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
