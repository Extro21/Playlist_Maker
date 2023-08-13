package com.practicum.playlistmarket.main.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.practicum.playlistmarket.databinding.ActivityMainBinding
import com.practicum.playlistmarket.media.ui.MediaActivity
import com.practicum.playlistmarket.search.ui.SearchActivity
import com.practicum.playlistmarket.settings.ui.SettingsActivity


class MainActivity : AppCompatActivity() {

   // private lateinit var viewModel : MainViewModel

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val factory = MainViewModel.getViewModelFactory(application)
//        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        //Пларировал сделать viewmodel для перехода на активити но это не требуется
        binding.buttonSearch.setOnClickListener {
          //  viewModel.openSearch()
            navigateTo(SearchActivity::class.java)
        }

        binding.buttonMedia.setOnClickListener {
           // viewModel.openPlayList()
            navigateTo(MediaActivity::class.java)
        }

        binding.buttonSetting.setOnClickListener {
           // viewModel.openSetting()
            navigateTo(SettingsActivity::class.java)
        }

//        viewModel.openActivityTo.observe(this) { intent ->
//            startActivity(intent)
//        }

    }

    private fun navigateTo(clazz: Class<out AppCompatActivity>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }
}