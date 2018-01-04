package com.doodle.doodle

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_myfeed_big.*

class MyfeedBigActivity: AppCompatActivity() {

    var feedType : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myfeed_big)

        val bundle = Bundle()




        feedType = getIntent().getIntExtra("feedType",0)
        when(feedType){    //뷰를 기준으로 구분
            1->{
                myfeed_big_writingName.setText("")
                bundle.putString("page", "first")
                AddFragment(BigGatherFragment(),bundle,"first")
            }
            2->{
                myfeed_big_profile.visibility = View.GONE
                myfeedBig_title.text = "담은 글"
                bundle.putString("page", "second")
                AddFragment(BigGatherFragment(),bundle,"second")
            }
        }

//

    }

    fun AddFragment(fragment: Fragment, bundle: Bundle, tag:String){    //fragment2를 fragment로 대체하는 부분
        val fm=supportFragmentManager
        val transaction=fm.beginTransaction()
        fragment.arguments = bundle
        transaction.add(R.id.myfeed_big_fragmentContainer,fragment,tag)
        transaction.commit()

    }

}
