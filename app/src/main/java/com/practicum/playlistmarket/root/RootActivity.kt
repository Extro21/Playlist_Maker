package com.practicum.playlistmarket.root

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRootBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container_view) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _ , destination, _ ->
            when(destination.id){
                R.id.fragmentNewPlayList, R.id.mediaPlayerFragment, R.id.fragmentPlayListView -> binding.bottomNavigationView.visibility = View.GONE
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }

        }

    }


}