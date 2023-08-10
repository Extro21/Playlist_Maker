package com.practicum.playlistmarket.settings.domain


class SettingInteractorImpl(private val repository: SettingRepository) : SettingInteractor {


    override fun sharingApp(link: String) {
        repository.sharingApp(link)
    }

    override fun openSupport() {
        repository.openSupport()
    }

    override fun openTermsOfUse() {
        repository.openTermsOfUse()
    }


    override fun changeTheme(check : Boolean){
        repository.themeChange(check)
    }

    override fun defaultChange(): Boolean {
        return repository.defaultChange()
    }
}