package com.practicum.playlistmarket.settings.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.databinding.ActivitySettingsBinding


class SettingsActivity : AppCompatActivity() {

    // private lateinit var viewModel: SettingViewModel
    private lateinit var binding: ActivitySettingsBinding

    //lateinit var shar: SharedPreferences
    private lateinit var viewModel: SettingViewModel
    //private val settingInterctor = Creator.provideSettingInteractor(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val factory = SettingViewModel.getViewModelFactory()

        viewModel = ViewModelProvider(
            this,
            SettingViewModel.getViewModelFactory()
        )[SettingViewModel::class.java]


//        val shareApp = findViewById<LinearLayout>(R.id.share_app)
//        val support = findViewById<LinearLayout>(R.id.support)
//        val termsOfUse = findViewById<LinearLayout>(R.id.termsОfUse)


        //val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.settings_toolbar)
        binding.settingsToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.shareApp.setOnClickListener {
//            val intent = Intent(Intent.ACTION_SEND).apply {
//                type = "text/plain"
//                putExtra(Intent.EXTRA_TEXT, getString(R.string.massageEmail))
//            }
//            startActivity(Intent.createChooser(intent, getString(R.string.massageEmail)))
            val link = getString(R.string.massageEmail)
            // settingInterctor.sharingApp(link)
            viewModel.shareApp(link)
        }



        binding.support.setOnClickListener {
            viewModel.sentSupport()
//            val intent = Intent(Intent.ACTION_SENDTO).apply {
//                data = Uri.parse("mailto:")
//                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.address)))
//                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.themeSupport))
//                putExtra(Intent.EXTRA_TEXT, getString(R.string.massageSupport))
//            }
//            startActivity(intent)
        }


        binding.termsFUse.setOnClickListener {
            viewModel.termOfUse()
//            val link = "https://yandex.ru/legal/practicum_offer/"
//            settingInterctor.openTermsOfUse(link)
//            val intent = Intent(Intent.ACTION_VIEW).apply {
//                data = Uri.parse(getString(R.string.urlTermsOfUse))
//            }
//            startActivity(intent)
        }

//        viewModel.openWindowGo().observe(this) {
//           it
//        }


        binding.switchSetting.isChecked = viewModel.default()


        binding.switchSetting.setOnCheckedChangeListener { _, isChecked ->
            viewModel.switchTheme(isChecked)

        }


    }
}