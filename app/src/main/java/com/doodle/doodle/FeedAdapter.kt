package com.doodle.doodle

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by SAMSUNG on 2018-01-01.
 */
class FeedAdapter(var postList : ArrayList<FeedList>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val HEADER:Int=0
    private val ITEM:Int=1


    private var onItemClick : View.OnClickListener? = null

    fun setOnItemClickListener(l: View.OnClickListener){
        onItemClick = l
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if(holder is Myfeed_ViewHolder){
            val myfeed_ViewHolder : Myfeed_ViewHolder=holder
            if(position<postList!!.size) {
                myfeed_ViewHolder!!.postImage.setImageResource(postList!!.get(position).feedImage)
            }
        }else if(holder is Header_ViewHolder){
            val headerHolder : Header_ViewHolder = holder
            headerHolder.text1.setText("Header")
        }

    }

    override fun getItemCount(): Int = postList!!.size


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {

        if(viewType==ITEM){
            val mainView : View= LayoutInflater.from(parent!!.context)
                    .inflate(R.layout.feed_item, parent, false) //레이아웃 연결
            mainView.setOnClickListener(onItemClick)
            return Myfeed_ViewHolder(mainView)
        }else if(viewType==HEADER){
            val mainView : View=LayoutInflater.from(parent!!.context)
                    .inflate(R.layout.myfeed_header,parent,false)
            return Header_ViewHolder(mainView)
        }

        return null
    }


    override fun getItemViewType(position: Int): Int {
        if(position==0){
            return HEADER
        }
        return ITEM
    }

}