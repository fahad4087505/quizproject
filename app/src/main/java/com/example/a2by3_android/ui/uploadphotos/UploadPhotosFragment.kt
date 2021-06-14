package com.example.a2by3_android.ui.uploadphotos

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.a2by3_android.base.BaseFragment
import com.example.a2by3_android.databinding.FragmentUploadPhotosBinding
import com.example.a2by3_android.repository.EmptyRepository
import com.example.a2by3_android.util.Constant.CAMERA_PERMISSION_REQUEST_CODE
import com.example.a2by3_android.util.Constant.READ_EXTERNAL_STORAGE_REQUEST_CODE
import com.example.a2by3_android.util.Constant.REQUEST_IMAGE_CAPTURE
import com.example.a2by3_android.util.Constant.REQUEST_PICK_IMAGE
import com.example.a2by3_android.util.imageutil.ImageUtil
import com.example.a2by3_android.util.imageutil.URIPathHelper
import kotlinx.android.synthetic.main.fragment_upload_photos.lytTakePhoto
import kotlinx.android.synthetic.main.fragment_upload_photos.lytUploadPhoto

class UploadPhotosFragment : BaseFragment<FragmentUploadPhotosBinding, EmptyRepository>() {
  override fun getFragmentBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentUploadPhotosBinding {
    return FragmentUploadPhotosBinding.inflate(inflater, container, false)
  }

  override fun getRepository(): EmptyRepository {
    return EmptyRepository()
  }

  override fun onPostInit() {
    lytTakePhoto.setOnClickListener {
      if (askForPermissions(Manifest.permission.CAMERA)) {
        dispatchTakePictureIntent()
      }
    }

    lytUploadPhoto.setOnClickListener {
      if (askForPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)) {
        pickImageFromGallery()
      }
    }
  }

  override fun onOptionsSelected(item: MenuItem) {
  }

  override fun onActivityCreation() {
  }

  private fun dispatchTakePictureIntent() {
    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    try {
      startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    } catch (e: ActivityNotFoundException) {
      // display error state to the user
    }
  }

  private fun pickImageFromGallery() {
    val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
    startActivityForResult(gallery, REQUEST_PICK_IMAGE)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
      val imageBitmap = data?.extras?.get("data") as Bitmap
      val imageUri: Uri? = ImageUtil.getImageUri(requireContext() , imageBitmap)
      val uriPathHelper = URIPathHelper()
      val filePath = imageUri?.let { uriPathHelper.getPath(requireContext(), it) }
    } else if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
      val imageUri: Uri? = data?.data
      val uriPathHelper = URIPathHelper()
      val filePath = imageUri?.let { uriPathHelper.getPath(requireContext(), it) }
    }
  }

  private fun isPermissionsAllowed(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
      requireContext(),
      permission
    ) == PackageManager.PERMISSION_GRANTED
  }

  private fun askForPermissions(permission: String): Boolean {
    if (!isPermissionsAllowed(permission)) {
      if (shouldShowRequestPermissionRationale(
          permission
        )
      ) {
        showPermissionDeniedDialog()
      } else {
        if (permission == Manifest.permission.READ_EXTERNAL_STORAGE) {
          requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE) , READ_EXTERNAL_STORAGE_REQUEST_CODE)
        } else if (permission == Manifest.permission.CAMERA) {
          requestPermissions(
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
          )
        }
      }
      return false
    }
    return true
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
  ) {
    when (requestCode) {
      CAMERA_PERMISSION_REQUEST_CODE -> {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            dispatchTakePictureIntent()
        } else {
          // permission is denied, you can ask for permission again, if you want
            askForPermissions(Manifest.permission.CAMERA)
        }
      }

      READ_EXTERNAL_STORAGE_REQUEST_CODE -> {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          pickImageFromGallery()
        } else {
          // permission is denied, you can ask for permission again, if you want
          askForPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
      }
    }
    return
  }

  private fun showPermissionDeniedDialog() {
    AlertDialog.Builder(requireContext())
      .setTitle("Permission Denied")
      .setMessage("Permission is denied, Please allow permissions from App Settings.")
      .setPositiveButton("App Settings"
      ) { _, _ ->
        // send to app settings if permission is denied permanently
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", "com.cards.android", null)
        intent.data = uri
        startActivity(intent)
      }
      .setNegativeButton("Cancel", null)
      .show()
  }
}