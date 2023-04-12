package com.practicum.playlistmarket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val buttonSearch = findViewById<Button>(R.id.button_search)
    val buttonMedia = findViewById<Button>(R.id.button_media)
    val buttonSetting = findViewById<Button>(R.id.button_setting)

    buttonSearch.setOnClickListener {
        navigateTo(SearchActivity::class.java)
    }

    buttonMedia.setOnClickListener {
        navigateTo(MediaActivity::class.java)
    }

    buttonSetting.setOnClickListener {
        navigateTo(SettingsActivity::class.java)
    }

}

    private fun navigateTo(clazz: Class<out AppCompatActivity>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }
}