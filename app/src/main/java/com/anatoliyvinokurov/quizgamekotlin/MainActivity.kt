package com.anatoliyvinokurov.quizgamekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.viewpager.widget.ViewPager
import kotlin.Array

class MainActivity : AppCompatActivity() {

    private var backPressedTime:Long = 0
    private lateinit var backToast:Toast

    private var slideViewPager: ViewPager? = null
    private var dotsLayout: LinearLayout? = null
    private var sliderAdapter: SliderAdapter? = null
    private var dots: Array<TextView?> = arrayOfNulls(3)
    private var main_background: ImageView? = null
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //code to deploy the application to full screen
        val w = getWindow()
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        slideViewPager = findViewById<View>(R.id.slideViewPager) as ViewPager
        dotsLayout = findViewById<View>(R.id.dotsLayout) as LinearLayout

        main_background = findViewById<ImageView>(R.id.main_background) as ImageView

        sliderAdapter = SliderAdapter(this)
        slideViewPager!!.setAdapter(sliderAdapter)

        addDotsIndicator(0)
        slideViewPager!!.addOnPageChangeListener(viewListener)

        /*
            slideViewPager!!.setCurrentItem(0) //set starting position of slider_page
         */

        // code for on-screen Start button
        val buttonStart = findViewById<Button>(R.id.button_start)
        buttonStart.setOnClickListener {
            when (currentPage) {
                0 -> {
                    try {
                        val intent = Intent(this@MainActivity, GameLevel1::class.java)
                        startActivity(intent)
                        finish()
                    } catch (e:Exception) {
                    }
                }
                1 -> {
                    try {
                        val intent = Intent(this@MainActivity, GameLevel2::class.java)
                        startActivity(intent)
                        finish()
                    } catch (e:Exception) {
                    }
                }
                2 -> {
                    try {
                        val intent = Intent(this@MainActivity, GameLevel3::class.java)
                        startActivity(intent)
                        finish()
                    } catch (e:Exception) {
                    }
                }
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
            backToast = Toast.makeText(getBaseContext(), R.string.exetpress, Toast.LENGTH_SHORT)
            backToast.show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    //function for coloring points according to the selected page-slides
    fun addDotsIndicator(position: Int) {
        dots = arrayOfNulls<TextView>(3)
        dotsLayout!!.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]!!.setText(Html.fromHtml("&#8226;"))
            dots[i]!!.setTextSize(35f)
            dots[i]!!.setTextColor(resources.getColor(R.color.colorTransparentWhite))
            dotsLayout!!.addView(dots[i]!!)
        }
        if (dots.size > 0) {
            dots[position]!!.setTextColor(resources.getColor(R.color.colorWhite))
        }
    }

    //function for setting a page-slides of OnPageChangeListener
    var viewListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {

        //the override function that is called when browsing the slides
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        //the override function that is called when browsing the slides
        override fun onPageSelected(position: Int) {
            addDotsIndicator(position)
            currentPage = position
            when (position) {
                0 -> {
                    main_background!!.setImageResource(R.drawable.main_background_level1)
                }
                1 -> {
                    main_background!!.setImageResource(R.drawable.main_background_level2)
                }
                2 -> {
                    main_background!!.setImageResource(R.drawable.main_background_level3)
                }
            }
        }

        //the override function that is called when browsing the slides
        override fun onPageScrollStateChanged(state: Int) {}
    }
}
