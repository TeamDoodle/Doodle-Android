package com.doodle.doodle.Doodle_Me

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.doodle.doodle.R
import kotlinx.android.synthetic.main.activity_myfeed.*

class MyfeedActivity : AppCompatActivity() ,View.OnClickListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myfeed)

        myfeed_gatherFeed.setOnClickListener(this)
        myfeed_scrapFeed.setOnClickListener(this)

        val bundle = Bundle()
//        모아보기
        ReplaceFragment(GatherFragment(),bundle,"first")
    }

    override fun onClick(v: View?) {
        when(v){    //뷰를 기준으로 구분
//            모아보기 버튼
            myfeed_gatherFeed->{
                val bundle = Bundle()
                bundle.putString("title",myfeed_gatherFeed!!.text.toString())
//                AddFragment(FirstFragmㅇent(),bundle,"first"
//                        ,supportFragmentManager.findFragmentById(R.id.main_container))
                myfeed_title.text = "나의 글"
                ReplaceFragment(GatherFragment(),bundle,"first")
            }
//            담아보기 버튼
            myfeed_scrapFeed->{
                val bundle = Bundle()
                bundle.putString("title",myfeed_scrapFeed!!.text.toString())
//                AddFragment(FirstFragment(),bundle,"first"
//                        ,supportFragmentManager.findFragmentById(R.id.main_container))
                myfeed_title.text = "담은 글"
               ReplaceFragment(ScrapFragment(),bundle,"first")
            }
        }

    }
    fun AddFragment(fragment: Fragment, bundle: Bundle, tag:String){    //
        val fm=supportFragmentManager
        val transaction=fm.beginTransaction()
        fragment.arguments = bundle
        transaction.add(R.id.myfeed_fragmentContainer,fragment,tag)
        transaction.commit()

    }

    fun ReplaceFragment(fragment: Fragment, bundle: Bundle, tag:String){    //fragment replace
        val fm=supportFragmentManager
        val transaction=fm.beginTransaction()
        fragment.arguments = bundle
        transaction.add(R.id.myfeed_fragmentContainer,fragment,tag)
        transaction.addToBackStack(null)
        transaction.commit()

    }
}
