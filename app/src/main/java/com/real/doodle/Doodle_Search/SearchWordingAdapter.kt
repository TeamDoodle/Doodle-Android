package com.real.doodle.Doodle_Search

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.real.doodle.Doodle_Single.SingleActivity
import com.real.doodle.R

class SearchWordingAdapter(var con: Context, private var dataList: ArrayList<SearchWordingData>, private var requestManager: RequestManager) : RecyclerView.Adapter<SearchWordingViewHolder>() {

    override fun onBindViewHolder(holder: SearchWordingViewHolder?, position: Int) {
        var holder: SearchWordingViewHolder = holder as SearchWordingViewHolder

        if (dataList[position].image == null) {
            requestManager!!.load(R.drawable.mytext1).into(holder!!.image)
        } else {
            requestManager!!.load(dataList!![position].image).placeholder(R.mipmap.ic_launcher).into(holder!!.image)
        }

//        holder!!.userImage = dataList!![position].Image.toString()
        holder!!.image.setOnClickListener {
            val intent: Intent = Intent(con!!, SingleActivity::class.java)
            intent.putExtra("idx", dataList!![position].idx)
            con!!.startActivity(intent)
        }
    }

    fun setAdapter(dataList: ArrayList<SearchWordingData>) {
        this.dataList = dataList
    }

    override fun getItemCount(): Int = dataList!!.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SearchWordingViewHolder {

        val mainView2: View = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.search_wording_item, parent, false)
//        Log.i("fat",dataList.toString())

        return SearchWordingViewHolder(mainView2)

    }

}