package com.doodle.doodle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by SAMSUNG on 2018-01-05.
 */
class Appreciate_pageAdapter(fm: android.support.v4.app.FragmentManager) :  FragmentStatePagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        when(position){
            0->{
                return AppreciateAllFragment()
            }
            1->{
                return AppreciateWeekFragment()
            }
            2->{
                return AppreciateDayFragment("day")
            }
        }
        return AppreciateAllFragment()
    }

    override fun getCount(): Int {
        return 3
    }
}