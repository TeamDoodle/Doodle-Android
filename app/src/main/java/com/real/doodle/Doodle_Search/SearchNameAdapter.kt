package com.real.doodle.Doodle_Search

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.real.doodle.Doodle_Others.OtherActivity
import com.real.doodle.R

/**
 * Created by Dayoung on 2018-01-01.
 */
class SearchNameAdapter(var con:Context,private var dataList:ArrayList<SearchNameData>?,private var requestManager: RequestManager): RecyclerView.Adapter<SearchNameViewHolder>() {

    override fun onBindViewHolder(holder: SearchNameViewHolder?, position: Int) {
//        holder!!.userImage. = dataList!![position].ImageData.toString()
        if(dataList!![position].image==null){
            requestManager!!.load(R.drawable.userprofile).into(holder!!.userImage)
        }else{
            requestManager!!.load(dataList!![position].image).placeholder(R.mipmap.ic_launcher).into(holder!!.userImage)
        }

        holder!!.userName.text = dataList!![position].nickname
//        Log.i("fat",position.toString())
        holder!!.userImage.setOnClickListener {
            val intent:Intent= Intent(con!!,OtherActivity::class.java)
            intent.putExtra("user_idx",dataList!![position].idx)
            con!!.startActivity(intent)
        }

    }
    fun setAdapter(dataList: ArrayList<SearchNameData>){
        this.dataList = dataList
    }

    override fun getItemCount(): Int = dataList!!.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SearchNameViewHolder {

        val mainView : View =LayoutInflater.from(parent!!.context)
                .inflate(R.layout.search_name_item,parent,false)
//        Log.i("fat",dataList.toString())

        return SearchNameViewHolder(mainView)

    }
    private var onItemClick : View.OnClickListener? = null

    fun setOnItemClickListener(l: View.OnClickListener){
        onItemClick = l
    }
}