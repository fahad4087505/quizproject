package com.example.a2by3_android.ui.uploadphotos

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amplifyframework.core.Amplify
import com.example.a2by3_android.base.BaseFragment
import com.example.a2by3_android.databinding.FragmentUploadPhotosBinding
import com.example.a2by3_android.extensions.invisible
import com.example.a2by3_android.extensions.show
import com.example.a2by3_android.repository.EmptyRepository
import com.example.a2by3_android.util.Constant.CAMERA_PERMISSION_REQUEST_CODE
import com.example.a2by3_android.util.Constant.READ_EXTERNAL_STORAGE_REQUEST_CODE
import com.example.a2by3_android.util.Constant.REQUEST_IMAGE_CAPTURE
import com.example.a2by3_android.util.Constant.REQUEST_PICK_IMAGE
import com.example.a2by3_android.util.imageutil.ImageUtil
import com.example.a2by3_android.util.imageutil.URIPathHelper
import java.io.File
import kotlinx.android.synthetic.main.fragment_upload_photos.btnPost
import kotlinx.android.synthetic.main.fragment_upload_photos.ivPhoto1
import kotlinx.android.synthetic.main.fragment_upload_photos.ivPhoto2
import kotlinx.android.synthetic.main.fragment_upload_photos.ivPhoto3
import kotlinx.android.synthetic.main.fragment_upload_photos.ivRemovePhoto1
import kotlinx.android.synthetic.main.fragment_upload_photos.ivRemovePhoto2
import kotlinx.android.synthetic.main.fragment_upload_photos.ivRemovePhoto3
import kotlinx.android.synthetic.main.fragment_upload_photos.lytTakePhoto
import kotlinx.android.synthetic.main.fragment_upload_photos.lytUploadPhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


const val TAG = "UploadPhotosFragment"

class UploadPhotosFragment : BaseFragment<FragmentUploadPhotosBinding, EmptyRepository>() {

