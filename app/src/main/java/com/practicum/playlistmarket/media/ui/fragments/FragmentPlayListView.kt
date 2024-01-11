package com.practicum.playlistmarket.media.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.databinding.FragmentPlayListViewBinding
import com.practicum.playlistmarket.media.ui.adapter.TrackClickListenerLong
import com.practicum.playlistmarket.media.ui.adapter.playlistViewAdapter.PlayListViewAdapter
import com.practicum.playlistmarket.media.ui.states.PlayListViewState
import com.practicum.playlistmarket.media.ui.view_model.PlayListViewViewModel
import com.practicum.playlistmarket.player.domain.models.Track
import com.practicum.playlistmarket.player.ui.fragment.MediaPlayerFragment
import org.koin.androidx.viewmodel.ext.android.viewModel



const val EXTRA_NAME_PLAYLIST = "name_playlist"
const val EXTRA_DESCRIPTION_PLAYLIST = "description_playlist"
const val EXTRA_URL_IMAGE_PLAYLIST = "url_image_playlist"
const val EXTRA_ID_PLAYLIST = "id_playlist"

class FragmentPlayListView : Fragment(), TrackClickListenerLong {

    private var _binding: FragmentPlayListViewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlayListViewViewModel by viewModel()

    private lateinit var deleteTrack: MaterialAlertDialogBuilder
    private lateinit var deletePlaylist: MaterialAlertDialogBuilder

    private var trackIdPlayList: String = "null"
    var uri: String = "null"


    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    private lateinit var bottomSheetBehaviorSetting: BottomSheetBehavior<*>


    val adapter = PlayListViewAdapter(this)

    override fun onTrackClick(track: Track) {
        findNavController().navigate(
            R.id.action_fragmentPlayListView_to_mediaPlayerFragment,
            MediaPlayerFragment.createArgs(
                track.trackId, track.artworkUrl100, track.trackName,
                track.artistName, track.trackTimeMillis, track.collectionName, track.releaseDate,
                track.primaryGenreName, track.country, track.isFavorite, track, track.previewUrl
            )
        )
    }

    //Долгое нажатие на трек
    override fun onItemLongClick(trackId: String) {
        deleteTrack.show()
        trackIdPlayList = trackId
        Log.d("onItemLongClick", "onItemLongClick")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayListViewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idPlaylist = requireArguments().getInt(EXTRA_ID_PLAYLIST)
        viewModel.fillDataTracks(idPlaylist)
        viewModel.showInfoPlaylist(idPlaylist)

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetBehavior).apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
        bottomSheetBehaviorSetting =
            BottomSheetBehavior.from(binding.bottomSheetBehaviorSetting).apply {
                state = BottomSheetBehavior.STATE_HIDDEN
            }

        binding.apply {
            viewModel.observePlaylist().observe(viewLifecycleOwner){ playlist ->
                namePlaylist.text = playlist.name
                description.text = playlist.description
                Log.d("playlistName" , binding.namePlaylist.text.toString() + "view")
                uri = playlist.uri.toString()
                Glide.with(binding.root)
                    .load(uri)
                    .centerCrop()
                    .placeholder(R.drawable.image_shape_default_playlist)
                    .into(imagePlaylist)
            }

            toolbar.setOnClickListener {
                findNavController().popBackStack()
            }
        }



        viewModel.observeStateTracksView().observe(viewLifecycleOwner) {
            when (it) {
                is PlayListViewState.ShowContent -> showContent(it.tracks)
                is PlayListViewState.Empty -> ""
            }
        }

        binding.apply {
            rvTracks.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvTracks.adapter = adapter
        }



