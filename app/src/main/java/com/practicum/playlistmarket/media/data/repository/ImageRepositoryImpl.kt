package com.practicum.playlistmarket.media.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import com.practicum.playlistmarket.media.domain.repository.ImageRepository
import java.io.File
import java.io.FileOutputStream

class ImageRepositoryImpl(val context: Context) : ImageRepository {

    val path =
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

    private val childName = "myalbum"

    override fun getUri(uriPlaylist: String): String {

        val filePath = File(
            path.toString(),
            childName
        )

        val file = File(filePath, uriPlaylist)

        return file.toUri().toString()

    }

    override fun saveImageToPrivateStorage(uri: Uri) {
        val filePath =
            File(path, childName)

        if (!filePath.exists()) {
            filePath.mkdirs()
        }
        val uriPlaylist = "${uri.toString().reversed().substringBefore("/")}.jpg"

        val file = File(filePath, uriPlaylist)

        val inputStream = context.contentResolver.openInputStream(uri)

        val outputStream = FileOutputStream(file)

        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
    }
}