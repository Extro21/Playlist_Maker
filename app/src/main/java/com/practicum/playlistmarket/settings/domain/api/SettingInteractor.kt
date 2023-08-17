package com.practicum.playlistmarket.settings.domain.api


interface SettingInteractor {

    fun sharingApp(link : String)
    fun openSupport()
    fun openTermsOfUse()


    fun changeTheme(check : Boolean)
    fun defaultChange(): Boolean


}