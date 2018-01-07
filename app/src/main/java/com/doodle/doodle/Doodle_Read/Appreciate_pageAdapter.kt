package com.doodle.doodle.Doodle_Read
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by SAMSUNG on 2018-01-05.
 */
class Appreciate_pageAdapter(fm: android.support.v4.app.FragmentManager) :  FragmentStatePagerAdapter(fm){
    val bundle=Bundle()
    val bundle1=Bundle()
    val bundle2=Bundle()
    var appreciateFragment: AppreciateFragment?=null
    override fun getItem(position: Int): Fragment {
        when(position){
            0->{
                bundle1.putInt("flag",-1)
                appreciateFragment= AppreciateFragment()
                appreciateFragment!!.arguments=bundle1
                return appreciateFragment as AppreciateFragment
            }
            1->{
                bundle2.putInt("flag",1)
                appreciateFragment= AppreciateFragment()
                appreciateFragment!!.arguments=bundle2
                return appreciateFragment as AppreciateFragment
            }
            2->{
                bundle.putInt("flag",2)
                return AppreciateDayFragment("day")
            }
        }
        return AppreciateFragment()
    }

    override fun getCount(): Int {
        return 3
    }
}