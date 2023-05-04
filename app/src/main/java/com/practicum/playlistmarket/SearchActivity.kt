package com.practicum.playlistmarket

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.playlistmarket.databinding.ActivitySearchBinding
import com.practicum.playlistmarket.databinding.ActivitySettingsBinding

class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding
    private lateinit var edText: EditText
    //val adapter = SearchAdapter()


    companion object {
        const val SEARCH_QUERY = "SEARCH_QUERY"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        // setContentView(R.layout.activity_search)
        setContentView(binding.root)
     //   init()

        val btClear = findViewById<ImageView>(R.id.btClear)
        edText = findViewById(R.id.editSearch)


        btClear.setOnClickListener {
            edText.setText("")
            val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(edText.windowToken, 0) // скрыть клавиатуру
            edText.clearFocus()
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btClear.visibility = clearButtonVisibility(s)


            }

            override fun afterTextChanged(s: Editable?) {
                //empty
            }

        }

        edText.addTextChangedListener(simpleTextWatcher)


    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }

    }

   fun clickToolBar(view: View) {
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_QUERY, edText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val searchQuery = savedInstanceState.getString(SEARCH_QUERY, "")
        edText.setText(searchQuery)
    }


//    fun init() {
//        binding.apply {
//            rcViewSearch.layoutManager =
//                LinearLayoutManager(this@SearchActivity, LinearLayoutManager.VERTICAL, false)
//            rcViewSearch.adapter = adapter
////val music = Music(R.drawable.ic_music, "Кукла колдуна", "Король и шут", "3:56")
////adapter.addMusicList(music)
//
//            val musicList = ArrayList<Music>(
//                listOf(
//                    Music(R.drawable.ic_music, "Кукла колдуна", "Король и шут", "3:56"),
//                    Music(R.drawable.ic_music, "Анархист", "Король и шут", "3:56"),
//                    Music(R.drawable.ic_music, "Лесник", "Король и шут", "3:56"),
//                    Music(R.drawable.ic_music, "Камнем по голове", "Король и шут", "3:56")
//                )
//            )
//            adapter.addMusicAllList(musicList)
//        }
//    }
}