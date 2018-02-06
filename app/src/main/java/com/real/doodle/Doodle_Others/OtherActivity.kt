package com.real.doodle.Doodle_Others

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.real.doodle.Doodle_Me.RecyclerViewItemDeco
import com.real.doodle.Network.ApplicationController
import com.real.doodle.Network.NetworkService
import com.real.doodle.R
import kotlinx.android.synthetic.main.activity_other.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtherActivity : AppCompatActivity(),View.OnClickListener {
    private var networkService: NetworkService? = null
    private var otherAdapter: OtherAdapter? = null
    private var requestManager: RequestManager? = null
    private var layoutManager: StaggeredGridLayoutManager? = null
    private var otherList: ArrayList<userDoodleData>? = null
    private var otherRecyclerView:RecyclerView?=null
    var user_idx:Int?=null
    override fun onClick(v: View?) {
        val idx:Int=otherRecyclerView!!.getChildAdapterPosition(v)
        val intent=Intent(applicationContext,BigOtherActivity::class.java)
        intent.putExtra("user_idx",user_idx)
        startActivity(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other)
        networkService= ApplicationController.instance!!.networkService
//

        otherfeed_back.setOnClickListener { finish() }
        user_idx=intent.getIntExtra("user_idx",0)
        otherList=ArrayList()
        requestManager= Glide.with(this)
        otherAdapter= OtherAdapter(otherList!!,requestManager!!)
        otherRecyclerView=findViewById(R.id.other_recyclerview)

        layoutManager= StaggeredGridLayoutManager(2,1)
        layoutManager!!.gapStrategy=StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        layoutManager!!.orientation=StaggeredGridLayoutManager.VERTICAL
        otherRecyclerView!!.isNestedScrollingEnabled=false
        otherRecyclerView!!.layoutManager=layoutManager
        otherRecyclerView!!.addItemDecoration(RecyclerViewItemDeco(2,convertDpToPixel(4,this),true))
        otherRecyclerView!!.adapter=otherAdapter
        getOtherFeed(user_idx!!)

    }

    fun getOtherFeed(user_index:Int){
        Log.d("user_index",user_idx.toString())

        var otherResponse=networkService!!.getOthers(getToken("token"),user_index!!)
        otherResponse.enqueue(object : Callback<OtherResponse> {
            override fun onResponse(call: Call<OtherResponse>?, response: Response<OtherResponse>?) {
                if (response!!.isSuccessful){
                    if(response.body().result.user.doodle_count==null){

                    }else{
                        other_posting.text=response.body().result.user.doodle_count.toString()
                    }

                    other_scrap.text=response.body().result.user.scrap_count.toString()
                    Log.d("success","success")
                    otherList=response.body().result.doodle
                    requestManager!!.load(response.body().result.user.profile!!).into(other_profile!!)
                    other_nickname.text=response.body().result.user.nickname
                    Log.d("프사 불러오니??",response.body().result.user.profile)
                    Log.d("Did you take userName?",response.body().result.user.nickname)
                    otherAdapter= OtherAdapter(otherList!!,requestManager!!)
                    otherRecyclerView!!.adapter=otherAdapter
                    otherAdapter!!.setOnItemClickListener(this@OtherActivity)
                    otherAdapter!!.notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<OtherResponse>?, t: Throwable?) {
                ApplicationController.instance!!.makeToast("통신상태를 확인해 주세요")
            }
        })
    }

    fun convertDpToPixel(dp: Int, context: FragmentActivity):Float{

        val resources: Resources = context.resources

        val metrics: DisplayMetrics = resources.displayMetrics

        val px:Float = dp * (metrics.densityDpi / 160f);

        return px

    }
    fun getToken(key : String) : String{
        val prefs = this.getSharedPreferences("token", Context.MODE_PRIVATE)
        return prefs.getString(key, "")
    }
}
