package com.anatoliyvinokurov.quizgamekotlin

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class Level1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_levels)

        //code to deploy the application to full screen
        val w = getWindow()
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)


        val img_left = findViewById<ImageView>(R.id.img_left)
        val img_right = findViewById<ImageView>(R.id.img_right)
        //code for rounding corners
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            img_left.clipToOutline = true
            img_right.clipToOutline = true
        }

        // code for on-screen Back button"
        val buttonStart = findViewById<Button>(R.id.button_back)
        buttonStart.setOnClickListener {
            try {
                val intent = Intent(this@Level1, GameLevels::class.java)
                startActivity(intent)
                finish()
            } catch (e:Exception) {
            }
        }
    }
    // code for system back button
    override fun onBackPressed() {
        try {
            val intent = Intent(this@Level1, GameLevels::class.java)
            startActivity(intent)
            finish()
        } catch (e:Exception) {
        }
    }
}
