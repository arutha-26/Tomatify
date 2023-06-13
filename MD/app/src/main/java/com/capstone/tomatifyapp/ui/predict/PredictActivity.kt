package com.capstone.tomatifyapp.ui.predict

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.capstone.tomatifyapp.R
import com.capstone.tomatifyapp.databinding.ActivityPredictBinding
import com.capstone.tomatifyapp.databinding.BottomDialogChooseUploadImageBinding
import com.capstone.tomatifyapp.model.Predict
import com.capstone.tomatifyapp.model.ResponsePredict
import com.capstone.tomatifyapp.ui.camerax.CameraXActivity
import com.capstone.tomatifyapp.utils.CAMERA_X_FILE
import com.capstone.tomatifyapp.utils.reduceFileImage
import com.capstone.tomatifyapp.utils.uriToFile
import com.google.android.material.bottomsheet.BottomSheetDialog
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
        supportActionBar?.title = "Predict Your Tomato Plant"

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

        val name = "late_blight"
        val description =
            "Penyakit Late Blight adalah salah satu penyakit yang umum terjadi pada tanaman tomat. Tanaman yang terinfeksi penyakit ini akan mengalami kerusakan pada daun, batang, dan buah. Penyakit Late Blight disebabkan oleh jamur Phytophthora infestans. Gejala awal penyakit ini dapat dikenali dengan munculnya bercak cokelat pada daun, batang, dan buah tanaman. Bercak ini akan membesar dan berubah warna menjadi hitam pada daun, sedangkan pada buah akan muncul bercak berwarna cokelat kehitaman yang dapat merusak buah."
        val prevention =
            "Untuk mencegah penyakit Late Blight, Anda dapat melakukan langkah-langkah berikut:\n1. Gunakan benih yang sehat dan bebas dari penyakit.\n2. Pastikan tanah dan kondisi tumbuh lainnya optimal untuk pertumbuhan tanaman.\n3. Jaga kebersihan kebun dan pastikan tidak ada tumpukan dedaunan atau serpihan tanaman yang dapat menjadi tempat berkembang biak bagi jamur.\n4. Jaga kelembaban tanah dengan sistem irigasi yang tepat dan hindari kelembaban yang berlebihan.\n5. Gunakan fungisida organik yang tepat untuk mengendalikan penyakit ini.\n6. Jika terjadi infeksi, lakukan pemangkasan dan pembuangan bagian tanaman yang terinfeksi."

        val responsePredict = ResponsePredict(
            error = false,
            message = "",
            listPredict = listOf(
                Predict(name = name, description = description, prevention = prevention)
            )
        )

        displayPredictionResult(responsePredict)
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

    private fun displayPredictionResult(responsePredict: ResponsePredict) {
        if (responsePredict.error) {
            // Error occurred, show error message
            Toast.makeText(this, responsePredict.message, Toast.LENGTH_SHORT).show()
        } else {
            val listPredict = responsePredict.listPredict
            if (listPredict.isNullOrEmpty()) {
                Toast.makeText(this, "No prediction result", Toast.LENGTH_SHORT).show()
            } else {
                // Display prediction result
                val predict = listPredict[0]
                val intent = Intent(this, ResultPredictActivity::class.java)
                intent.putExtra("name", predict.name)
                intent.putExtra("description", predict.description)
                intent.putExtra("prevention", predict.prevention)
                startActivity(intent)
            }
        }
    }
}
