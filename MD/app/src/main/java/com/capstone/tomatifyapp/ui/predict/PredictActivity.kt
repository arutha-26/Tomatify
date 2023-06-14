package com.capstone.tomatifyapp.ui.predict

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.capstone.tomatifyapp.R
import com.capstone.tomatifyapp.databinding.ActivityPredictBinding
import com.capstone.tomatifyapp.databinding.BottomDialogChooseUploadImageBinding
import com.capstone.tomatifyapp.ui.camerax.CameraXActivity
import com.capstone.tomatifyapp.utils.CAMERA_X_FILE
import com.capstone.tomatifyapp.utils.reduceFileImage
import com.capstone.tomatifyapp.utils.uriToFile
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import java.io.File

@Suppress("DEPRECATION")
class PredictActivity : AppCompatActivity() {

    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSION = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        private const val REQUEST_CODE_PERMISSION = 101
    }

    private lateinit var binding: ActivityPredictBinding
    private var imgFile: File? = null

    private val launcherIntentCameraX =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == CAMERA_X_RESULT) {
                val myFile =
                    activityResult?.data?.getSerializableExtra(CAMERA_X_FILE) as File
                imgFile = reduceFileImage(myFile)

                binding.imgAddStory.setImageBitmap(BitmapFactory.decodeFile(myFile.path))

            }
        }

    private val launcherImageGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                val myUri = activityResult?.data?.data as Uri
                val createFile = uriToFile(myUri, this@PredictActivity)

                imgFile = reduceFileImage(createFile)

                binding.imgAddStory.setImageURI(myUri)
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Predict Your Plant"

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSION, REQUEST_CODE_PERMISSION
            )
        }

        with(binding) {
            llAddImage.setOnClickListener {
                if (!allPermissionsGranted()) {
                    ActivityCompat.requestPermissions(
                        this@PredictActivity, REQUIRED_PERMISSION, REQUEST_CODE_PERMISSION
                    )
                } else {
                    showDialogSelectAddImage()
                }

            }

            buttonAdd.setOnClickListener {
                validation()
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun validation() {
        if (imgFile == null || !imgFile!!.exists()) {
            Toast.makeText(this, "No Photo Has Been Input!", Toast.LENGTH_SHORT).show()
            return
        }

        isLoading(true)
        val predictViewModel = ViewModelProvider(this).get(PredictViewModel::class.java)
        predictViewModel.uploadPhoto(imgFile as File).observe(this) { responseGeneral ->
            isLoading(false)

            if (responseGeneral != null) {
                if (!responseGeneral.error) {
                    // Handle success response
                    val responseJson = Gson().toJson(responseGeneral)
                    val intent = Intent(this, ResultPredictActivity::class.java)
                    intent.putExtra("responseJson", responseJson)
                    startActivity(intent)
                } else {
                    // Handle error response
                    Toast.makeText(this, responseGeneral.message, Toast.LENGTH_SHORT).show()
                }
            } else {
                // Handle null response
                Toast.makeText(this, "Failed to upload photo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraXActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun startImageGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherImageGallery.launch(chooser)
    }

    private fun showDialogSelectAddImage() {
        val binding: BottomDialogChooseUploadImageBinding =
            BottomDialogChooseUploadImageBinding.inflate(
                LayoutInflater.from(this)
            )
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogStyle)
        val view = binding.root

        with(binding) {

            llCamera.setOnClickListener {
                startCameraX()
                bottomSheetDialog.dismiss()
            }

            llGallery.setOnClickListener {
                startImageGallery()
                bottomSheetDialog.dismiss()
            }

            imgClose.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

        }

        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }

    private fun allPermissionsGranted(): Boolean = REQUIRED_PERMISSION.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this, "Did not Grant A Permission!", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun isLoading(isL: Boolean) {
        if (isL) {
            binding.rlLoading.visibility = View.VISIBLE
        } else {
            binding.rlLoading.visibility = View.GONE
        }
    }
}