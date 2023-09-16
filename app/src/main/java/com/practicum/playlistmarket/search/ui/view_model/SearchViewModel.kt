package com.practicum.playlistmarket.search.ui.view_model

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.practicum.playlistmarket.player.domain.models.Track
import com.practicum.playlistmarket.search.domain.api.TrackHistoryInteractor
import com.practicum.playlistmarket.search.domain.api.TrackInteractor
import com.practicum.playlistmarket.search.ui.activity.TrackState

class SearchViewModel(application: Application,
                    private val interactorHistory : TrackHistoryInteractor,
                   private val  interactorSearch : TrackInteractor  ) : AndroidViewModel(application) {

    private var latestSearchText: String? = null
    private val handler = Handler(Looper.getMainLooper())

    private val _clearHistory = MutableLiveData<Unit>()
    val clearHistory: LiveData<Unit> = _clearHistory

    fun addHistoryTracks(tracksHistory: ArrayList<Track>) {
        interactorHistory.addHistoryTracks(tracksHistory)
    }

    fun addHistoryList(tracksHistory: ArrayList<Track>) {
        interactorHistory.editHistoryList(tracksHistory)
    }


    fun clearTrackListHistory(tracksHistory: ArrayList<Track>) {
        _clearHistory.value = interactorHistory.clearTrack(tracksHistory)
    }

    fun addHistoryTrack(track: Track) {
        interactorHistory.addTrackInAdapter(track)
    }


    private val stateLiveData = MutableLiveData<TrackState>()
    fun observeState(): LiveData<TrackState> = stateLiveData


    private fun renderState(state: TrackState) {
        stateLiveData.postValue(state)
    }

    override fun onCleared() {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        this.latestSearchText = changedText
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)

        val searchRunnable = Runnable { searchRequest(changedText) }

        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY
        handler.postAtTime(
            searchRunnable,
            SEARCH_REQUEST_TOKEN,
            postTime,
        )


    }


    fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(TrackState.Loading)

            interactorSearch.searchTrack(newSearchText, object : TrackInteractor.TrackConsumer {
                override fun consume(foundTrack: List<Track>?, errorMessage: String?) {
                    val tracks = mutableListOf<Track>()
                    if (foundTrack != null) {
                        tracks.addAll(foundTrack)
                    }
                    when {
                        errorMessage != null -> {
                            renderState(
                                TrackState.Error
                            )
                        }
                        tracks.isEmpty() -> {
                            renderState(
                                TrackState.Empty
                            )
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
            })
        }


    }


    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()


//        fun factoryViewModelSearch(): ViewModelProvider.Factory =
//            viewModelFactory {
//                initializer {
//                    val application = this[APPLICATION_KEY]
//                    SearchViewModel(application!!)
//                }
//            }
    }
}


