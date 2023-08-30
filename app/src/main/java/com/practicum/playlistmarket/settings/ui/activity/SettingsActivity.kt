package com.practicum.playlistmarket.settings.ui.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.databinding.ActivitySettingsBinding
import com.practicum.playlistmarket.settings.ui.view_model.SettingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    private val viewModel: SettingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.settingsToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.shareApp.setOnClickListener {
            val link = getString(R.string.massageEmail)
            viewModel.shareApp(link)
        }

        binding.support.setOnClickListener {
            viewModel.sentSupport()
        }


        binding.termsFUse.setOnClickListener {
            viewModel.termOfUse()
        }

        binding.switchSetting.isChecked = viewModel.default()

        binding.switchSetting.setOnCheckedChangeListener { _, isChecked ->
            viewModel.switchTheme(isChecked)
        }


    }
}