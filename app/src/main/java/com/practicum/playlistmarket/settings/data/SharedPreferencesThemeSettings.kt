package com.practicum.playlistmarket.settings.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

private const val SHARED_PREF_THEME = "shared_pref_theme"
private const val KEY_THEME = "key_theme"

class SharedPreferencesThemeSettings(private val context: Context) {

    private lateinit var sharedPreferences: SharedPreferences
    var darkTheme = false


    fun valueChangeDefault() : Boolean {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_THEME, Application.MODE_PRIVATE)
        val boo =  sharedPreferences.getBoolean(KEY_THEME, false)
        return boo
    }


    fun switchTheme(darkThemeEnabled: Boolean) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_THEME, Application.MODE_PRIVATE)

        darkTheme = sharedPreferences.getBoolean(
            KEY_THEME,
            false
        )

        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                sharedPreferences.edit().putBoolean(KEY_THEME, true).apply()
                AppCompatDelegate.MODE_NIGHT_YES

            } else {
                sharedPreferences.edit().putBoolean(KEY_THEME, false).apply()
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

}