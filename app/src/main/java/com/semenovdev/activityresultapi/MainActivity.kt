package com.semenovdev.activityresultapi

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
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

        val contractUsername = ActivityResultContracts.StartActivityForResult()
        val launcherUsername = registerForActivityResult(contractUsername) {
            if (it.resultCode == RESULT_OK) {
                usernameTextView.text = it.data?.getStringExtra(UsernameActivity.EXTRA_USERNAME)
            }
        }

        val contractForImage = ActivityResultContracts.GetContent()
        val launcherImage = registerForActivityResult(contractForImage) {
            if (it != null) {
                imageFromGalleryImageView.setImageURI(it)
            }
        }


        getUsernameButton.setOnClickListener {
            launcherUsername.launch(UsernameActivity.newIntent(this))
        }

        getImageButton.setOnClickListener {
            launcherImage.launch("image/*")
        }
    }

    private fun initViews() {
        getUsernameButton = findViewById(R.id.get_username_button)
        usernameTextView = findViewById(R.id.username_textview)
        getImageButton = findViewById(R.id.get_image_button)
        imageFromGalleryImageView = findViewById(R.id.image_from_gallery_imageview)
    }
}