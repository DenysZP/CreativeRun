package com.dm.creativerun.manager

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit

class ThemePreferencesManager(context: Context) {

    companion object {
        private const val PREFERENCE_FILE_KEY = "theme_preferences"
        private const val CURRENT_MODE_KEY = "current_mode_key"
    }

    private val sharedPreferences =
        context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)
    var currentMode: Int = AppCompatDelegate.MODE_NIGHT_UNSPECIFIED
        private set

    init {
        currentMode =
            sharedPreferences.getInt(CURRENT_MODE_KEY, AppCompatDelegate.MODE_NIGHT_UNSPECIFIED)
        if (currentMode == AppCompatDelegate.MODE_NIGHT_UNSPECIFIED) {
            currentMode = AppCompatDelegate.MODE_NIGHT_NO
        }
    }

    fun changeThemeMode(activity: Activity?, newMode: Int) {
        currentMode = newMode
        sharedPreferences.edit {
            putInt(CURRENT_MODE_KEY, newMode)
        }
        applyThemeMode()
        (activity as? AppCompatActivity)?.delegate?.applyDayNight()
    }

    fun applyThemeMode() {
        AppCompatDelegate.setDefaultNightMode(currentMode)
    }
}