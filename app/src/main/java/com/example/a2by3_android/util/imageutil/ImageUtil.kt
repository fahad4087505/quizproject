package com.example.a2by3_android.util.imageutil

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream

object ImageUtil {

  fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path =
      MediaStore.Images.Media.insertImage(
        inContext.contentResolver,
        inImage,
        getRandomString(),
        null
      )
    return Uri.parse(path)
  }

  private fun getRandomString(): String {
    val allowedChars = ('A'..'Z') + ('a'..'z')
    return (1..5)
      .map { allowedChars.random() }
      .joinToString("")
  }
}