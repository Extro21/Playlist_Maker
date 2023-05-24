package com.practicum.playlistmarket

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.playlistmarket.databinding.ActivitySearchBinding
import com.practicum.playlistmarket.network.SongSearchResponse
import com.practicum.playlistmarket.network.TrackApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchText: String
    private val adapter = SearchAdapter()

    private val retrofit =
        Retrofit.Builder().baseUrl(urlMusicItunes)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    private val trackApi = retrofit.create(TrackApi::class.java)

    private val tracks = ArrayList<Track>()

    companion object {
        private const val SEARCH_QUERY = "SEARCH_QUERY"
        private const val TRACK_QUERY = "TRACK_QUERY"
        private const val urlMusicItunes = "https://itunes.apple.com"
        private const val successfully = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_search)
        setContentView(binding.root)
        init()

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.search_toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.editSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                search()
                true
            }
            false
        }

        binding.apply {
            btClear.setOnClickListener {
                tracks.clear()
                editSearch.setText("")
                val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(editSearch.windowToken, 0) // скрыть клавиатуру
                editSearch.clearFocus()
                recreate()
            }
        }


        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btClear.visibility = clearButtonVisibility(s)
                searchText = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        binding.editSearch.addTextChangedListener(simpleTextWatcher)
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
        outState.putParcelableArrayList(TRACK_QUERY, adapter.trackList)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.editSearch.setText(savedInstanceState.getString(SEARCH_QUERY, ""))
        val trackSave = savedInstanceState.getParcelableArrayList<Track>(TRACK_QUERY)
        if (trackSave != null) {
            adapter.trackList.addAll(trackSave)
        }
    }

    private fun init() {
        adapter.trackList = tracks
        binding.apply {
            rcViewSearch.layoutManager =
                LinearLayoutManager(this@SearchActivity, LinearLayoutManager.VERTICAL, false)
            rcViewSearch.adapter = adapter

        }
    }

    private fun showMessage(textNotFound: String, textNotInternet: String) {
        binding.apply {
            if (textNotFound.isNotEmpty()) {
                tracks.clear()
                placeholderMessage.text = textNotFound
                massageNotInternet.visibility = View.GONE
                massageNotFound.visibility = View.VISIBLE
                adapter.notifyDataSetChanged()
            }
            if (textNotInternet.isNotEmpty()) {
                tracks.clear()
                placeholderMessageNotInternet.text = textNotInternet
                massageNotFound.visibility = View.GONE
                massageNotInternet.visibility = View.VISIBLE
                adapter.notifyDataSetChanged()
                btResetSearch.setOnClickListener {
                    massageNotInternet.visibility = View.GONE
                    search()
                }
            }
        }
    }

    private fun search() {
        trackApi.search(binding.editSearch.text.toString())
            .enqueue(object : Callback<SongSearchResponse> {
                override fun onResponse(
                    call: Call<SongSearchResponse>,
                    response: Response<SongSearchResponse>
                ) {
                    when (response.code()) {
                        successfully -> {
                            if (response.body()?.results?.isNotEmpty() == true) {
                                tracks.clear()
                                binding.massageNotFound.visibility = View.GONE
                                tracks.addAll(response.body()?.results!!)
                                adapter.notifyDataSetChanged()
                                //  showMessage("", "")
                            } else {
                                showMessage(getString(R.string.nothing_not_found), "")
                            }
                        }
                        else -> showMessage("", getString(R.string.not_internet))
                    }
                }

                override fun onFailure(call: Call<SongSearchResponse>, t: Throwable) {
                    showMessage("", getString(R.string.not_internet))
                }
            })
    }

}