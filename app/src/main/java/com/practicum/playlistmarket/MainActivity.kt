package com.practicum.playlistmarket

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {
@SuppressLint("MissingInflatedId")
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val buttonSearch = findViewById<Button>(R.id.button_search)
    val buttonMedia = findViewById<Button>(R.id.button_media)
    val buttonSetting = findViewById<Button>(R.id.button_setting)


    buttonSearch.setOnClickListener {
        val displaySearch = Intent(this, SearchActivity::class.java)
        startActivity(displaySearch)
    }

    buttonMedia.setOnClickListener {
        val displayMedia = Intent(this, MediaActivity::class.java)
        startActivity(displayMedia)
    }

    buttonSetting.setOnClickListener {
        val displaySetting = Intent(this, SettingsActivity::class.java)
        startActivity(displaySetting)
    }

}
}