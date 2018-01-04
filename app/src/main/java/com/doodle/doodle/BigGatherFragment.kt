package com.doodle.doodle

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by SAMSUNG on 2018-01-01.
 */
class BigGatherFragment: Fragment(), View.OnClickListener {


    private var BigFeedList : RecyclerView? = null
    private var postDatas : ArrayList<FeedBigList>? = null
    private var adapter : FeedBigAdapter? = null
    private var nameTag : String = ""









    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        onActivityCreated(savedInstanceState)

        val v = inflater!!.inflate(R.layout.fragment_big_gather,container,false) //레이아웃을 붙이는 과정
        if(arguments!=null){
            nameTag = arguments.get("page").toString()
            //v!!.first_text.text = arguments.getString("title")
        }

        BigFeedList = v.findViewById(R.id.feed_big_recyclerview)




        postDatas = ArrayList<FeedBigList>()
        postDatas!!.add(FeedBigList(R.drawable.kko, "123", 1,2,3,"a"))
        postDatas!!.add(FeedBigList(R.drawable.pa, "123", 1,2,3,"b"))
        postDatas!!.add(FeedBigList(R.drawable.e, "123", 1,2,3,"c"))
        postDatas!!.add(FeedBigList(R.drawable.kko, "123", 1,2,3,"a"))
        postDatas!!.add(FeedBigList(R.drawable.pa, "123", 1,2,3,"b"))
        postDatas!!.add(FeedBigList(R.drawable.e, "123", 1,2,3,"a"))
        postDatas!!.add(FeedBigList(R.drawable.kko, "123", 1,2,3,"d"))
        postDatas!!.add(FeedBigList(R.drawable.pa, "123", 1,2,3,"a"))
        postDatas!!.add(FeedBigList(R.drawable.e, "123", 1,2,3,"a"))
        postDatas!!.add(FeedBigList(R.drawable.kko, "123", 1,2,3,"a"))
        postDatas!!.add(FeedBigList(R.drawable.pa, "123", 1,2,3,"a"))
        postDatas!!.add(FeedBigList(R.drawable.e, "123", 1,2,3,"a"))

        adapter = FeedBigAdapter(postDatas!!, nameTag, "")
        adapter!!.setOnItemClickListener(this)
        BigFeedList!!.adapter = adapter





        return v

    }

    override fun onClick(v: View?) {
        val idx : Int = BigFeedList!!.getChildAdapterPosition(v)


        //val name : String? = postDatas!!.get(idx).pocketmonName
        //val type : String? = pocketmonDatas!!.get(idx).pocketmonType
        //val pro : Int? = pocketmonDatas!!.get(idx).pocketmonImage

        //val intent = Intent(applicationContext, Main3Activity::class.java)

        //intent.putExtra("pro", pro)
        //intent.putExtra("type", type)
        //intent.putExtra("name", name)
        //startActivity(intent)


    }


}