package com.practicum.playlistmarket
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val shareApp = findViewById<TextView>(R.id.share_app)
        shareApp.setOnClickListener {
          // val intent = Intent(Intent.ACTION_SEND)
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
            }
            intent.type = "plain/text";
            intent.putExtra(Intent.EXTRA_EMAIL, "vlad@yandex.ru")
            intent.putExtra(Intent.EXTRA_SUBJECT, "go")
            intent.putExtra(Intent.EXTRA_TEXT, "hello")
            startActivity(Intent.createChooser(intent,
                "Отправка письма..."));
        }


//        toolbar.setNavigationOnClickListener { onBackPressed() }



    }



    fun nightTheme(view: View){
        val switch = findViewById<Switch>(R.id.switch_setting)
        if(switch.isChecked){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun clickToolBar(view: View){
        finish()
    }




}