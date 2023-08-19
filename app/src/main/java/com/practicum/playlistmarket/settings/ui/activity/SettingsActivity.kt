package com.practicum.playlistmarket.settings.ui.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.databinding.ActivitySettingsBinding
import com.practicum.playlistmarket.settings.ui.view_model.SettingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySettingsBinding


    //private lateinit var viewModel: SettingViewModel


   private val viewModel: SettingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)



     //   viewModel = ViewModelProvider(this, SettingViewModel.getViewModelFactory())[SettingViewModel::class.java]


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