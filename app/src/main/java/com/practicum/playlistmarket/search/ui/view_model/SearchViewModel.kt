package com.practicum.playlistmarket.search.ui.view_model

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmarket.player.domain.models.Track
import com.practicum.playlistmarket.search.domain.api.TrackHistoryInteractor
import com.practicum.playlistmarket.search.domain.api.TrackInteractor
import com.practicum.playlistmarket.search.ui.fragment.TrackState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(
    application: Application,
    private val interactorHistory: TrackHistoryInteractor,
    private val interactorSearch: TrackInteractor
) : AndroidViewModel(application) {


    private var latestSearchText: String? = null
    private val handler = Handler(Looper.getMainLooper())

    private val _clearHistory = MutableLiveData<Unit>()
    val clearHistory: LiveData<Unit> = _clearHistory

//    fun showHistory() {
//        renderState(TrackState.SearchHistory(
//            getHistoryTrack())
//        )
//    }


    fun saveTrack(track: Track) {
        interactorHistory.saveTrack(track)
    }

    fun clearTrackListHistory() {
        interactorHistory.clearTrack()
        renderState(TrackState.SearchHistory(emptyList()))

    }

    private fun getHistoryTrack() = interactorHistory.getAllTracks()

    private val stateLiveData = MutableLiveData<TrackState>()
    fun observeState(): LiveData<TrackState> = stateLiveData


    private fun renderState(state: TrackState) {
        stateLiveData.postValue(state)
    }

    override fun onCleared() {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }

    private var searchJob: Job? = null
    fun searchDebounce(changedText: String) {

        if (changedText.isBlank()) {
            stateLiveData.value = TrackState.SearchHistory(getHistoryTrack())
        } else {
            this.latestSearchText = changedText

            searchJob?.cancel()

            searchJob = viewModelScope.launch {
                delay(SEARCH_DEBOUNCE_DELAY)
                searchRequest(changedText)
            }
        }
    }


    fun btClear() {
        renderState(TrackState.Default)

    }


    private fun processResult(foundTrack: List<Track>?, errorMessage: String?) {
        val tracks = mutableListOf<Track>()
        if (foundTrack != null) {
            tracks.addAll(foundTrack)
        }

        when {
            errorMessage != null -> {
                renderState(TrackState.Error)
            }

            tracks.isEmpty() -> {
                renderState(TrackState.Empty)
            }

            else -> {
                renderState(
                    TrackState.Content(
                        tracks = tracks
                    )
                )
            }
        }
    }

    fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(TrackState.Loading)

            viewModelScope.launch {
                interactorSearch.searchTrack(newSearchText)
                    .collect { pair ->
                        processResult(pair.first, pair.second)
                    }
            }
        }


    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()

    }
}


