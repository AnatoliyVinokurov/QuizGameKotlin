package com.anatoliyvinokurov.quizgamekotlin

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class Level1 : AppCompatActivity() {

    var dialog: Dialog? = null
    var numLeft = 0
    var numRight = 0
    var powerLeft = 0
    var powerRight = 0
    val array = Array()
    var random = Random()
    var count = 0 // correct answer counter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_levels)

        //code to deploy the application to full screen
        val w = getWindow()
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        //Create a variable text_levels
        val text_levels = findViewById<TextView>(R.id.text_levels)
        text_levels.setText(R.string.level1)

        val img_left = findViewById<ImageView>(R.id.img_left)
        val img_right = findViewById<ImageView>(R.id.img_right)
        //code for rounding corners
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            img_left.clipToOutline = true
            img_right.clipToOutline = true
        }

        // Path to onscreen TextView
        val text_left = findViewById<TextView>(R.id.text_left)
        val text_right = findViewById<TextView>(R.id.text_right)

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
        try {
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
                } catch (e:Exception) {
                }


        //Animation
        val a = AnimationUtils.loadAnimation(this@Level1, R.anim.alpha)

        //code to insert images from Array to screen
        numLeft = random.nextInt(15) // Generate a random number from 0 to 14
        img_left.setImageResource(array.images1[numLeft]) //Get from the array images 1 picture under the number generated randomly numLeft
        text_left.setText(array.texts1[numLeft]) //Get from array texts1 the inscription under the number generated randomly by numLeft
        powerLeft = array.power1[numLeft] ////Get from the array power 1 power value under the number generated randomly numLeft

        numRight = random.nextInt(15) // Generate a random number from 0 to 14
        // a loop with a precondition that checks the equality of numbers
        while (numLeft == numRight) {
            numRight = random.nextInt(15)
        }
        img_right.setImageResource(array.images1[numRight])
        text_right.setText(array.texts1[numRight])
        powerRight = array.power1[numRight]

        // process clicking on the left picture
        img_left.setOnTouchListener { v, event ->
            //if touched pictures
            if (event.action == MotionEvent.ACTION_DOWN) { //touch
                img_right.isEnabled = false //when touched to the left of the picture, need to lock the clicking the right
                //checking the correct answer
                if (powerLeft > powerRight) {
                    img_left.setImageResource(R.drawable.img_true)
                } else {
                    img_left.setImageResource(R.drawable.img_false)
                }
            } else if (event.action == MotionEvent.ACTION_UP) { //untouched
                img_right.isEnabled = true //when unpressed with the finger of the left picture, need to unlock, press the right
        }
            true
        }

        // process clicking on the right picture
        img_right.setOnTouchListener { v, event ->
            //if touched pictures
            if (event.action == MotionEvent.ACTION_DOWN) { //touch
                img_left.isEnabled = false //when touched to the right of the picture, need to lock the clicking the left
                //checking the correct answer
                if (powerRight > powerLeft) {
                    img_right.setImageResource(R.drawable.img_true)
                } else {
                    img_right.setImageResource(R.drawable.img_false)
                }
            } else if (event.action == MotionEvent.ACTION_UP) { //untouched
                img_right.isEnabled = true //when unpressed with the finger of the right picture, need to unlock, press the left
            }
            true
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
