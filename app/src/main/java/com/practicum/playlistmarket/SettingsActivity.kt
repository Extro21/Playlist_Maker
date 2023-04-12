package com.practicum.playlistmarket
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toolbar

class SettingsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

//        val toolbar = findViewById<Toolbar>(R.id.settings_toolbar)
//        toolbar.setNavigationOnClickListener { onBackPressed() }

    }

    fun clickToolBar(view: View){
        finish()
    }

}