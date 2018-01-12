package com.doodle.doodle.Doodle_Books

/**
 * Created by SAMSUNG on 2018-01-12.
 */

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.doodle.doodle.R


class ItemFragment : Fragment() {

    private var screenWidth: Int = 0
    private var screenHeight: Int = 0

    private val imageArray = intArrayOf(R.drawable.book1_big, R.drawable.book2_big)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWidthAndHeight()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (container == null) {
            return null
        }

        val postion = this.arguments.getInt(POSITON)
        val scale = this.arguments.getFloat(SCALE)

        //val layoutParams = LinearLayout.LayoutParams(7*(screenWidth / 10), 5*(screenHeight / 10))
        val layoutParams = LinearLayout.LayoutParams(756, 1071 )
        val linearLayout = inflater!!.inflate(R.layout.fragment_book, container, false) as LinearLayout

        val textView = linearLayout.findViewById(R.id.text) as TextView
        val root = linearLayout.findViewById(R.id.root_container) as CarouselLinearLayout
        val imageView = linearLayout.findViewById(R.id.pagerImg) as ImageView

        //textView.text = "Carousel item: " + postion
        imageView.layoutParams = layoutParams
        imageView.setImageResource(imageArray[postion])

        //handling click event
        imageView.setOnClickListener {
            when(postion){
                0->{
                    val intent = Intent(activity, DoodleBookOneActivity::class.java)
                    //intent.putExtra(DRAWABLE_RESOURE, imageArray[postion])
                    startActivity(intent)
                }
                1->{
                    val intent = Intent(activity, DoodleBookTwoActivity::class.java)
                    //intent.putExtra(DRAWABLE_RESOURE, imageArray[postion])
                    startActivity(intent)
                }
            }
        }

        root.setScaleBoth(scale)

        return linearLayout
    }

    /**
     * Get device screen width and height
     */
    private fun getWidthAndHeight() {
        val displaymetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displaymetrics)
        screenHeight = displaymetrics.heightPixels
        screenWidth = displaymetrics.widthPixels
    }

    companion object {

        private val POSITON = "position"
        private val SCALE = "scale"
        private val DRAWABLE_RESOURE = "resource"

        fun newInstance(context: BookActivity, pos: Int, scale: Float): Fragment {
            val b = Bundle()
            b.putInt(POSITON, pos)
            b.putFloat(SCALE, scale)

            return Fragment.instantiate(context, ItemFragment::class.java.name, b)
        }
    }
}