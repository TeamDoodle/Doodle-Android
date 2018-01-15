package com.doodle.doodle.Tutorial

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import com.doodle.doodle.Login.LoginActivity
import com.doodle.doodle.R
import com.doodle.doodle.Splash.SplashActivity
import com.rd.PageIndicatorView

class TutorialActivity : AppCompatActivity() {
    private var sharedPreference: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    var Position : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)



        var vp : ViewPager = findViewById(R.id.tutorial_viewpager)
        vp.adapter = pagerAdapter(supportFragmentManager)


        var pageIndicatorView : PageIndicatorView = findViewById(R.id.pageIndicatorView)


        //vp.adapter(pageIndicatorView)
        vp.setCurrentItem(0)

        pageIndicatorView.setViewPager(vp)
        pageIndicatorView.setDynamicCount(true)
        pageIndicatorView.setCount(6)

        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                pageIndicatorView.setSelection(position)
            }

        })




    }

    private class pagerAdapter(fm: android.support.v4.app.FragmentManager) :  FragmentStatePagerAdapter(fm){
        override fun getItem(position: Int): Fragment {
            when(position){
                0->{
                    var tutorialFragment : TutorialFragment ?= TutorialFragment()
                    val bundle:Bundle = Bundle()
                    bundle.putInt("flag", position)
                    tutorialFragment!!.arguments = bundle
                    return tutorialFragment
                }
                1->{
                    var tutorialFragment : TutorialFragment ?= TutorialFragment()
                    val bundle:Bundle = Bundle()
                    bundle.putInt("flag", position)
                    tutorialFragment!!.arguments = bundle
                    return tutorialFragment
                }
                2->{
                    var tutorialFragment : TutorialFragment ?= TutorialFragment()
                    val bundle:Bundle = Bundle()
                    bundle.putInt("flag", position)
                    tutorialFragment!!.arguments = bundle
                    return tutorialFragment
                }
                3->{
                    var tutorialFragment : TutorialFragment ?= TutorialFragment()
                    val bundle:Bundle = Bundle()
                    bundle.putInt("flag", position)
                    tutorialFragment!!.arguments = bundle
                    return tutorialFragment
                }
                4->{
                    var tutorialFragment : TutorialFragment ?= TutorialFragment()
                    val bundle:Bundle = Bundle()
                    bundle.putInt("flag", position)
                    tutorialFragment!!.arguments = bundle
                    return tutorialFragment
                }
                5->{
                    var tutorialFragment : TutorialFragment ?= TutorialFragment()
                    val bundle:Bundle = Bundle()
                    bundle.putInt("flag", 5)
                    tutorialFragment!!.arguments = bundle
                    return tutorialFragment
                }
            }
            return TutorialFragment()
        }

        override fun getCount(): Int {
            return 6
            //
        }
    }
}
private operator fun PagerAdapter.invoke(pageIndicatorView: PageIndicatorView) {}
