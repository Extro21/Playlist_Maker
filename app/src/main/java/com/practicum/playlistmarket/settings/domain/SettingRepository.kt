package com.practicum.playlistmarket.settings.domain

interface SettingRepository {

    fun sharingApp(link :String)

    fun openSupport()

    fun openTermsOfUse()

    fun themeChange(check : Boolean)
    fun defaultChange() : Boolean
}