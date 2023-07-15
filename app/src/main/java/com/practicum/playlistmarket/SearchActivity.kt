package com.practicum.playlistmarket

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.practicum.playlistmarket.databinding.ActivitySearchBinding
import com.practicum.playlistmarket.network.SongSearchResponse
import com.practicum.playlistmarket.network.TrackApi
import com.practicum.playlistmarket.search.HistoryAdapter
import com.practicum.playlistmarket.search.SearchAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val HISTORY_TRACK = "history_track"
const val KEY_HISTORY = "key_history"
const val KEY_HISTORY_ALL = "key_history_all"

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private var searchText: String = ""
    private lateinit var sharedPref: SharedPreferences

    //    private val searchAdapter = SearchAdapter()
//    private val historyAdapter = HistoryAdapter()
    private val searchAdapter = SearchAdapter()
    private val historyAdapter = HistoryAdapter()

    private var flag = false
    private val tracksHistory = ArrayList<Track>()
    private val tracksSearch = ArrayList<Track>()
    private val handler = Handler(Looper.getMainLooper())

    private val searchRunnable = Runnable { search() }


    private val retrofit =
        Retrofit.Builder().baseUrl(urlMusicItunes)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    private val trackApi = retrofit.create(TrackApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_search)
        setContentView(binding.root)
        init()

        sharedPref = getSharedPreferences(HISTORY_TRACK, MODE_PRIVATE)
        val searchHistory = SearchHistory(sharedPref)

        searchHistory.addTrackHistory(tracksHistory)

        sharedPref.registerOnSharedPreferenceChangeListener(searchHistory.listener)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.search_toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

//        binding.editSearch.setOnEditorActionListener { _, actionId, _ ->
//            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                search()
//                true
//            }
//            false
//        }


        binding.apply {
            btClearHistory.setOnClickListener {
                searchHistory.clearTrack()
                historyMenu.visibility = View.GONE
                historyAdapter.notifyDataSetChanged()
            }
        }

        binding.apply {
            btClear.setOnClickListener {
                tracksSearch.clear()
                editSearch.setText("")
                val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(editSearch.windowToken, 0) // скрыть клавиатуру
                editSearch.clearFocus()
                binding.progressBar.visibility = View.GONE
                searchAdapter.notifyDataSetChanged()
                historyAdapter.notifyDataSetChanged()
            }

            editSearch.setOnFocusChangeListener { view, hasFocus ->
                historyMenu.visibility =
                    if (hasFocus && editSearch.text.isEmpty() &&
                        tracksHistory.isNotEmpty()
                    ) View.VISIBLE else View.GONE

                if (!hasFocus) sharedPref.edit()
                    .putString(KEY_HISTORY_ALL, Gson().toJson(historyAdapter.trackListHistory))
                    .apply()
            }
        }


        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchDebounce()
                binding.btClear.visibility = clearButtonVisibility(s)
                searchText = s.toString()

                binding.progressBar.visibility = View.VISIBLE
                searchAdapter.notifyDataSetChanged()

                if (searchText.isNotEmpty()) {
                    binding.historyMenu.visibility = View.GONE
                }

                if (searchText.isNotEmpty() and tracksSearch.isEmpty()) {
                    binding.progressBar.visibility = View.VISIBLE
                    searchAdapter.notifyDataSetChanged()
                }

                if (searchText.isEmpty()) {
                    tracksSearch.clear()
                    binding.progressBar.visibility = View.GONE
                    binding.historyMenu.visibility = View.VISIBLE
                    searchAdapter.notifyDataSetChanged()
                    historyAdapter.notifyDataSetChanged()
                }

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
        outState.putParcelableArrayList(TRACK_QUERY, searchAdapter.trackList)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.editSearch.setText(savedInstanceState.getString(SEARCH_QUERY, ""))
        val trackSave = savedInstanceState.getParcelableArrayList<Track>(TRACK_QUERY)
        if (trackSave != null) {
            searchAdapter.trackList.addAll(trackSave)
        }
    }

    private fun init() {
        searchAdapter.trackList = tracksSearch
        historyAdapter.trackListHistory = tracksHistory

        binding.apply {
            rcViewSearch.layoutManager =
                LinearLayoutManager(this@SearchActivity, LinearLayoutManager.VERTICAL, false)
            rcViewSearch.adapter = searchAdapter

            rcViewHistory.layoutManager =
                LinearLayoutManager(this@SearchActivity, LinearLayoutManager.VERTICAL, false)
            rcViewHistory.adapter = historyAdapter

            if (tracksHistory.isEmpty()) {
                historyMenu.visibility = View.GONE
            }

        }
    }

    override fun onResume() {
        super.onResume()
        if (!flag) {
            binding.editSearch.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.editSearch, InputMethodManager.SHOW_IMPLICIT)
            flag = true
        }
    }

    override fun onStop() {
        super.onStop()
        sharedPref.edit()
            .putString(KEY_HISTORY_ALL, Gson().toJson(historyAdapter.trackListHistory))
            .apply()
    }


    private fun showMessage(textNotFound: String, textNotInternet: String) {
        binding.apply {
            if (textNotFound.isNotEmpty()) {
                tracksSearch.clear()
                placeholderMessage.text = textNotFound
                massageNotInternet.visibility = View.GONE
                massageNotFound.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                searchAdapter.notifyDataSetChanged()
            }
            if (textNotInternet.isNotEmpty()) {
                tracksSearch.clear()
                placeholderMessageNotInternet.text = textNotInternet
                massageNotFound.visibility = View.GONE
                massageNotInternet.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                searchAdapter.notifyDataSetChanged()
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
                                tracksSearch.clear()
                                binding.massageNotFound.visibility = View.GONE
                                tracksSearch.addAll(response.body()?.results!!)
                                binding.progressBar.visibility = View.GONE

                                searchAdapter.notifyDataSetChanged()
                            } else {
                                if (binding.editSearch.text.isNotEmpty()) {
                                    showMessage(SearchStatus.NOTHING_FOUND.nameStatus, "")
                                    binding.historyMenu.visibility = View.GONE
                                    binding.progressBar.visibility = View.GONE
                                }
                            }
                        }
                        else -> showMessage("", SearchStatus.NO_INTERNET.nameStatus)
                    }
                }

                override fun onFailure(call: Call<SongSearchResponse>, t: Throwable) {
                    showMessage("", SearchStatus.NO_INTERNET.nameStatus)
                }
            })
    }

    private fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }


    companion object {
        private const val SEARCH_QUERY = "SEARCH_QUERY"
        private const val TRACK_QUERY = "TRACK_QUERY"
        private const val urlMusicItunes = "https://itunes.apple.com"
        private const val successfully = 200
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

}