package com.doodle.doodle.Doodle_Me
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.doodle.doodle.Login.CommonData
import com.doodle.doodle.R
import kotlinx.android.synthetic.main.activity_myfeed_big.*

class MyfeedBigActivity: AppCompatActivity() {
    var position:Int?=null
    var flag:Int?=null
    var profileImage:ImageView?=null
    var requestManager:RequestManager?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myfeed_big)
        profileImage=findViewById(R.id.myfeed_big_profile)
        requestManager=Glide.with(this)
        requestManager!!.load(CommonData.loginData!!.profile.profile).into(profileImage!!)

        val bundle = Bundle()

//        값을 받아왔네효,,
//        idx=intent.getIntExtra("idx",0)
        flag=intent.getIntExtra("flag",0)
        position=intent.getIntExtra("position",0)
//        bundle.putInt("idx",idx!!)
        bundle.putInt("flag",flag!!)
        bundle.putInt("position",position!!)
        myfeed_big_nickname.text=CommonData.loginData!!.profile.nickname

        AddFragment(BigGatherFragment(),bundle)

    }

    fun AddFragment(fragment: Fragment, bundle: Bundle){    //fragment2를 fragment로 대체하는 부분
        val fm=supportFragmentManager
        val transaction=fm.beginTransaction()
        fragment.arguments = bundle
        transaction.add(R.id.big_fragment,fragment)
        transaction.commit()

    }

}