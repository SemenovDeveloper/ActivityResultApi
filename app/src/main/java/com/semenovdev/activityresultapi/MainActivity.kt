package com.semenovdev.activityresultapi

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var getUsernameButton: Button
    private lateinit var usernameTextView: TextView
    private lateinit var getImageButton: Button
    private lateinit var imageFromGalleryImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

        val contractUsername = object : ActivityResultContract<Intent, String?>() {
            override fun createIntent(context: Context, input: Intent): Intent {
                return input
            }

            override fun parseResult(resultCode: Int, intent: Intent?): String? {
                if (resultCode == RESULT_OK) {
                    return intent?.getStringExtra(UsernameActivity.EXTRA_USERNAME)
                }
                 return null
            }
        }

        val contractForImage = object : ActivityResultContract<Intent, Uri?>() {
            override fun createIntent(context: Context, input: Intent): Intent {
                return input
            }

            override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
                if (resultCode == RESULT_OK) {
                    return intent?.data
                }
                return null
            }
        }

        val launcherUsername = registerForActivityResult(contractUsername) {
            if (!it.isNullOrBlank()) {
                usernameTextView.text = it
            }
        }

        val launcherImage = registerForActivityResult(contractForImage) {
            if (it != null) {
                imageFromGalleryImageView.setImageURI(it)
            }
        }


        getUsernameButton.setOnClickListener {
            launcherUsername.launch(UsernameActivity.newIntent(this))
        }

        getImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }

            launcherImage.launch(intent)
        }
    }

    private fun initViews() {
        getUsernameButton = findViewById(R.id.get_username_button)
        usernameTextView = findViewById(R.id.username_textview)
        getImageButton = findViewById(R.id.get_image_button)
        imageFromGalleryImageView = findViewById(R.id.image_from_gallery_imageview)
    }
}