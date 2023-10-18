package com.practicum.playlistmarket.media.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.playlistmarket.databinding.FragmentSelectedTracksBinding
import com.practicum.playlistmarket.media.ui.FavoriteState
import com.practicum.playlistmarket.media.ui.adapter.FavoriteAdapter
import com.practicum.playlistmarket.media.ui.view_model.FavoriteViewModel
import com.practicum.playlistmarket.player.domain.models.Track
import com.practicum.playlistmarket.player.ui.activity.EXTRA_ARTIST_NAME
import com.practicum.playlistmarket.player.ui.activity.EXTRA_COLLECTION_NAME
import com.practicum.playlistmarket.player.ui.activity.EXTRA_COUNTRY
import com.practicum.playlistmarket.player.ui.activity.EXTRA_DATA
import com.practicum.playlistmarket.player.ui.activity.EXTRA_IMAGE
import com.practicum.playlistmarket.player.ui.activity.EXTRA_LIKE
import com.practicum.playlistmarket.player.ui.activity.EXTRA_PRIMARY_NAME
import com.practicum.playlistmarket.player.ui.activity.EXTRA_SONG
import com.practicum.playlistmarket.player.ui.activity.EXTRA_TIME_MILLIS
import com.practicum.playlistmarket.player.ui.activity.EXTRA_TRACK
import com.practicum.playlistmarket.player.ui.activity.EXTRA_TRACK_NAME
import com.practicum.playlistmarket.player.ui.activity.MediaPlayerActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentFavoriteTracks : Fragment() {

    private lateinit var binding: FragmentSelectedTracksBinding
    private val viewModel: FavoriteViewModel by viewModel()

    private var isClickAllowed = true


    private val adapter = FavoriteAdapter { track ->
        if (clickDebounce()) {
            openPlayerToIntent(track)
            Log.e("fovarite", track.isFavorite.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectedTracksBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            recyclerFavoriteTrack.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerFavoriteTrack.adapter = adapter
        }

        viewModel.fillData()
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }


    }

    private fun render(state: FavoriteState) {
        when (state) {
            is FavoriteState.Content -> {
                val tracks = state.track.reversed()
                showContent(tracks)
            }
            is FavoriteState.Empty -> showEmpty()
            is FavoriteState.Loading -> showLoading()
        }
    }

    private fun showLoading() {
        binding.apply {
            progressBarFavoriteTrack.visibility = View.VISIBLE
            recyclerFavoriteTrack.visibility = View.GONE
            massageFoundFavoriteTracks.visibility = View.GONE
        }
    }

    private fun showEmpty() {
        binding.apply {
            progressBarFavoriteTrack.visibility = View.GONE
            recyclerFavoriteTrack.visibility = View.GONE
            massageFoundFavoriteTracks.visibility = View.VISIBLE
        }
    }

    private fun showContent(track: List<Track>) {
        binding.apply {
            progressBarFavoriteTrack.visibility = View.GONE
            recyclerFavoriteTrack.visibility = View.VISIBLE
            massageFoundFavoriteTracks.visibility = View.GONE
        }
        adapter.trackListFavorite.clear()
        Log.e("qweasd", track.toString())
        adapter.trackListFavorite.addAll(track)
        adapter.notifyDataSetChanged()
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
        intent.putExtra(EXTRA_LIKE, track.isFavorite)
        intent.putExtra(EXTRA_TRACK, track)
        startActivity(intent)
        adapter.notifyDataSetChanged()
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewLifecycleOwner.lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY_MILLIS)
                isClickAllowed = true
            }
        }
        return current
    }


    override fun onResume() {
        super.onResume()
        Log.e("fovarite", "onResume")
        viewModel.fillData()
        adapter.notifyDataSetChanged()
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L

        @JvmStatic
        fun newInstance() =
            FragmentFavoriteTracks()
    }
}