package com.practicum.playlistmarket


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView

class SearchActivity : AppCompatActivity() {
    //lateinit var binding: ActivitySearchBinding
    private lateinit var edText: EditText
    lateinit var searchText : String
    //val adapter = SearchAdapter()


    companion object {
        const val SEARCH_QUERY = "SEARCH_QUERY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // binding = ActivitySearchBinding.inflate(layoutInflater)
         setContentView(R.layout.activity_search)
       // setContentView(binding.root)
     //   init()

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.search_toolbar)
        toolbar.setNavigationOnClickListener{
            finish()
        }

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
                searchText = edText.text.toString()
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


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_QUERY, searchText)

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