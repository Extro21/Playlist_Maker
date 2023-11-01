package com.practicum.playlistmarket.media.ui.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.practicum.playlistmarket.databinding.FragmentNewPlayListBinding
import android.net.Uri
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.blue
import androidx.core.net.toUri
import androidx.core.view.isEmpty
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.media.domain.module.PlayList
import com.practicum.playlistmarket.media.ui.view_model.NewPlayListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream

class FragmentNewPlayList : Fragment() {

    private val viewModel: NewPlayListViewModel by viewModel()

    private lateinit var binding: FragmentNewPlayListBinding

    private var uriPlaylist = "null"

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
                    binding.imagePlayList.setImageURI(uri)
                    saveImageToPrivateStorage(uri)
                } else {
                    Log.d("PhotoPicker", "No media selected")
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

        binding.edTextNamePlaylist.setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        AppCompatResources.getColorStateList(requireContext(), R.color.blue)
                            ?.let { binding.edTextNamePlaylist.setBoxStrokeColorStateList(it) }
                    } else {
                when (binding.edTextNamePlaylistInput.text.isNullOrEmpty()) {
                    true -> {
                        AppCompatResources.getColorStateList(requireContext(), R.color.blue)
                            ?.let {
                                binding.edTextNamePlaylist.setBoxStrokeColorStateList(it)
                            }
                    }
                    else -> {
                        AppCompatResources.getColorStateList(requireContext(), R.color.blue)
                            ?.let {
                                binding.edTextNamePlaylist.setBoxStrokeColorStateList(
                                    it
                                )
                            }
                    }
                }
            }
        }

        // binding.edTextNamePlaylist.defaultHintTextColor = R.color.blue


        binding.imagePlayList.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest((ActivityResultContracts.PickVisualMedia.ImageOnly)))

        }

        binding.btAddPlayList.setOnClickListener {
            val filePath = File(
                requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "myalbum"
            )
            val file = File(filePath, uriPlaylist)
            Log.e("uriPlaylist", file.toUri().toString())
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


            Toast.makeText(
                requireContext(),
                "${getString(R.string.playlist)} ${binding.edTextNamePlaylistInput.text} ${
                    getString(
                        R.string.created
                    )
                }", Toast.LENGTH_LONG
            ).show()
            findNavController().popBackStack()
        }


        val confirmDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.finish_creating_playlist)
            .setMessage(R.string.all_unsaved_data_will_be_lost)
            .setNeutralButton(R.string.cancellation) { dialog, which ->

            }
            .setPositiveButton(R.string.complete) { dialog, which ->
                findNavController().popBackStack()
            }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.edTextNamePlaylistInput.text.toString().isNotEmpty() ||
                    binding.edDescriptionInput.text.toString().isNotEmpty()
                ) {
                    confirmDialog.show()
                } else {
                    findNavController().popBackStack()
                }

            }
        })

        binding.newPlaylistToolbar.setOnClickListener {
            if (binding.edTextNamePlaylistInput.text.toString().isNotEmpty() ||
                binding.edDescriptionInput.text.toString().isNotEmpty() || uriPlaylist != "null"
            ) {
                confirmDialog.show()
            } else {
                findNavController().popBackStack()
            }
        }


    }

    fun checkEnteredFields() {

    }

    private fun saveImageToPrivateStorage(uri: Uri) {
        val filePath =
            File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "myalbum")

        if (!filePath.exists()) {
            filePath.mkdirs()
        }

        uriPlaylist = "${uri.toString().reversed().substringBefore("/")}.jpg"

        Log.e("uriPlaylist", uriPlaylist)
        Log.e("uriPlaylist", "first_cover1.jpg")
        val file = File(filePath, uriPlaylist)

        val inputStream = requireActivity().contentResolver.openInputStream(uri)

        val outputStream = FileOutputStream(file)

        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
    }


}