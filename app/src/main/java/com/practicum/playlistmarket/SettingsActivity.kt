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
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val shareApp = findViewById<LinearLayout>(R.id.share_app)
        val support = findViewById<LinearLayout>(R.id.support)
        val termsOfUse = findViewById<LinearLayout>(R.id.termsОfUse)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.settings_toolbar)
        toolbar.setNavigationOnClickListener{
            finish()
        }

        shareApp.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, getString(R.string.massageEmail))
            }
            startActivity(Intent.createChooser(intent, getString(R.string.sentText)))
        }



        support.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.address)) )
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.themeSupport))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.massageSupport)
                )
            }
            startActivity(intent)
        }


        termsOfUse.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply{
                data = Uri.parse(getString(R.string.urlTermsOfUse))}
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

}