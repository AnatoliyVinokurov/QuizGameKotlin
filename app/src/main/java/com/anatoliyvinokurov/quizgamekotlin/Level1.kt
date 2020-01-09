package com.anatoliyvinokurov.quizgamekotlin

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import android.widget.LinearLayout
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
    var levels = IntArray(30) // created an array containing answers (correct/incorrect/ and unanswered)
    var starsLevelOne = 0

    private lateinit var sPref: SharedPreferences
    var str = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_levels)

        //code to deploy the application to full screen
        val w = getWindow()
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        //assign a value to the variable count from the Internet using the key count
        count = intent.getIntExtra("count", 0) // if there is nothing on the count key, then i=0, otherwise i=value

        //loading the levels array from the save
        loadAnArrayWithLevels()

        //count the number of stars in the first level
        countNumberOfStarsInOneLevel()

        //Create a variable text_levels
        val text_levels = findViewById<TextView>(R.id.text_levels)
        text_levels.setText(R.string.level1)

        //set the background image for the first level
        val background = findViewById<ImageView>(R.id.background)
        background.setImageResource(R.drawable.level1)

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

            //put the image
            val previewimg = dialog!!.findViewById<ImageView>(R.id.previewimg)
            previewimg.setImageResource(R.drawable.preview_img_one)
            //put the text
            val textdescription = dialog!!.findViewById<View>(R.id.textdescription) as TextView
            textdescription.setText(R.string.levelone)
            //put the background image
            val dialogfom = dialog!!.findViewById<LinearLayout>(R.id.dialogfom)
            dialogfom.setBackgroundResource(R.drawable.preview_background_one)

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


        //An array of elements to display the progress of the game
        val progress = intArrayOf(
            R.id.point1, R.id.point2, R.id.point3, R.id.point4, R.id.point5,
            R.id.point6, R.id.point7, R.id.point8, R.id.point9, R.id.point10,
            R.id.point11, R.id.point12, R.id.point13, R.id.point14, R.id.point15,
            R.id.point16, R.id.point17, R.id.point18, R.id.point19, R.id.point20,
            R.id.point21, R.id.point22, R.id.point23, R.id.point24, R.id.point25,
            R.id.point26, R.id.point27, R.id.point28, R.id.point29, R.id.point30
        )

        //Animation
        val a = AnimationUtils.loadAnimation(this@Level1, R.anim.alpha)

        //code to insert images from Array to screen
        assignedToThePicturesOfThePlace()//method for placing picture numbers
        img_left.setImageResource(array.images1[numLeft]) //Get from the array images 1 picture under the number generated randomly numLeft
        text_left.setText(array.texts1[numLeft]) //Get from array texts1 the inscription under the number generated randomly by numLeft
        powerLeft = array.power1[numLeft] ////Get from the array power 1 power value under the number generated randomly numLeft

        img_right.setImageResource(array.images1[numRight])
        text_right.setText(array.texts1[numRight])
        powerRight = array.power1[numRight]

        //Paint the elements from the progress array
        for (i in 0..29) {
            when {
                levels[i] == 0 -> { //if the level I-th element in the array is zero ...
                    val tv = findViewById<TextView>(progress[i])
                    tv.setBackgroundResource(R.drawable.style_points)//...then we paint the i-th element in gray
                }
                levels[i] == 1 -> {
                    val tv = findViewById<TextView>(progress[i])
                    tv.setBackgroundResource(R.drawable.style_points_gold)//otherwise-in gold
                }
                levels[i] == -1 -> {
                    val tv = findViewById<TextView>(progress[i])
                    tv.setBackgroundResource(R.drawable.style_points_grey)//otherwise - in grey
                }
            }
        }

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
                // Check the answer for correctness
                if (powerLeft > powerRight) { //if the correct answer was given, then
                    // Paint an element from the progress array gold
                    val tv = findViewById<TextView>(progress[count])
                    tv.setBackgroundResource(R.drawable.style_points_gold)
                    levels[count] = 1 // write one to the level array
                } else { //if an incorrect answer was given, then
                    // Paint an element from the progress array in grey
                    val tv = findViewById<TextView>(progress[count])
                    tv.setBackgroundResource(R.drawable.style_points_grey)
                    levels[count] = -1 // write minus one to the level array
                }


                if (count == 29) { ////if this is the last level, then
                    // Output
                    countNumberOfStarsInOneLevel()
                    if (starsLevelOne >= 15){
                        //call the dialog box at the end of the game
                        try {
                            dialog = Dialog(this)//created a new dialog box
                            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE) //hide dialog box title
                            dialog!!.setContentView(R.layout.enddialog) //the path to the dialog box layout
                            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //transparent dialog box background
                            dialog!!.setCancelable(false) // disallow closing the dialog box with the back button

                            //put the text
                            val textdescription = dialog!!.findViewById<View>(R.id.textdescriptionEnd) as TextView
                            textdescription.setText(R.string.levelonefinishtrue)
                            //put the background image
                            val dialogfom = dialog!!.findViewById<LinearLayout>(R.id.dialogfom)
                            dialogfom.setBackgroundResource(R.drawable.preview_background_one)

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
                                    val intent = Intent(this@Level1, GameLevels::class.java)
                                    startActivity(intent)
                                    finish()
                                } catch (e:Exception) {
                                }
                                dialog!!.dismiss() // closes the dialog box
                            }

                            dialog!!.show()//show dialog box
                        } catch (e:Exception) {
                        }
                    } else {
                        //call the dialog box at the end of the game
                        try {
                            dialog = Dialog(this)//created a new dialog box
                            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE) //hide dialog box title
                            dialog!!.setContentView(R.layout.enddialog) //the path to the dialog box layout
                            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //transparent dialog box background
                            dialog!!.setCancelable(false) // disallow closing the dialog box with the back button

                            //put the text
                            val textdescription = dialog!!.findViewById<View>(R.id.textdescriptionEnd) as TextView
                            textdescription.setText(R.string.levelonefinishfalse)
                            //put the background image
                            val dialogfom = dialog!!.findViewById<LinearLayout>(R.id.dialogfom)
                            dialogfom.setBackgroundResource(R.drawable.preview_background_one)
                            val btncontinue = dialog!!.findViewById<View>(R.id.btncontinue) as Button
                            btncontinue.setText(R.string.textstartover)


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
                            btncontinue.setOnClickListener {
                                try {
                                    val intent = Intent(this@Level1, GameLevels::class.java)
                                    startActivity(intent)
                                    finish()
                                } catch (e:Exception) {
                                }
                                dialog!!.dismiss() // closes the dialog box
                            }

                            dialog!!.show()//show dialog box
                        } catch (e:Exception) {
                        }
                    }

                } else {
                    count++ // increase points
                    // put pictures
                    assignedToThePicturesOfThePlace()
                    img_left.setImageResource(array.images1[numLeft])
                    img_left.startAnimation(a) //start animation
                    text_left.setText(array.texts1[numLeft])
                    powerLeft = array.power1[numLeft]
                    img_right.setImageResource(array.images1[numRight])
                    img_right.startAnimation(a) //start animation
                    text_right.setText(array.texts1[numRight])
                    powerRight = array.power1[numRight]
                }
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
                // Check the answer for correctness
                if (powerRight > powerLeft) { //if the correct answer was given, then
                    // Paint an element from the progress array gold
                    val tv = findViewById<TextView>(progress[count])
                    tv.setBackgroundResource(R.drawable.style_points_gold)
                    levels[count] = 1 // write one to the level array
                } else { //if an incorrect answer was given, then
                    // Paint an element from the progress array in grey
                    val tv = findViewById<TextView>(progress[count])
                    tv.setBackgroundResource(R.drawable.style_points_grey)
                    levels[count] = -1 // write minus one to the level array
                }


                if (count == 29) { ////if this is the last level, then
                    // Output
                    // Output
                    countNumberOfStarsInOneLevel()
                    if (starsLevelOne >= 15){
                        //call the dialog box at the end of the game
                        try {
                            dialog = Dialog(this)//created a new dialog box
                            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE) //hide dialog box title
                            dialog!!.setContentView(R.layout.enddialog) //the path to the dialog box layout
                            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //transparent dialog box background
                            dialog!!.setCancelable(false) // disallow closing the dialog box with the back button

                            //put the text
                            val textdescription = dialog!!.findViewById<View>(R.id.textdescriptionEnd) as TextView
                            textdescription.setText(R.string.levelonefinishtrue)
                            //put the background image
                            val dialogfom = dialog!!.findViewById<LinearLayout>(R.id.dialogfom)
                            dialogfom.setBackgroundResource(R.drawable.preview_background_one)

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
                                    val intent = Intent(this@Level1, GameLevels::class.java)
                                    startActivity(intent)
                                    finish()
                                } catch (e:Exception) {
                                }
                                dialog!!.dismiss() // closes the dialog box
                            }

                            dialog!!.show()//show dialog box
                        } catch (e:Exception) {
                        }
                    } else {
                        //call the dialog box at the end of the game
                        try {
                            dialog = Dialog(this)//created a new dialog box
                            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE) //hide dialog box title
                            dialog!!.setContentView(R.layout.enddialog) //the path to the dialog box layout
                            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //transparent dialog box background
                            dialog!!.setCancelable(false) // disallow closing the dialog box with the back button

                            //put the text
                            val textdescription = dialog!!.findViewById<View>(R.id.textdescriptionEnd) as TextView
                            textdescription.setText(R.string.levelonefinishfalse)
                            //put the background image
                            val dialogfom = dialog!!.findViewById<LinearLayout>(R.id.dialogfom)
                            dialogfom.setBackgroundResource(R.drawable.preview_background_one)
                            val btncontinue = dialog!!.findViewById<View>(R.id.btncontinue) as Button
                            btncontinue.setText(R.string.textstartover)


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
                            btncontinue.setOnClickListener {
                                try {
                                    val intent = Intent(this@Level1, GameLevels::class.java)
                                    startActivity(intent)
                                    finish()
                                } catch (e:Exception) {
                                }
                                dialog!!.dismiss() // closes the dialog box
                            }

                            dialog!!.show()//show dialog box
                        } catch (e:Exception) {
                        }
                    }

                } else {
                    count++ // increase points
                    // put pictures
                    assignedToThePicturesOfThePlace()
                    img_left.setImageResource(array.images1[numLeft])
                    img_left.startAnimation(a) //start animation
                    text_left.setText(array.texts1[numLeft])
                    powerLeft = array.power1[numLeft]
                    img_right.setImageResource(array.images1[numRight])
                    img_right.startAnimation(a) //start animation
                    text_right.setText(array.texts1[numRight])
                    powerRight = array.power1[numRight]
                }
                img_left.isEnabled = true //when unpressed with the finger of the right picture, need to unlock, press the left
            }
            true
        }

    }

    ////method for placing picture numbers
    private fun assignedToThePicturesOfThePlace() {
        when (count) {
            0 -> {
                numLeft = 1
                numRight = 12
            }
            1 -> {
                numLeft = 10
                numRight = 2
            }
            2 -> {
                numLeft = 0
                numRight = 13
            }
            3 -> {
                numLeft = 3
                numRight = 12
            }
            4 -> {
                numLeft = 7
                numRight = 1
            }
            5 -> {
                numLeft = 10
                numRight = 7
            }
            6 -> {
                numLeft = 5
                numRight = 2
            }
            7 -> {
                numLeft = 6
                numRight = 10
            }
            8 -> {
                numLeft = 13
                numRight = 8
            }
            9 -> {
                numLeft = 14
                numRight = 1
            }
            10 -> {
                numLeft = 12
                numRight = 14
            }
            11 -> {
                numLeft = 12
                numRight = 11
            }
            12 -> {
                numLeft = 4
                numRight = 2
            }
            13 -> {
                numLeft = 4
                numRight = 6
            }
            14 -> {
                numLeft = 6
                numRight = 7
            }
            15 -> {
                numLeft = 1
                numRight = 6
            }
            16 -> {
                numLeft = 7
                numRight = 12
            }
            17 -> {
                numLeft = 8
                numRight = 4
            }
            18 -> {
                numLeft = 9
                numRight = 5
            }
            19 -> {
                numLeft = 14
                numRight = 10
            }
            20 -> {
                numLeft = 4
                numRight = 7
            }
            21 -> {
                numLeft = 5
                numRight = 2
            }
            22 -> {
                numLeft = 3
                numRight = 13
            }
            23 -> {
                numLeft = 11
                numRight = 3
            }
            24 -> {
                numLeft = 3
                numRight = 1
            }
            25 -> {
                numLeft = 12
                numRight = 8
            }
            26 -> {
                numLeft = 9
                numRight = 7
            }
            27 -> {
                numLeft = 11
                numRight = 9
            }
            28 -> {
                numLeft = 9
                numRight = 12
            }
            29 -> {
                numLeft = 13
                numRight = 14
            }
            else -> {
                numLeft = random.nextInt(15)
                numRight = random.nextInt(15)
                // a loop with a precondition that checks the equality of numbers
                while (numLeft == numRight) {
                    numRight = random.nextInt(15)
                }
            }
        }
    }


    //load the levels array from the save
    private fun loadAnArrayWithLevels() {
        try {
            sPref = getSharedPreferences("dsd", Context.MODE_PRIVATE)
            var savedString: String = sPref.getString("level_one", "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0").toString()
            val st = StringTokenizer(savedString, ",")
            for (i in 0..29) {
                levels[i] = st.nextToken().toInt()
            }
        } catch (e:Exception) {
        }
    }
    //save the levels array to save
    private fun saveAnArrayWithLevels() {
        sPref = getSharedPreferences("dsd", Context.MODE_PRIVATE)
        for (i in levels.indices) {
            str.append(levels[i]).append(",")
        }
        val ed = sPref.edit()
        ed.putString("level_one", str.toString())
        ed.commit()
    }

    //count the number of stars in the first level
    private fun countNumberOfStarsInOneLevel() {
        //counting the number of stars
        starsLevelOne = 0
        for (i in 0..29) {
            if (levels[i] == 1) {
                starsLevelOne++
            }
        }
        //save the value of the number of stars
        sPref = getSharedPreferences("dsd", Context.MODE_PRIVATE)
        val ed = sPref.edit()
        ed.putInt("StarsInOneLevel", starsLevelOne)
        ed.commit()
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

    override fun onResume() {
        super.onResume()
        countNumberOfStarsInOneLevel()
    }

    override fun onPause() {
        super.onPause()
        saveAnArrayWithLevels()
        countNumberOfStarsInOneLevel()
    }

    override fun onStop() {
        super.onStop()
        saveAnArrayWithLevels()
        countNumberOfStarsInOneLevel()
    }

    //code that is triggered when the form is destroyed/closed
    override fun onDestroy() {
        super.onDestroy()
        saveAnArrayWithLevels()
        countNumberOfStarsInOneLevel()
    }


}
