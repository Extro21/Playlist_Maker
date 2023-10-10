package com.practicum.playlistmarket.search.ui.fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.databinding.FragmentSearchBinding
import com.practicum.playlistmarket.player.domain.models.Track
import com.practicum.playlistmarket.player.ui.activity.*
import com.practicum.playlistmarket.search.ui.adapter.HistoryAdapter
import com.practicum.playlistmarket.search.ui.adapter.SearchAdapter
import com.practicum.playlistmarket.search.ui.view_model.SearchViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var searchText: String = ""

    //private var flag = false

    private val viewModel: SearchViewModel by viewModel()

    private var isClickAllowed = true

   // private lateinit var onTrackSearchDebounce : (Track) -> Unit

//    private val historyAdapter = HistoryAdapter {
//        if (clickDebounce()) {
//            openPlayer(it)
//        }
//    }

    private var historyAdapter = HistoryAdapter {
        //onTrackSearchDebounce(it)
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
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

//        onTrackSearchDebounce = debounce<Track>(CLICK_DEBOUNCE_DELAY,
//            viewLifecycleOwner.lifecycleScope, false) {
//            track -> openPlayer(track)
//        }

//        historyAdapter  = HistoryAdapter() {
//            onTrackSearchDebounce(it)
//        }

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }


        binding.apply {
            btClearHistory.setOnClickListener {
                viewModel.clearTrackListHistory()
                binding.historyMenu.visibility = View.GONE
                historyAdapter.notifyDataSetChanged()
            }
        }

        viewModel.clearHistory.observe(viewLifecycleOwner) {
            binding.historyMenu.visibility = View.GONE
            historyAdapter.notifyDataSetChanged()
        }

        binding.apply {
            btClear.setOnClickListener {
                viewModel.btClear()
            }

            editSearch.setOnFocusChangeListener { view, hasFocus ->
                historyMenu.visibility =
                    if (hasFocus && editSearch.text.isEmpty() &&
                        historyAdapter.trackListHistory.isNotEmpty()
                    ) View.VISIBLE else View.GONE
            }
        }

        binding.btResetSearch.setOnClickListener {
            binding.massageNotInternet.visibility = View.GONE
            viewModel.searchRequest(binding.editSearch.text.toString())
        }

        binding.editSearch.addTextChangedListener {
            binding.btClear.visibility = clearButtonVisibility(it)
            if(it != null){
                viewModel.searchDebounce(
                    changedText = it?.toString() ?: ""
                )
            }

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
            is TrackState.Default -> showDefault()
            is TrackState.SearchHistory -> showHistory(state.tracks)
        }
    }



    fun showHistory(track: List<Track>) {
            historyAdapter.trackListHistory.clear()
            historyAdapter.trackListHistory.addAll(track)
            binding.rcViewSearch.visibility = View.GONE
            binding.massageNotFound.visibility = View.GONE
            binding.massageNotInternet.visibility = View.GONE
            if (track.isNotEmpty() && binding.editSearch.text.isEmpty() and binding.editSearch.isActivated) {
                binding.historyMenu.visibility = View.VISIBLE
                searchAdapter.notifyDataSetChanged()
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

    private fun showDefault() {
        binding.apply {
            editSearch.setText("")
            val keyboard =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(editSearch.windowToken, 0) // скрыть клавиатуру
            editSearch.clearFocus()
            progressBar.visibility = View.GONE
            massageNotFound.visibility = View.GONE
            massageNotInternet.visibility = View.GONE
            binding.historyMenu.visibility = View.GONE
            searchAdapter.trackList.clear()
//            if (historyAdapter.trackListHistory.isNotEmpty()) {
//                binding.historyMenu.visibility = View.VISIBLE
//            }
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


    private fun init() {
        // historyAdapter.trackListHistory = tracksHistory

        binding.apply {
            rcViewSearch.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rcViewSearch.adapter = searchAdapter

            rcViewHistory.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rcViewHistory.adapter = historyAdapter

//            if (historyAdapter.trackListHistory.isEmpty()) {
//                historyMenu.visibility = View.GONE
//            }

        }
    }


    override fun onStop() {
        super.onStop()
      //  viewModel.addHistoryList(historyAdapter.trackListHistory)
        if (binding.editSearch.text.isEmpty()) {
            viewModel.btClear()
        }

    }

//    private fun clickDebounce(): Boolean {
//        val current = isClickAllowed
//        if (isClickAllowed) {
//            isClickAllowed = false
//            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
//        }
//        return current
//    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewLifecycleOwner.lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
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

    private fun openPlayer(track: Track) {
    //    viewModel.addHistoryTrack(track)
        viewModel.saveTrack(track)
        openPlayerToIntent(track)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val SEARCH_QUERY = "SEARCH_QUERY"
        private const val TRACK_QUERY = "TRACK_QUERY"
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

}