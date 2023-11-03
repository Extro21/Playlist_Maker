package com.practicum.playlistmarket.search.ui.fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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

    private val viewModel: SearchViewModel by viewModel()

    private var isClickAllowed = true

    private var historyAdapter = HistoryAdapter {
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


        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btClear.visibility = clearButtonVisibility(s)
                viewModel.searchDebounce(
                    changedText = s?.toString() ?: " "
                )

            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        binding.editSearch.addTextChangedListener(simpleTextWatcher)
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


    private fun showHistory(track: List<Track>) {
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

    private fun showLoading() {
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
        if (binding.editSearch.text.isEmpty()) {
            viewModel.btClear()
        }

    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY_MILLIS)
                isClickAllowed = true
            }
        }
        return current
    }


    private fun openPlayerToIntent(track: Track) {
        findNavController().navigate(
            R.id.action_searchFragment_to_mediaPlayerFragment,
            MediaPlayerFragment.createArgs(track.trackId, track.artworkUrl100,track.trackName,
                track.artistName, track.trackTimeMillis, track.collectionName, track.releaseDate,
                track.primaryGenreName, track.country, track.isFavorite, track, track.previewUrl)
        )
    }

    private fun openPlayer(track: Track) {
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
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
    }

}