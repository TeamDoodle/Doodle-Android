package com.doodle.doodle.Doodle_Others

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.doodle.doodle.R
import retrofit2.Callback

/**
 * Created by Jinyoung on 2018-01-11.
 */
class OtherAdapter (var otherList: ArrayList<userDoodleData>, var requestManager:RequestManager):RecyclerView.Adapter<OtherViewHolder>(){
    private var onItemClick : View.OnClickListener? = null
    fun setOnItemClickListener(l: View.OnClickListener){
        onItemClick = l
    }
    override fun getItemCount(): Int =otherList!!.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): OtherViewHolder {
        val mainView:View=LayoutInflater.from(parent!!.context)
                .inflate(R.layout.other_item,parent,false)
        mainView.setOnClickListener(onItemClick)
        return OtherViewHolder(mainView)
    }
    fun setAdapter(otherList: ArrayList<userDoodleData>){
        this.otherList=otherList
    }
    override fun onBindViewHolder(holder: OtherViewHolder?, position: Int) {
       var holder:OtherViewHolder=holder as OtherViewHolder
        if(otherList!![position].image==null){
            requestManager.load(R.drawable.mytext2).into(holder!!.OtherImage)
        }
        else{
            requestManager.load(otherList[position].image).into(holder!!.OtherImage)
        }

    }





}