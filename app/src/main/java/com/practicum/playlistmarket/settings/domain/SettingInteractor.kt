package com.practicum.playlistmarket.settings.domain


interface SettingInteractor {

    fun sharingApp(link : String)
    fun openSupport()
    fun openTermsOfUse()


    fun changeTheme(check : Boolean)
    fun defaultChange(): Boolean


}