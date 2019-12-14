package com.anatoliyvinokurov.quizgamekotlin

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Level1 : AppCompatActivity() {

    var dialog: Dialog? = null

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

        //code for on-screen Back button"
        val buttonStart = findViewById<Button>(R.id.button_back)
        buttonStart.setOnClickListener {
            try {
                val intent = Intent(this@Level1, GameLevels::class.java)
                startActivity(intent)
                finish()
            } catch (e:Exception) {
            }
        }

        //call the dialog box at the beginning of the game
        dialog = Dialog(this)//created a new dialog box
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE) //hide dialog box title
        dialog!!.setContentView(R.layout.previewdialog) //the path to the dialog box layout
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //transparent dialog box background
        dialog!!.setCancelable(false) // disallow closing the dialog box with the back button
        // button to close the dialog box
        val btnclose = dialog!!.findViewById<View>(R.id.btncloce) as TextView
        btnclose.setOnClickListener {
            try {
                val intent = Intent(this@Level1, GameLevels::class.java)
                startActivity(intent)
                finish()
            } catch (e:Exception) {
            }
            dialog!!.dismiss() // closes the dialog box
        }
        // continue button
        val btncontinue = dialog!!.findViewById<View>(R.id.btncontinue) as Button
        btncontinue.setOnClickListener {
            try {
                dialog!!.dismiss() //closes the dialog box
            } catch (e:Exception){
            }
        }

        dialog!!.show()//show dialog box


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
