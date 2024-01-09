package com.practicum.playlistmarket.media.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.databinding.FragmentNewPlayListBinding
import com.practicum.playlistmarket.media.domain.module.PlayList
import com.practicum.playlistmarket.media.ui.states.PlayListCreateAndNewState
import com.practicum.playlistmarket.media.ui.view_model.NewPlayListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

const val NAME_CREATE_PLAYLIST = "name_playlist_create"
const val DESCRIPTION_CREATE_PLAYLIST = "description_playlist_create"
const val IMAGE_CREATE_PLAYLIST = "image_playlist_create"
const val ID_CREATE_PLAYLIST = "id_playlist_create"

class FragmentNewPlayList : Fragment() {

    private val viewModel: NewPlayListViewModel by viewModel()

    private var _binding: FragmentNewPlayListBinding? = null
    private val binding get() = _binding!!

    private var uriPlaylist = ""

    private lateinit var confirmDialog: MaterialAlertDialogBuilder

    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    private lateinit var callback : OnBackPressedCallback


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewPlayListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val idPlaylist = requireArguments().getInt(ID_CREATE_PLAYLIST)
        viewModel.checkStateCreateAndNew(idPlaylist)


        viewModel.observeStateNewAndCreatePlaylist().observe(viewLifecycleOwner) { state ->
            when (state) {
                is PlayListCreateAndNewState.CreatePlaylist -> {
                    createPlaylist(state.playList)
                    binding.btAddPlayList.setOnClickListener {
                        Log.d("logImage123", uriPlaylist)

                        val uri = viewModel.getUri(uriPlaylist)
                        viewModel.updatePlaylist(
                            binding.edTextNamePlaylistInput.text.toString(),
                            binding.edDescriptionInput.text.toString(),
                            uri,
                            idPlaylist
                        )
                        findNavController().popBackStack()
                    }
                    binding.newPlaylistToolbar.setOnClickListener {
                        findNavController().popBackStack()
                    }
                }

                is PlayListCreateAndNewState.NewPlaylist -> {
                    btAddPlaylist()
                    onBackPressedDispatcher()
                    binding.newPlaylistToolbar.setOnClickListener {
                        if (binding.edTextNamePlaylistInput.text.toString().isNotEmpty() ||
                            binding.edDescriptionInput.text.toString()
                                .isNotEmpty() || uriPlaylist != ""
                        ) {
                            confirmDialog.show()
                        } else {
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }

        pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    val cornerSize =
                        binding.root.resources.getDimensionPixelSize(R.dimen.indent_image_playlist)
                    Glide.with(binding.root)
                        .load(uri)
                        .centerCrop()
                        .transform(RoundedCorners(cornerSize))
                        .into(binding.imagePlayList)
                    viewModel.addImageStorage(uri)
                    uriPlaylist = "${uri.toString().reversed().substringBefore("/")}.jpg"
                }
            }

        binding.edTextNamePlaylistInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                val isEditTextEmpty = p0.isNullOrBlank()
                binding.btAddPlayList.isEnabled = !isEditTextEmpty
            }
        }
        )
        binding.btAddPlayList.isEnabled = false


        binding.edTextNamePlaylistInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                AppCompatResources.getColorStateList(requireContext(), R.color.selector_et_blue)
                    ?.let {
                        binding.edTextNamePlaylist.setBoxStrokeColorStateList(it)
                        binding.edTextNamePlaylist.defaultHintTextColor = it
                    }
            } else {
                when (binding.edTextNamePlaylistInput.text.isNullOrEmpty()) {
                    true -> {
                        AppCompatResources.getColorStateList(requireContext(), R.color.selector_et)
                            ?.let {
                                binding.edTextNamePlaylist.setBoxStrokeColorStateList(it)
                                binding.edTextNamePlaylist.defaultHintTextColor =
                                    AppCompatResources.getColorStateList(
                                        requireContext(),
                                        R.color.selector_color_text
                                    )
                            }
                    }

                    false -> {
                        AppCompatResources.getColorStateList(
                            requireContext(),
                            R.color.selector_et_blue
                        )
                            ?.let {
                                binding.edTextNamePlaylist.setBoxStrokeColorStateList(it)
                                binding.edTextNamePlaylist.defaultHintTextColor = it
                            }
                    }
                }
            }
        }



        binding.edDescriptionInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                AppCompatResources.getColorStateList(requireContext(), R.color.selector_et_blue)
                    ?.let {
                        binding.edDescriptionLayout.setBoxStrokeColorStateList(it)
                        binding.edDescriptionLayout.defaultHintTextColor = it
                    }
            } else {
                when (binding.edDescriptionInput.text.isNullOrEmpty()) {
                    true -> {
                        AppCompatResources.getColorStateList(requireContext(), R.color.selector_et)
                            ?.let {
                                binding.edDescriptionLayout.setBoxStrokeColorStateList(it)
                                binding.edDescriptionLayout.defaultHintTextColor =
                                    AppCompatResources.getColorStateList(
                                        requireContext(),
                                        R.color.selector_color_text
                                    )
                            }
                    }

                    false -> {
                        AppCompatResources.getColorStateList(
                            requireContext(),
                            R.color.selector_et_blue
                        )
                            ?.let {
                                binding.edDescriptionLayout.setBoxStrokeColorStateList(it)
                                binding.edDescriptionLayout.defaultHintTextColor = it
                            }
                    }
                }
            }
        }

        binding.imagePlayList.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest((ActivityResultContracts.PickVisualMedia.ImageOnly)))
        }

        confirmDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.finish_creating_playlist)
            .setMessage(R.string.all_unsaved_data_will_be_lost)
            .setNeutralButton(R.string.cancellation) { dialog, which ->

            }
            .setPositiveButton(R.string.complete) { dialog, which ->
                findNavController().popBackStack()
            }



    }

    private fun onBackPressedDispatcher() {
        callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (binding.edTextNamePlaylistInput.text.toString().isNotEmpty() ||
                binding.edDescriptionInput.text.toString().isNotEmpty() || uriPlaylist != ""
            ) {
                confirmDialog.show()
            } else {
                findNavController().popBackStack()
            }
        }
    }

    private fun btAddPlaylist() {
        binding.btAddPlayList.setOnClickListener {
            Log.d("logImage123", "${uriPlaylist} add")
            val uri = viewModel.getUri(uriPlaylist)
            //    Log.d("logImage123", uri)

            //     Log.d("PathPlayList", uriPlaylist)
            lifecycleScope.launch {
                viewModel.addPlaylist(
                    name = binding.edTextNamePlaylistInput.text.toString(),
                    description = binding.edDescriptionInput.text.toString(),
                    uri = uri,
                )
            }

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
                "${getString(R.string.playlist)} ${binding.edTextNamePlaylistInput.text} ${
                    getString(
                        R.string.created
                    )
                }",
                Snackbar.LENGTH_LONG
            ).setBackgroundTint(colorBackground).setTextColor(colorText)
            val snackBarView = snackBar.view
            snackBarView.findViewById<TextView>(android.R.id.message)
            val textView =
                snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            snackBar.show()
            callback.isEnabled = false
            findNavController().popBackStack()
        }
    }


    private fun createPlaylist(playlist: PlayList) {
        binding.btAddPlayList.text = getString(R.string.save)
        binding.toolbarText.text = getString(R.string.edit)
        binding.edTextNamePlaylistInput.setText(playlist.name)
        binding.edDescriptionInput.setText(playlist.description)
        binding.edTextNamePlaylistInput.requestFocus()
        binding.edDescriptionInput.requestFocus()
        binding.edDescriptionInput.clearFocus()
        uriPlaylist = playlist.uri.toString().substringAfterLast("/")
        Log.d("logImage", uriPlaylist)
        Glide.with(binding.root)
            .load(playlist.uri)
            .centerCrop()
            .placeholder(R.drawable.image_shape_new_playlist)
            .into(binding.imagePlayList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun createArgs(
            namePlaylist: String?,
            descriptionPlaylist: String?,
            imagePlaylist: String?,
            idPlaylist: Int?
        ): Bundle =
            bundleOf(
                NAME_CREATE_PLAYLIST to namePlaylist,
                DESCRIPTION_CREATE_PLAYLIST to descriptionPlaylist,
                IMAGE_CREATE_PLAYLIST to imagePlaylist,
                ID_CREATE_PLAYLIST to idPlaylist,
            )
    }


}