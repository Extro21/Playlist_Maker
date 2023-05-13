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
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.playlistmarket.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var edText: EditText
    private lateinit var searchText: String
    private val adapter = SearchAdapter()


    companion object {
        const val SEARCH_QUERY = "SEARCH_QUERY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_search)
        setContentView(binding.root)
        init()

//        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.search_toolbar)
//        toolbar.setNavigationOnClickListener{
//            finish()
//        }

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
                searchText = s.toString()

            }

            override fun afterTextChanged(s: Editable?) {

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
        edText.setText(savedInstanceState.getString(SEARCH_QUERY, ""))
    }

    private fun init() {
        binding.apply {
            rcViewSearch.layoutManager =
                LinearLayoutManager(this@SearchActivity, LinearLayoutManager.VERTICAL, false)
            rcViewSearch.adapter = adapter

            val trackLists = ArrayList<Track>(
                listOf(
                    Track(
                        "https://is5-ssl.mzstatic.com/image/thumb/Music115/v4/7b/58/c2/7b58c21a-2b51-2bb2-e59a-9bb9b96ad8c3/00602567924166.rgb.jpg/100x100bb.jpg",
                        "Smells Like Teen Spirit", "Nirvana", "5:01"
                    ),
                    Track(
                        "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/3d/9d/38/3d9d3811-71f0-3a0e-1ada-3004e56ff852/827969428726.jpg/100x100bb.jpg",
                        "Billie Jean", "Michael Jackson", "4:35"
                    ),
                    Track(
                        "https://is4-ssl.mzstatic.com/image/thumb/Music115/v4/1f/80/1f/1f801fc1-8c0f-ea3e-d3e5-387c6619619e/16UMGIM86640.rgb.jpg/100x100bb.jpg",
                        "Stayin' Alive", "Bee Gees", "4:10"
                    ),
                    Track(
                        "https://is2-ssl.mzstatic.com/image/thumb/Music62/v4/7e/17/e3/7e17e33f-2efa-2a36-e916-7f808576cf6b/mzm.fyigqcbs.jpg/100x100bb.jpg",
                        "Whole Lotta Love", "Led Zeppelin", "5:33"
                    ),
                    Track(
                        "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/a0/4d/c4/a04dc484-03cc-02aa-fa82-5334fcb4bc16/18UMGIM24878.rgb.jpg/100x100bb.jpg",
                        "Sweet Child O'Mine", "Guns N' Roses", "5:03"
                    )

                )
            )
            adapter.addMusicAllList(trackLists)
        }
    }


}