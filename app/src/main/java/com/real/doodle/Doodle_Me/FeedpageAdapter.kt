package com.real.doodle.Doodle_Me

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by Jinyoung on 2018-01-06.
 */
class FeedpageAdapter(fm:android.support.v4.app.FragmentManager):FragmentStatePagerAdapter(fm) {
    val bundle1=Bundle()
    val bundle2=Bundle()
    var feedFragment:FeedFragment?=null
    override fun getItem(position: Int): Fragment {
            when(position) {
                0 -> {
                    bundle1.putInt("flag", 3)
                    feedFragment = FeedFragment()
                    feedFragment!!.arguments = bundle1
                    return feedFragment as FeedFragment
                }
                1-> {
                    bundle2.putInt("flag", 4)
                        feedFragment= FeedFragment()
                        feedFragment!!.arguments = bundle2
                    return feedFragment as FeedFragment
                }
            }

                   return FeedFragment()
    }

    override fun getCount(): Int {
        return 2
    }
}