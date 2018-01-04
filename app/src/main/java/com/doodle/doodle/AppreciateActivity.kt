package com.doodle.doodle

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_appreciate.*

class AppreciateActivity  : AppCompatActivity() , View.OnClickListener {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appreciate)

        all.setOnClickListener(this)
        week.setOnClickListener(this)
        day.setOnClickListener(this)

        appreciate_viewPager.adapter = Appreciate_pageAdapter(supportFragmentManager)
        appreciate_viewPager.setCurrentItem(0)

       // val bundle = Bundle()
       // ReplaceFragment(AppreciateAllFragment(),bundle,"first")
    }

    override fun onClick(v: View?) {
        when(v){    //뷰를 기준으로 구분
            all->{
                //val bundle = Bundle()
                //bundle.putString("title",myfeed_gatherFeed!!.text.toString())
//                AddFragment(FirstFragmㅇent(),bundle,"first"
//                        ,supportFragmentManager.findFragmentById(R.id.main_container))
                //myfeed_title.text = "나의 글"
                //ReplaceFragment(AppreciateAllFragment(),bundle,"first")
                appreciate_viewPager.setCurrentItem(0)
            }
            week->{
                //val bundle = Bundle()
                //bundle.putString("title",myfeed_scrapFeed!!.text.toString())
//                AddFragment(FirstFragment(),bundle,"first"
//                        ,supportFragmentManager.findFragmentById(R.id.main_container))
                //myfeed_title.text = "담은 글"

                //ReplaceFragment(AppreciateWeekFragment(),bundle,"first")
                appreciate_viewPager.setCurrentItem(1)
            }
            day->{
                val bundle = Bundle()
                //bundle.putString("title",myfeed_scrapFeed!!.text.toString())
//                AddFragment(FirstFragment(),bundle,"first"
//                        ,supportFragmentManager.findFragmentById(R.id.main_container))
                //myfeed_title.text = "담은 글"

                bundle.putString("page", "day")
                appreciate_viewPager.setCurrentItem(2)
                //ReplaceFragment(AppreciateDayFragment(),bundle,"first")
            }
        }

    }
    fun AddFragment(fragment: Fragment, bundle: Bundle, tag:String){    //fragment2를 fragment로 대체하는 부분
        val fm=supportFragmentManager
        val transaction=fm.beginTransaction()
        fragment.arguments = bundle
        //transaction.add(R.id.appreciate_viewPager,fragment,tag)
        transaction.commit()

    }

    fun ReplaceFragment(fragment: Fragment, bundle: Bundle, tag:String){    //fragment2를 fragment로 대체하는 부분
        val fm=supportFragmentManager
        val transaction=fm.beginTransaction()
        fragment.arguments = bundle
       // transaction.replace(R.id.appreciate_viewPager,fragment,tag)
//        transaction.addToBackStack(null)
        transaction.commit()

    }
}
