package com.practicum.playlistmarket.search.ui.activity


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.databinding.FragmentSearchBinding
import com.practicum.playlistmarket.player.domain.models.Track
import com.practicum.playlistmarket.player.ui.activity.*
import com.practicum.playlistmarket.search.ui.adapter.HistoryAdapter
import com.practicum.playlistmarket.search.ui.adapter.SearchAdapter
import com.practicum.playlistmarket.search.ui.view_model.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment :Fragment() {

    private lateinit var binding : FragmentSearchBinding

    private var searchText: String = ""

    private var flag = false
    private val tracksHistory = ArrayList<Track>()
    private val tracksSearch = ArrayList<Track>()

    private val handler = Handler(Looper.getMainLooper())

    private val viewModel: SearchViewModel by viewModel()

    private var isClickAllowed = true

    private val historyAdapter = HistoryAdapter {
        if (clickDebounce()) {
            openPlayer(it)
        }
    }

    private val searchAdapter = SearchAdapter {
        if (clickDebounce()) {
            openPlayer(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        init()
        viewModel.addHistoryTracks(tracksHistory)

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }


        binding.apply {
            btClearHistory.setOnClickListener {
                viewModel.clearTrackListHistory(historyAdapter.trackListHistory)
            }
        }

        viewModel.clearHistory.observe(viewLifecycleOwner) {
            binding.historyMenu.visibility = View.GONE
            historyAdapter.notifyDataSetChanged()
        }

        binding.apply {
            btClear.setOnClickListener {
                clearSearch()
            }

            editSearch.setOnFocusChangeListener { view, hasFocus ->
                historyMenu.visibility =
                    if (hasFocus && editSearch.text.isEmpty() &&
                        tracksHistory.isNotEmpty()
                    ) View.VISIBLE else View.GONE

                if (!hasFocus) viewModel.addHistoryList(historyAdapter.trackListHistory)
            }
        }

        binding.btResetSearch.setOnClickListener {
            binding.massageNotInternet.visibility = View.GONE
            viewModel.searchRequest(binding.editSearch.text.toString())
        }

        binding.editSearch.addTextChangedListener {
            binding.btClear.visibility = clearButtonVisibility(it)
            viewModel.searchDebounce(
                changedText = it?.toString() ?: ""
            )

            searchAdapter.notifyDataSetChanged()

            if (searchText.isNotEmpty()) {
                binding.historyMenu.visibility = View.GONE
            }
        }


    }

    private fun render(state: TrackState) {
        when (state) {
            is TrackState.Content -> showContent(state.tracks)
            is TrackState.Empty -> showEmpty()
            is TrackState.Error -> showError()
            is TrackState.Loading -> showLoading()
        }
    }

    fun showLoading() {
        binding.rcViewSearch.visibility = View.GONE
        binding.historyMenu.visibility = View.GONE
        binding.massageNotFound.visibility = View.GONE
        binding.massageNotInternet.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showError() {
        binding.rcViewSearch.visibility = View.GONE
        binding.massageNotInternet.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        binding.massageNotFound.visibility = View.GONE
        binding.placeholderMessageNotInternet.text = getString(R.string.not_internet)
    }

    private fun showEmpty() {
        binding.rcViewSearch.visibility = View.GONE
        binding.massageNotFound.visibility = View.VISIBLE
        binding.massageNotInternet.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.placeholderMessage.visibility = View.VISIBLE
        binding.placeholderMessage.text = getString(R.string.nothing_not_found)
    }

    private fun showContent(track: List<Track>) {
        binding.massageNotFound.visibility = View.GONE
        binding.massageNotInternet.visibility = View.GONE
        binding.rcViewSearch.visibility = View.VISIBLE
        binding.placeholderMessage.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.historyMenu.visibility = View.GONE
        searchAdapter.trackList.clear()
        searchAdapter.trackList.addAll(track)
        searchAdapter.notifyDataSetChanged()
    }


    fun clearSearch() {
        binding.apply {
            tracksSearch.clear()
            editSearch.setText("")
            //проверить
            val keyboard = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(editSearch.windowToken, 0) // скрыть клавиатуру
            editSearch.clearFocus()
            progressBar.visibility = View.GONE
            massageNotFound.visibility = View.GONE
            massageNotInternet.visibility = View.GONE
            searchAdapter.notifyDataSetChanged()
            historyAdapter.notifyDataSetChanged()
        }

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.editSearch.setText(savedInstanceState?.getString(SEARCH_QUERY, ""))
        val trackSave = savedInstanceState?.getParcelableArrayList<Track>(TRACK_QUERY)
        if (trackSave != null) {
            searchAdapter.trackList.addAll(trackSave)
        }
    }

//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        binding.editSearch.setText(savedInstanceState.getString(SEARCH_QUERY, ""))
//        val trackSave = savedInstanceState.getParcelableArrayList<Track>(TRACK_QUERY)
//        if (trackSave != null) {
//            searchAdapter.trackList.addAll(trackSave)
//        }
//    }

    private fun init() {
        searchAdapter.trackList = tracksSearch
        historyAdapter.trackListHistory = tracksHistory

        binding.apply {
            rcViewSearch.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rcViewSearch.adapter = searchAdapter

            rcViewHistory.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rcViewHistory.adapter = historyAdapter

            if (tracksHistory.isEmpty()) {
                historyMenu.visibility = View.GONE
            }

        }
    }

    override fun onStop() {
        super.onStop()

        if(binding.progressBar.visibility == View.VISIBLE){
            Log.d("clear", "onStop")

        }

    }

//    override fun onResume() {
//        super.onResume()
//        Log.d("clear", "onResume")
//        if(binding.editSearch.text.isEmpty()){
//            tracksSearch.clear()
//            searchAdapter.notifyDataSetChanged()
//        }
//
//    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

        private fun openPlayerToIntent(track: Track) {
        val intent = Intent(requireContext(), MediaPlayerActivity::class.java)
        intent.putExtra(EXTRA_TRACK_NAME, track.trackName)
        intent.putExtra(EXTRA_ARTIST_NAME, track.artistName)
        intent.putExtra(EXTRA_TIME_MILLIS, track.trackTimeMillis)
        intent.putExtra(EXTRA_IMAGE, track.artworkUrl100)
        intent.putExtra(EXTRA_DATA, track.releaseDate)
        intent.putExtra(EXTRA_COLLECTION_NAME, track.collectionName)
        intent.putExtra(EXTRA_PRIMARY_NAME, track.primaryGenreName)
        intent.putExtra(EXTRA_COUNTRY, track.country)
        intent.putExtra(EXTRA_SONG, track.previewUrl)
        startActivity(intent)
        historyAdapter.notifyDataSetChanged()
    }

    fun openPlayer(track: Track) {
        viewModel.addHistoryTrack(track)
        openPlayerToIntent(track)
    }


    companion object {
        private const val SEARCH_QUERY = "SEARCH_QUERY"
        private const val TRACK_QUERY = "TRACK_QUERY"
        private const val CLICK_DEBOUNCE_DELAY = 1000L



    }

}