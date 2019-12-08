package com.anatoliyvinokurov.quizgamekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button

class GameLevels : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gamelevels)

        //code to deploy the application to full screen
        val w = getWindow()
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // code for on-screen Back button"
        val buttonStart = findViewById<Button>(R.id.button_back)
        buttonStart.setOnClickListener {
            try {
                val intent = Intent(this@GameLevels, MainActivity::class.java)
                startActivity(intent)
                finish()
            } catch (e:Exception) {
            }
        }

    }

    // code for system back button
    override fun onBackPressed() {
        try {
            val intent = Intent(this@GameLevels, MainActivity::class.java)
            startActivity(intent)
            finish()
        } catch (e:Exception) {
        }
    }
}
