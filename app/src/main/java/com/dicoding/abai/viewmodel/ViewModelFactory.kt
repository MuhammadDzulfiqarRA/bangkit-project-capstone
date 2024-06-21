package com.dicoding.abai.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.abai.database.UserRepository
import com.dicoding.abai.helper.UserPreference
import com.dicoding.abai.helper.Injection
import com.dicoding.abai.helper.dataStore

class ViewModelFactory(
    private val repository: UserRepository,
    private val userPreference: UserPreference
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ReadingViewModel::class.java) -> {
                ReadingViewModel(repository) as T
            }

            modelClass.isAssignableFrom(AchievementViewModel::class.java) -> {
                AchievementViewModel(repository, userPreference) as T
            }

            modelClass.isAssignableFrom(SettingViewModel::class.java) -> {
                SettingViewModel(repository) as T
            }

            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> {
                DashboardViewModel(repository, userPreference) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(
                    Injection.provideRepository(context),
                    UserPreference.getInstance(context.dataStore)
                ).also { INSTANCE = it }
            }
        }
    }
}
