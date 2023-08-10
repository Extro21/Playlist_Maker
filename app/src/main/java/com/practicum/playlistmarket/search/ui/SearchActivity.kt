package com.practicum.playlistmarket.search.ui

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
import com.practicum.playlistmarket.Creator.Creator
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.presentation.SearchStatus
import com.practicum.playlistmarket.databinding.ActivitySearchBinding
import com.practicum.playlistmarket.player.domain.models.Track
import com.practicum.playlistmarket.presentation.ui.SearchHistory
import com.practicum.playlistmarket.search.domain.api.TrackInteractor


const val HISTORY_TRACK = "history_track"
const val KEY_HISTORY = "key_history"
const val KEY_HISTORY_ALL = "key_history_all"

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    private var searchText: String = ""

    private lateinit var sharedPref: SharedPreferences

    private val searchAdapter = SearchAdapter()
    private val historyAdapter = HistoryAdapter()

    private var flag = false
    private val tracksHistory = ArrayList<Track>()
    private val tracksSearch = ArrayList<Track>()

    private val handler = Handler(Looper.getMainLooper())

    private val searchRunnable = Runnable { search() }

    private val moviesInteractor = Creator.provideTrackInteractor()

//    private val adapter = MoviesAdapter {
//        if (clickDebounce()) {
//            val intent = Intent(this, PosterActivity::class.java)
//            intent.putExtra("poster", it.image)
//            startActivity(intent)
//        }
//    }

//    private var isClickAllowed = true

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

        val toolbar = binding.searchToolbar
        toolbar.setNavigationOnClickListener {
            finish()
        }

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
                placeholderMessage.visibility = View.VISIBLE
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
        if (binding.editSearch.text.isNotEmpty()) {
            binding.apply {

                placeholderMessage.visibility = View.GONE
                rcViewSearch.visibility = View.GONE
//                massageNotInternet.visibility = View.GONE
//                massageNotFound.visibility = View.GONE
                progressBar.visibility = View.VISIBLE

                moviesInteractor.searchTrack(editSearch.text.toString(), object : TrackInteractor.TrackConsumer {
                    override fun consume(foundMovies: List<Track>, code : Int) {
                        handler.post {
                            if (code != 200){
                                showMessage("", SearchStatus.NO_INTERNET.nameStatus)
                                progressBar.visibility = View.GONE
                            } else {
                                progressBar.visibility = View.GONE
                                tracksSearch.clear()
                                tracksSearch.addAll(foundMovies)
                                rcViewSearch.visibility = View.VISIBLE
                                massageNotInternet.visibility = View.GONE
                                massageNotFound.visibility = View.GONE
                                searchAdapter.notifyDataSetChanged()
                                if (tracksSearch.isEmpty()) {
                                    showMessage(SearchStatus.NOTHING_FOUND.nameStatus, "")
                                } else {
                                    progressBar.visibility = View.GONE
                                }
                            }

                        }
                    }
                })

            }

        }
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

        private const val CLICK_DEBOUNCE_DELAY = 1000L

    }

}