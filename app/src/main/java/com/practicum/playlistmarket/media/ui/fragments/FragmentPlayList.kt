package com.practicum.playlistmarket.media.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.databinding.FragmentPlayListBinding
import com.practicum.playlistmarket.media.domain.module.PlayList
import com.practicum.playlistmarket.media.ui.states.PlayListState
import com.practicum.playlistmarket.media.ui.adapter.playlistAdapter.PlayListAdapter
import com.practicum.playlistmarket.media.ui.view_model.PlayListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class FragmentPlayList : Fragment() {


    private val viewModel: PlayListViewModel by viewModel()

    private lateinit var binding: FragmentPlayListBinding

    private val adapter = PlayListAdapter { playlist ->
        findNavController().navigate(
            R.id.action_mediaFragment_to_fragmentPlayListView,
            FragmentPlayListView.createArgs(
                playlist.name,
                playlist.description,
                playlist.uri,
                playlist.playListId
            )
        )
        // viewModel.fillDataTracks(playlist.playListId)
        Log.d("TracksAdapterView", playlist.playListId.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayListBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonNewPlayList.setOnClickListener {
            findNavController().navigate(R.id.action_mediaFragment_to_fragmentNewPlayList,
                FragmentNewPlayList.createArgs(
                null, null,null, null
            ))

        }
        Log.d("deletePlaylist", "deletePlaylist onResume")
        viewModel.fillData()

        viewModel.observerState().observe(viewLifecycleOwner) {
            render(it)
        }

        binding.recView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recView.adapter = adapter

    }

    private fun render(state: PlayListState) {
        when (state) {
            is PlayListState.Loading -> showLoading()
            is PlayListState.Empty -> showEmpty()
            is PlayListState.Content -> showContent(state.playList)
        }

    }

    private fun showContent(playList: List<PlayList>) = with(binding) {
        progressBarPlayList.visibility = View.GONE
        imageView.visibility = View.GONE
        textEmpty.visibility = View.GONE
        recView.visibility = View.VISIBLE
        Log.d("deletePlaylist", "deletePlaylist showContent")
        adapter.playList.clear()
        adapter.playList.addAll(playList)
        adapter.notifyDataSetChanged()

    }

    private fun showLoading() = with(binding) {
        progressBarPlayList.visibility = View.VISIBLE
        imageView.visibility = View.GONE
        textEmpty.visibility = View.GONE
        recView.visibility = View.GONE
    }

    private fun showEmpty() = with(binding) {
        progressBarPlayList.visibility = View.GONE
        imageView.visibility = View.VISIBLE
        textEmpty.visibility = View.VISIBLE
        recView.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()

      //  adapter.notifyDataSetChanged()
    }


    companion object {
        fun newInstance() = FragmentPlayList()


    }
}