package com.doodle.doodle.Doodle_Me
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.doodle.doodle.R
import kotlinx.android.synthetic.main.activity_myfeed_big.*

class MyfeedBigActivity: AppCompatActivity() {

    var feedType : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myfeed_big)

        val bundle = Bundle()
        AddFragment(BigGatherFragment(),bundle,"first")


//      feedType 넘겨 받음
        feedType = getIntent().getIntExtra("feedType",0)
        when(feedType){    //뷰를 기준으로 구분
            2->{
                myfeedBig_title.text = "담은 글"
            }
        }


    }

    fun AddFragment(fragment: Fragment, bundle: Bundle, tag:String){    //fragment2를 fragment로 대체하는 부분
        val fm=supportFragmentManager
        val transaction=fm.beginTransaction()
        fragment.arguments = bundle
        transaction.add(R.id.myfeed_big_fragmentContainer,fragment,tag)
        transaction.commit()

    }

}
