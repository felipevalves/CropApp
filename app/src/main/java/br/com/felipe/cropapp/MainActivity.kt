package br.com.felipe.cropapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.util.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById<ImageView>(R.id.img)
        val btChooseImage = findViewById<Button>(R.id.btChooseImage)

        btChooseImage.setOnClickListener{
            CropImage.startPickImageActivity(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "Result code is not OK $resultCode", Toast.LENGTH_LONG).show()
            return
        }

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE) {
            val cropUri = CropImage.getPickImageResultUri(this, data)

            if (CropImage.isReadExternalStoragePermissionsRequired(this, cropUri)) {
                imageUri = cropUri
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 0)
            }
            else {
                startCrop(cropUri)
            }
        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            imageView.setImageURI(result.uri)
        }
    }

    private fun startCrop(cropUri: Uri?) {
        //cropUri?.let { uri -> imageView.setImageURI(uri) }
        CropImage.activity(cropUri)
            .setCropShape(CropImageView.CropShape.RECTANGLE)
            .setGuidelines(CropImageView.Guidelines.ON)
            .setMultiTouchEnabled(true)
            .start(this)
    }

}