  private var imageFilesList: ArrayList<File>? = arrayListOf()
  private var imageUrlsList: ArrayList<String> = arrayListOf()

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
    setUpClickListener()
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
      setImage(imageBitmap)
      val imageUri: Uri? = ImageUtil.getImageUri(requireContext(), imageBitmap)
      val uriPathHelper = URIPathHelper()
      val filePath = imageUri?.let { uriPathHelper.getPath(requireContext(), it) }
      filePath?.let {
        val imageFile = File(it)
        addImageFiles(imageFile)
      }
    } else if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
      val imageUri: Uri? = data?.data
      val uriPathHelper = URIPathHelper()
      val filePath = imageUri?.let { uriPathHelper.getPath(requireContext(), it) }
      filePath?.let {
        val imageFile = File(it)
        addImageFiles(imageFile)
      }
      val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        imageUri?.let {
          ImageDecoder.createSource(
            requireContext().contentResolver,
            it
          )
        }?.let { ImageDecoder.decodeBitmap(it) }
      } else {
        MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)
      }
      bitmap?.let { setImage(it) }
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
          requestPermissions(
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            READ_EXTERNAL_STORAGE_REQUEST_CODE
          )
        } else if (permission == Manifest.permission.CAMERA) {
          requestPermissions(
            arrayOf(
              Manifest.permission.READ_EXTERNAL_STORAGE,
              Manifest.permission.WRITE_EXTERNAL_STORAGE,
              Manifest.permission.CAMERA
            ),
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
      .setPositiveButton(
        "App Settings"
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

  private fun setImage(bitMap: Bitmap) {
    when {
      ivPhoto1.drawable == null -> {
        ivPhoto1.setImageBitmap(bitMap)
        ivRemovePhoto1.show()
        ivPhoto1.clipToOutline = true
      }
      ivPhoto2.drawable == null -> {
        ivPhoto2.setImageBitmap(bitMap)
        ivRemovePhoto2.show()
        ivPhoto2.clipToOutline = true
      }
      ivPhoto3.drawable == null -> {
        ivPhoto3.setImageBitmap(bitMap)
        ivRemovePhoto3.show()
        ivPhoto3.clipToOutline = true
      }
      else -> return
    }
  }

  private fun addImageFiles(imageFile: File) {
    imageFilesList?.let {
      if (it.size < 3) {
        it.add(imageFile)
        Log.d(TAG, "addImageFiles: $imageFilesList")
      }
    }
  }

  private fun setUpClickListener() {

    btnPost.setOnClickListener {
      val imagesListReturned = uploadImagesTos3()
      Log.d(TAG, "Images Returned List: $imagesListReturned")
    }

    lytTakePhoto.setOnClickListener {
      if (imageFilesList?.size == 3) {
        Toast.makeText(requireContext(), "Cannot Upload More Than 3 Photos", Toast.LENGTH_SHORT)
          .show()
      } else {
        if (askForPermissions(Manifest.permission.CAMERA)) {
          dispatchTakePictureIntent()
        }
      }
    }

    lytUploadPhoto.setOnClickListener {
      if (imageFilesList?.size == 3) {
        Toast.makeText(requireContext(), "Cannot Upload More Than 3 Photos", Toast.LENGTH_SHORT)
          .show()
      } else {
        if (askForPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)) {
          pickImageFromGallery()
        }
      }
    }

    ivRemovePhoto1.setOnClickListener {
      ivPhoto1.setImageDrawable(null)
      ivRemovePhoto1.invisible()
      imageFilesList?.removeFirst()
      Log.d(TAG, "removeImageFiles: $imageFilesList")
    }

    ivRemovePhoto2.setOnClickListener {
      ivPhoto2.setImageDrawable(null)
      ivRemovePhoto2.invisible()
      when (imageFilesList?.size) {
        2 -> {
          imageFilesList?.removeAt(imageFilesList?.size!! - 1)
          Log.d(TAG, "removeImageFiles: $imageFilesList")
        }
        1 -> {
          imageFilesList?.removeLast()
          Log.d(TAG, "removeImageFiles: $imageFilesList")
        }
        3 -> {
          imageFilesList?.removeAt(1)
          Log.d(TAG, "removeImageFiles: $imageFilesList")
        }
      }
    }

    ivRemovePhoto3.setOnClickListener {
      ivPhoto3.setImageDrawable(null)
      ivRemovePhoto3.invisible()
      imageFilesList?.removeLast()
      Log.d(TAG, "removeImageFiles: $imageFilesList")
    }
  }

  /** Not using this function, as we not not using Amazon Sdk for Android
  for Storage
   */
  private fun UploadImages() {
    imageFilesList?.forEach {
      Amplify.Storage.uploadFile(it.name, it,
        { Log.i("MyAmplifyApp", "Successfully uploaded: ${it.key}") },
        { Log.e("MyAmplifyApp", "Upload failed", it) }
      )
    }
  }

  private fun uploadImagesTos3(): ArrayList<String> {
    val imageUrlsList: ArrayList<String> = arrayListOf()

    val s3Client = AmazonS3Client(
      BasicAWSCredentials(
        "AKIAXYD4WGL7PACLSERQ",
        "7K6WyXMetzZVj8nDml7aaYfsk3TSivvyh7Z+jQDP"
      )
    )
    imageFilesList?.let {
      if (it.isNotEmpty()) {
        it.forEach {
          val por = PutObjectRequest(
            "twobythreecards",
            "Images/" + it.name,
            it
          )

          runBlocking(Dispatchers.IO) {
            try {
              s3Client.putObject(por)
              imageUrlsList.add("https://s3.us-east-1.amazonaws.com/twobythreecards/Images/" + it.name)
              Log.d(TAG, "imageUrlsList: $imageUrlsList")
            } catch (e: AmazonServiceException) {
              Log.d(TAG, e.errorMessage)
            } catch (e: AmazonClientException) {
            }
          }
        }
      }
    }
    return imageUrlsList
  }
}