package com.anatoliyvinokurov.quizgamekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var backPressedTime:Long = 0
    private lateinit var backToast:Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //code to deploy the application to full screen
        val w = getWindow()
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // code for on-screen Start button
        val buttonStart = findViewById<Button>(R.id.button_start)
        buttonStart.setOnClickListener {
            try {
                val intent = Intent(this@MainActivity, GameLevels::class.java)
                startActivity(intent)
                finish()
            } catch (e:Exception) {
            }
        }


    }
    // code for system back button
    override fun onBackPressed() {
        //if the current time is + 2 seconds longer than the current time
        if (backPressedTime + 2000 > System.currentTimeMillis())
        {
            backToast.cancel() //command to close the pop-up message
            super.onBackPressed() //the team that closes the game
            return
        }
        else
        {
            backToast = Toast.makeText(getBaseContext(), "Нажмите еще раз, чтобы выйти", Toast.LENGTH_SHORT)
            backToast.show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}