        deleteTrack = MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.delete_track)
            .setNegativeButton(R.string.no) { dialog, which ->

            }
            .setPositiveButton(R.string.yes) { dialog, which ->
                viewModel.deleteTrackPlaylist(trackIdPlayList, idPlaylist)
            }

        viewModel.observeStateDeleteTrack().observe(viewLifecycleOwner) {
            if (it) {
                viewModel.fillDataTracks(idPlaylist)
            }
        }



        Log.d("playlistName" , binding.namePlaylist.text.toString())


        //Размер bottomSheet
        Handler().postDelayed(Runnable { calculatePeekHeight() }, 100L)


        binding.shareButton.setOnClickListener {
            btSharePlaylist()
        }

        binding.menuButton.setOnClickListener {
            binding.namePlaylistSetting.text = binding.namePlaylist.text
            binding.countTrackSetting.text = binding.quantityTracks.text
            val cornerSize = resources.getDimensionPixelSize(R.dimen.corners_image_track)
            Glide.with(binding.root)
                .load(uri)
                .centerCrop()
                .placeholder(R.drawable.image_shape_default_playlist)
                .transform(RoundedCorners(cornerSize))
                .into(binding.imagePlaylistSetting)
            bottomSheetBehaviorSetting.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        bottomSheetBehaviorSetting.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> binding.transparencyView.visibility =
                        View.VISIBLE

                    BottomSheetBehavior.STATE_HIDDEN -> binding.transparencyView.visibility =
                        View.GONE
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        binding.btShareSetting.setOnClickListener {
            btSharePlaylist()
        }

        binding.btRemovePlaylist.setOnClickListener {
            val namePlaylist = binding.namePlaylist.text
            deletePlaylist = MaterialAlertDialogBuilder(requireContext(), R.style.AlertTheme)
                .setMessage(resources.getString(R.string.delete_playlist) + " \"$namePlaylist\"?")
                .setNegativeButton(R.string.no) { dialog, which -> }
                .setPositiveButton(R.string.yes) { dialog, which ->
                    viewModel.deletePlaylist(idPlaylist)
                }
            deletePlaylist.show()
        }

        viewModel.observeStateDeletePlaylist().observe(viewLifecycleOwner) {
            if (it) {
                findNavController().popBackStack()
            }
        }

        binding.btEditInformation.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentPlayListView_to_fragmentNewPlayList,
                FragmentNewPlayList.createArgs(
                    binding.namePlaylist.text.toString(),
                    binding.description.text.toString(),
                    uri,
                    idPlaylist
                ))
        }
        Log.d("testText"," ${binding.namePlaylist.text.toString()}")


        behaviorSizeSetting()
    }

     private fun btSharePlaylist(){
         if(adapter.tracks.isEmpty()){
             //snackBar
             val typedValue = TypedValue()

             requireActivity().theme.resolveAttribute(
                 com.google.android.material.R.attr.colorOnPrimary,
                 typedValue,
                 true
             )
             val colorText = typedValue.data

             requireActivity().theme.resolveAttribute(
                 com.google.android.material.R.attr.colorOnSecondary,
                 typedValue,
                 true
             )
             val colorBackground = typedValue.data


             val snackBar = Snackbar.make(
                 binding.root,
                 getString(R.string.no_sharing_no_track),
                 Snackbar.LENGTH_LONG
             ).setBackgroundTint(colorBackground).setTextColor(colorText)
             val snackBarView = snackBar.view
             snackBarView.findViewById<TextView>(android.R.id.message)
             val textView =
                 snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
             textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
             snackBar.show()
         } else {
             val trackName = binding.namePlaylist.text.toString()
             val description = binding.description.text.toString()
             val quantityTracks = binding.quantityTracks.text.toString()
             sharePlaylist(adapter.tracks, trackName, description,quantityTracks)
         }
    }

    private fun sharePlaylist(tracks : List<Track>, nameTrack : String, description: String, quantityTracks : String) {
        viewModel.sharePlaylist(tracks, nameTrack, description, quantityTracks)
    }

    private fun showContent(tracks: List<Track>) {
        var timeSec = 0
        for (track in tracks) {
            timeSec += track.trackTimeMillis?.toInt() ?: 0
        }
        val timeMin = timeSec / 60000
        val trackCount = tracks.size
        adapter.tracks.clear()
        adapter.tracks.addAll(tracks.reversed())

        val duration = timeMin.toString() + " ${minuteString(timeMin)}"
        val quantityTracks = trackCount.toString() + " ${getEnding(trackCount)}"
        binding.durationTracks.text = duration
        binding.quantityTracks.text = quantityTracks
        adapter.notifyDataSetChanged()
    }

    private fun minuteString(min : Int) : String {
        val lastDigit = min % 10
        val lastTwoDigits = min % 100

        return when {
            lastTwoDigits in 11..19 -> requireContext().getString(R.string.minut)
            lastDigit == 1 -> requireContext().getString(R.string.minuta)
            lastDigit in 2..4 -> requireContext().getString(R.string.minuti)
            else -> requireContext().getString(R.string.minut)
        }
    }


    private fun calculatePeekHeight() {
        val screenHeight = binding.root.height
        val arr = IntArray(2)
        binding.shareButton.getLocationOnScreen(arr)
        bottomSheetBehavior.peekHeight = screenHeight - arr[1]
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun getEnding(number: Int): String {
        val lastDigit = number % 10
        val lastTwoDigits = number % 100

        return when {
            lastTwoDigits in 11..19 -> getString(R.string.trackov)
            lastDigit == 1 -> getString(R.string.track)
            lastDigit in 2..4 -> getString(R.string.tracka)
            else -> getString(R.string.trackov)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun behaviorSizeSetting() {
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels
        binding.bottomSheetBehaviorSetting.layoutParams.height = screenHeight
        bottomSheetBehaviorSetting.peekHeight = screenHeight / 2
    }

    companion object {
        fun createArgs(
            namePlaylist: String,
            descriptionPlaylist: String?,
            urlPlaylist: String?,
            playlistId: Int,
        ): Bundle =
            bundleOf(
                EXTRA_NAME_PLAYLIST to namePlaylist,
                EXTRA_DESCRIPTION_PLAYLIST to descriptionPlaylist,
                EXTRA_URL_IMAGE_PLAYLIST to urlPlaylist,
                EXTRA_ID_PLAYLIST to playlistId,
            )
    }
}