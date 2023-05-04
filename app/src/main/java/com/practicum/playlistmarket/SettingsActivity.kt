package com.practicum.playlistmarket

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val shareApp = findViewById<LinearLayout>(R.id.share_app)
        val support = findViewById<LinearLayout>(R.id.support)
        val termsOfUse = findViewById<LinearLayout>(R.id.termsОfUse)

        shareApp.setOnClickListener {
            val massage = "https://practicum.yandex.ru/android-developer/"
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, massage)
            startActivity(Intent.createChooser(intent, "Отправляем текст"))
        }


        support.setOnClickListener {
            val address = "vostrik16@uandex.ru"

            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    "Сообщение разработчикам и разработчицам приложения Playlist Maker"
                )
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Спасибо разработчикам и разработчицам за крутое приложение!"
                )
            }
            startActivity(intent)
        }


        termsOfUse.setOnClickListener {
            val address = Uri.parse("https://yandex.ru/legal/practicum_offer/")
            val intent = Intent(Intent.ACTION_VIEW, address)
            startActivity(intent)
        }

    }


    @SuppressLint("UseSwitchCompatOrMaterialCode")
    fun nightTheme(view: View) {
        val switch = findViewById<Switch>(R.id.switch_setting)
        if (switch.isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun clickToolBar(view: View) {
        finish()
    }


}