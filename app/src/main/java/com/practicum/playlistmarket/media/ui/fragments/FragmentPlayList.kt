package com.practicum.playlistmarket.media.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.databinding.FragmentPlayListBinding
import com.practicum.playlistmarket.media.domain.module.PlayList
import com.practicum.playlistmarket.media.ui.states.PlayListState
import com.practicum.playlistmarket.media.ui.adapter.playlistAdapter.PlayListAdapter
import com.practicum.playlistmarket.media.ui.view_model.PlayListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentPlayList : Fragment() {

    private val viewModel: PlayListViewModel by viewModel()

    private lateinit var binding: FragmentPlayListBinding

    private val adapter = PlayListAdapter {

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
           findNavController().navigate(R.id.action_mediaFragment_to_fragmentNewPlayList)
        }

        viewModel.fillData()

        viewModel.observerState().observe(viewLifecycleOwner){
            render(it)
        }

        binding.recView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recView.adapter = adapter

        viewModel.observerStateTracks().observe(viewLifecycleOwner){

        }
    }

    private fun render(state: PlayListState){

        when(state){
            is PlayListState.Loading -> showLoading()
            is PlayListState.Empty -> showEmpty()
            is PlayListState.Content -> showContent(state.playList)
        }

    }

    private fun showContent(playList : List<PlayList>) = with(binding){
        progressBarPlayList.visibility = View.GONE
        imageView.visibility= View.GONE
        textEmpty.visibility = View.GONE
        recView.visibility = View.VISIBLE
        lifecycleScope.launch{
            playList.map {
                it.quantityTracks = viewModel.getTrackCount(it)
            }
            adapter.playList.clear()
            adapter.playList.addAll(playList)
            adapter.notifyDataSetChanged()
        }

    }

    private fun showLoading() = with(binding) {
        progressBarPlayList.visibility = View.VISIBLE
        imageView.visibility= View.GONE
        textEmpty.visibility = View.GONE
        recView.visibility = View.GONE
    }

    private fun showEmpty() = with(binding){
        progressBarPlayList.visibility = View.GONE
        imageView.visibility= View.VISIBLE
        textEmpty.visibility = View.VISIBLE
        recView.visibility = View.GONE
    }


    companion object {
        fun newInstance() = FragmentPlayList()
    }
}