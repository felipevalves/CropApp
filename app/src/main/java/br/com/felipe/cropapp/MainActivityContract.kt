package br.com.felipe.cropapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class MainActivityContract : AppCompatActivity() {

    private lateinit var myActivityResultLauncher: ActivityResultLauncher<Any?>

    private val myActivityResultContract = object : ActivityResultContract<Any?, Uri?>(){
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                .setAspectRatio(16, 9)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .getIntent(this@MainActivityContract)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {

            return CropImage.getActivityResult(intent)?.uri
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_contract)

        val btChooseImage = findViewById<Button>(R.id.btChooseImage)
        val imageView = findViewById<ImageView>(R.id.img)

        myActivityResultLauncher = registerForActivityResult(myActivityResultContract) {
            it?.let { uri -> imageView.setImageURI(uri) }
        }

        btChooseImage.setOnClickListener{
            myActivityResultLauncher.launch(null)
        }
    }


}