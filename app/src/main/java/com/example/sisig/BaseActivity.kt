package com.example.yourapp

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.sisig.R

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    // Method to set/change avatar image
    fun setAvatarImage(resId: Int) {
        val avatarImage = findViewById<ImageView>(R.id.avatar_image)
        avatarImage?.setImageResource(resId)
    }
}
