package com.doodle.doodle.Doodle_Me

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by Jinyoung on 2018-01-06.
 */
class Myfeed_pageAdapter(fm:android.support.v4.app.FragmentManager):FragmentStatePagerAdapter(fm) {
    val bundle1=Bundle()
    val bundle2=Bundle()
    var gatherFragment:GatherFragment?=null
    override fun getItem(position: Int): Fragment {
            when(position) {
                0 -> {
                    bundle1.putInt("flag", 3)
                    gatherFragment = GatherFragment()
                    gatherFragment!!.arguments = bundle1
                    return gatherFragment as GatherFragment
                }
                1 -> {
                    bundle2.putInt("flag", 4)
                    gatherFragment= GatherFragment()
                    gatherFragment!!.arguments = bundle2
                    return gatherFragment as GatherFragment
                }
            }

                   return GatherFragment()
    }

    override fun getCount(): Int {
        return 2
    }
}