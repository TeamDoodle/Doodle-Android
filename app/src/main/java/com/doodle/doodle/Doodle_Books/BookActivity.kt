package com.doodle.doodle.Doodle_Books

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.doodle.doodle.R


class BookActivity : AppCompatActivity() {

    lateinit var adapter: CarouselPagerAdapter
    lateinit var pager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        val s = findViewById<Spinner>(R.id.book_spinner)
        s.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View,
                                        position: Int, id: Long) {
//                tv.setText("position : " + position +
//                        parent.getItemAtPosition(position))
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        pager = findViewById(R.id.myviewpager) as ViewPager

        //set page margin between pages for viewpager
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val pageMargin = metrics.widthPixels / 3 * 1
        pager.pageMargin = -pageMargin



        adapter = CarouselPagerAdapter(this, supportFragmentManager)
        pager.adapter = adapter
        adapter.notifyDataSetChanged()

        pager.addOnPageChangeListener(adapter)

        // Set current item to the middle page so we can fling to both
        // directions left and right
        pager.currentItem = FIRST_PAGE
        pager.offscreenPageLimit = 3





    }


    companion object {
        val LOOPS = 1
        var count = 2 //ViewPager items size
        /**
         * You shouldn't define first page = 0.
         * Let define firstpage = 'number viewpager size' to make endless carousel
         */
        var FIRST_PAGE = 0
    }
}