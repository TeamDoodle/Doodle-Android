package com.doodle.doodle

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by SAMSUNG on 2018-01-01.
 */
class ScrapFragment : Fragment(), View.OnClickListener  {

    private var FeedList : RecyclerView? = null
    private var postDatas : ArrayList<FeedList>? = null
    private var adapter : FeedAdapter? = null




    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater!!.inflate(R.layout.fragment_gather,container,false) //레이아웃을 붙이는 과정
        if(arguments!=null){
            //v!!.first_text.text = arguments.getString("title")
        }


        FeedList = v.findViewById(R.id.feed_recyclerview)

        postDatas = ArrayList<FeedList>()


        postDatas!!.add(FeedList(R.drawable.e))
        postDatas!!.add(FeedList(R.drawable.pa))
        postDatas!!.add(FeedList(R.drawable.kko))
        postDatas!!.add(FeedList(R.drawable.e))
        postDatas!!.add(FeedList(R.drawable.pa))
        postDatas!!.add(FeedList(R.drawable.kko))
        postDatas!!.add(FeedList(R.drawable.e))
        postDatas!!.add(FeedList(R.drawable.pa))
        postDatas!!.add(FeedList(R.drawable.kko))
        postDatas!!.add(FeedList(R.drawable.e))
        postDatas!!.add(FeedList(R.drawable.pa))
        postDatas!!.add(FeedList(R.drawable.kko))



        adapter = FeedAdapter(postDatas!!)
        adapter!!.setOnItemClickListener(this)
        FeedList!!.adapter = adapter



        return v

    }

    override fun onClick(v: View?) {
        val idx : Int = FeedList!!.getChildAdapterPosition(v)
        val feedType : Int = 2
        //val name : String? = postDatas!!.get(idx).pocketmonName
        //val type : String? = pocketmonDatas!!.get(idx).pocketmonType
        //val pro : Int? = pocketmonDatas!!.get(idx).pocketmonImage

        val intent = Intent(activity, MyfeedBigActivity::class.java)


        intent.putExtra("feedType", feedType)
        //intent.putExtra("type", type)
        //intent.putExtra("name", name)
        startActivity(intent)


    }

}