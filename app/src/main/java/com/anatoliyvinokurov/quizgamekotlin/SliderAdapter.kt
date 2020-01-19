package com.anatoliyvinokurov.quizgamekotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter


class SliderAdapter(var context: Context) : PagerAdapter() {
    var layoutInflater: LayoutInflater? = null

    //Array
    var slide_images = intArrayOf(
        R.drawable.main_img_giraffe,
        R.drawable.main_img_kangaroo,
        R.drawable.main_img_cheetah
    )

    override fun getCount(): Int {
        return slide_images.size
    }

    override fun isViewFromObject(
        view: View,
        `object`: Any
    ): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view =
            layoutInflater!!.inflate(R.layout.slide_layout, container, false)
        val slideImage =
            view.findViewById<View>(R.id.slideImage) as ImageView
        slideImage.setImageResource(slide_images[position])
        container.addView(view)
        return view
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        container.removeView(`object` as RelativeLayout)
    }

}