package com.practicum.playlistmarket.media.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.databinding.FragmentSelectedTracksBinding
import com.practicum.playlistmarket.media.ui.states.FavoriteState
import com.practicum.playlistmarket.media.ui.adapter.favoriteAdapter.FavoriteAdapter
import com.practicum.playlistmarket.media.ui.view_model.FavoriteViewModel
import com.practicum.playlistmarket.player.domain.models.Track
import com.practicum.playlistmarket.player.ui.fragment.MediaPlayerFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentFavoriteTracks : Fragment() {

    private lateinit var binding: FragmentSelectedTracksBinding
    private val viewModel: FavoriteViewModel by viewModel()

    private var isClickAllowed = true


    private val adapter = FavoriteAdapter { track ->
        Log.e("CheckRV", "check")
        if (clickDebounce()) {
            Log.e("CheckRV", "check clickDebounce")
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
        findNavController().navigate(
            R.id.action_mediaFragment_to_mediaPlayerFragment,
            MediaPlayerFragment.createArgs(track.trackId, track.artworkUrl100,track.trackName,
                track.artistName, track.trackTimeMillis, track.collectionName, track.releaseDate,
                track.primaryGenreName, track.country, track.isFavorite, track, track.previewUrl)
        )
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