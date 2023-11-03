package com.practicum.playlistmarket.media.ui.fragments

import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.net.toUri
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
import com.practicum.playlistmarket.media.ui.view_model.NewPlayListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class FragmentNewPlayList : Fragment() {

    private val viewModel: NewPlayListViewModel by viewModel()

    private lateinit var binding: FragmentNewPlayListBinding

    private var uriPlaylist = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewPlayListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    val cornerSize = binding.root.resources.getDimensionPixelSize(R.dimen.indent_image_playlist)
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

        val confirmDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.finish_creating_playlist)
            .setMessage(R.string.all_unsaved_data_will_be_lost)
            .setNeutralButton(R.string.cancellation) { dialog, which ->

            }
            .setPositiveButton(R.string.complete) { dialog, which ->
                findNavController().popBackStack()
            }

        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (binding.edTextNamePlaylistInput.text.toString().isNotEmpty() ||
                binding.edDescriptionInput.text.toString().isNotEmpty() || uriPlaylist != ""
            ) {
                confirmDialog.show()
            } else {
                findNavController().popBackStack()
            }
        }

        binding.btAddPlayList.setOnClickListener {

            val filePath = File(
                requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "myalbum"
            )

            val file = File(filePath, uriPlaylist)
            Log.e("PathPlayList", uriPlaylist)
            lifecycleScope.launch {
                viewModel.addPlaylist(
                    PlayList(
                        name = binding.edTextNamePlaylistInput.text.toString(),
                        description = binding.edDescriptionInput.text.toString(),
                        uri = file.toUri().toString(),
                        playListId = 0,
                        idTracks = 1
                    )
                )
            }


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


        binding.newPlaylistToolbar.setOnClickListener {
            if (binding.edTextNamePlaylistInput.text.toString().isNotEmpty() ||
                binding.edDescriptionInput.text.toString().isNotEmpty() || uriPlaylist != ""
            ) {
                confirmDialog.show()
            } else {
                findNavController().popBackStack()
            }
        }


    }


}