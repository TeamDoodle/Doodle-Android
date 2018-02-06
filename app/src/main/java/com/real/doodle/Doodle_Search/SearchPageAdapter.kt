package com.real.doodle.Doodle_Search

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log

/**
 * Created by Dayoung on 2018-01-09.
 */
class SearchPageAdapter(fm : FragmentManager) :FragmentPagerAdapter(fm){
    var searchNameFragment : SearchNameFragment ? =null
    override fun getItem(position: Int): Fragment {
        searchNameFragment= SearchNameFragment()
        return when (position) {
            0 -> {
//                searchNameFragment.setTag
                Log.i("fat1",searchNameFragment.toString())
                return searchNameFragment as SearchNameFragment
            }
            1 -> {
                SearchWordingFragment()
            }

            else -> SearchNameFragment()
        }

    }
    fun getFragment(): SearchNameFragment? {
        return searchNameFragment
    }

    override fun getCount(): Int {
        return 2
    }

}