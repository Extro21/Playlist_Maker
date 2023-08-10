package com.practicum.playlistmarket.main.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.settings.ui.SettingsActivity


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSearch = findViewById<Button>(R.id.button_search)
        val buttonMedia = findViewById<Button>(R.id.button_media)
        val buttonSetting = findViewById<Button>(R.id.button_setting)

        val factory = MainViewModel.getViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]



        buttonSearch.setOnClickListener {
            viewModel.openSearch()
        }

        buttonMedia.setOnClickListener {
            viewModel.openPlayList()
        }

        buttonSetting.setOnClickListener {
           // viewModel.openSetting()
            navigateTo(SettingsActivity::class.java)
        }

        viewModel.openActivityTo.observe(this) { intent ->
            startActivity(intent)
        }



    }

    private fun navigateTo(clazz: Class<out AppCompatActivity>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }
}