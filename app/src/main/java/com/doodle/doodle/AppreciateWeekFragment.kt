package com.doodle.doodle

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by SAMSUNG on 2018-01-05.
 */
class AppreciateWeekFragment : Fragment(), View.OnClickListener {



    private var AppreciateList : RecyclerView? = null
    private var appreciateDatas : ArrayList<AppreciateList>? = null
    private var adapter : AppreciateAdapter? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        onActivityCreated(savedInstanceState)

        val v = inflater!!.inflate(R.layout.fragment_appreciate,container,false) //레이아웃을 붙이는 과정
        if(arguments!=null){
            //v!!.first_text.text = arguments.getString("title")
        }

        AppreciateList = v.findViewById(R.id.appreciate_recyclerview)




        appreciateDatas = ArrayList<AppreciateList>()
        appreciateDatas!!.add(AppreciateList(R.drawable.e))
        appreciateDatas!!.add(AppreciateList(R.drawable.e))
        appreciateDatas!!.add(AppreciateList(R.drawable.pa))
        appreciateDatas!!.add(AppreciateList(R.drawable.pa))
        appreciateDatas!!.add(AppreciateList(R.drawable.kko))
        appreciateDatas!!.add(AppreciateList(R.drawable.pa))
        appreciateDatas!!.add(AppreciateList(R.drawable.e))



        adapter = AppreciateAdapter(appreciateDatas!!)
        adapter!!.setOnItemClickListener(this)
        AppreciateList!!.adapter = adapter

        return v

    }

    override fun onClick(v: View?) {
        val idx : Int = AppreciateList!!.getChildAdapterPosition(v)
        val feedType : Int = 1

        //val name : String? = postDatas!!.get(idx).pocketmonName
        //val type : String? = pocketmonDatas!!.get(idx).pocketmonType
        //val pro : Int? = pocketmonDatas!!.get(idx).pocketmonImage

        //val intent = Intent(activity, MyfeedBigActivity::class.java)
        //intent.putExtra("feedType", feedType)
        //intent.putExtra("pro", pro)
        //intent.putExtra("type", type)
        //intent.putExtra("name", name)
        //startActivity(intent)


    }


}
