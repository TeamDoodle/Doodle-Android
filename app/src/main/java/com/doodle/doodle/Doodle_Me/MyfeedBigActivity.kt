package com.doodle.doodle.Doodle_Me
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.doodle.doodle.R
import kotlinx.android.synthetic.main.activity_myfeed_big.*

class MyfeedBigActivity: AppCompatActivity() {

    var feedType : Int = 0
    var updated: String?=null
    var like_count:Int?=null
    var comment_count:Int?=null
    var scrap_count:Int?=null
    var nickname:String?=null
    var idx:Int?=null
    var image:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myfeed_big)

        val bundle = Bundle()

//        값을 받아왔네효,,
        feedType = intent.getIntExtra("feedType",0)
        updated = intent.getStringExtra("updated")
        like_count=intent.getIntExtra("like_count",0)
        comment_count=intent.getIntExtra("comment_count",0)
        scrap_count=intent.getIntExtra("scrap_count",0)
        nickname=intent.getStringExtra("nickname")
        idx=intent.getIntExtra("idx",0)
        image=intent.getStringExtra("image")

        when(feedType){    //뷰를 기준으로 구분
            1->{
                myfeed_big_writingName.setText("")

//        데이터들을 다시 BigGatherFragment로 보낼게염,,,★
//                GatherFragment->MyfeedBigActivity->BigGatherFragment
                bundle.putString("page", "first")
                bundle.putString("updated",updated!!)
                bundle.putInt("like_count", like_count!!)
                bundle.putInt("comment_count", comment_count!!)
                bundle.putInt("scrap_count", scrap_count!!)
                bundle.putString("nickname",nickname!!)
                bundle.putInt("idx",idx!!)
                bundle.putString("image",image!!)
                AddFragment(BigGatherFragment(),bundle,"first")
            }
            2->{
                myfeed_big_profile.visibility = View.GONE
                myfeedBig_title.text = "담은 글"

                //        데이터들을 다시 BigGatherFragment로 보낼게염,,,★
                //      ScrapFragment->MyfeedBigActivity->BigGatherFragment
                bundle.putString("page", "first")
                bundle.putString("updated",updated!!)
                bundle.putInt("like_count", like_count!!)
                bundle.putInt("comment_count", comment_count!!)
                bundle.putInt("scrap_count", scrap_count!!)
                bundle.putString("nickname",nickname!!)
                bundle.putInt("idx",idx!!)
                bundle.putString("image",image!!)
                AddFragment(BigGatherFragment(),bundle,"second")
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