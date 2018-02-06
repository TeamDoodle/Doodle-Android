package com.real.doodle.Doodle_Books

/**
 * Created by SAMSUNG on 2018-01-12.
 */

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.fragment_book.view.*


class CarouselPagerAdapter(private val context: BookActivity, private val fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager), ViewPager.OnPageChangeListener {
    private var scale: Float = 0.toFloat()

    override fun getItem(position: Int): Fragment {
        var position = position
        // make the first pager bigger than others
        try {
            if (position == BookActivity.FIRST_PAGE)
                scale = BIG_SCALE
            else
                scale = SMALL_SCALE

            position = position % BookActivity.count

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ItemFragment.newInstance(context, position, scale)
    }

    override fun getCount(): Int {
        var count = 0
        try {
            count = BookActivity.count * BookActivity.LOOPS
        } catch (e: Exception) {
            // TODO: handle exception
            e.printStackTrace()
        }

        return count
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        try {
            if (positionOffset >= 0f && positionOffset <= 1f) {
                val cur = getRootView(position)
                val next = getRootView(position + 1)

                cur.setScaleBoth(BIG_SCALE - DIFF_SCALE * positionOffset)
                next.setScaleBoth(SMALL_SCALE + DIFF_SCALE * positionOffset)

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onPageSelected(position: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    private fun getRootView(position: Int): CarouselLinearLayout {
        return fragmentManager.findFragmentByTag(this.getFragmentTag(position))
                .view!!.root_container
    }

    private fun getFragmentTag(position: Int): String {
        return "android:switcher:" + context.pager.getId() + ":" + position
    }

    companion object {

        val BIG_SCALE = 1.0f
        val SMALL_SCALE = 0.65f
        val DIFF_SCALE = BIG_SCALE - SMALL_SCALE
    }
}