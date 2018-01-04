package com.doodle.doodle

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by SAMSUNG on 2018-01-01.
 */
class AppreciateAdapter(var postList : ArrayList<AppreciateList>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {


        if(holder is AppreciateViewHolder) {

            var holder1 : AppreciateViewHolder = holder as AppreciateViewHolder
            holder1!!.Appreciate_postImage.setImageResource(postList!!.get(position).appreciatePostImage)
        }else if(holder is TopAppreciateViewHolder){
            var holder2 : TopAppreciateViewHolder = holder as TopAppreciateViewHolder
            holder2!!.AppreciateTop_postImage.setImageResource(postList!!.get(position).appreciatePostImage)
            when(position){
                0->{
                    holder2!!.Appreciate_topRate.setImageResource(R.drawable.kko)
                }
                1->{
                    holder2!!.Appreciate_topRate.setImageResource(R.drawable.pa)
                }
                2->{
                    holder2!!.Appreciate_topRate.setImageResource(R.drawable.e)
                }
            }
        }

    }

    private var TYPE_COUNT : Int = 2
    public var VIEW_TYPE_TOP_RATE : Int = 1
    public var VIEW_TYPE_IMAGE : Int = 2




    private var onItemClick : View.OnClickListener? = null

    fun setOnItemClickListener(l: View.OnClickListener){
        onItemClick = l
    }



    override fun getItemCount(): Int = postList!!.size

    override fun getItemViewType(position: Int): Int {

        when(position){
            0->{
                return VIEW_TYPE_TOP_RATE
            }
            1->{
                return VIEW_TYPE_TOP_RATE
            }
            2->{
                return VIEW_TYPE_TOP_RATE
            }
            else->{
                return VIEW_TYPE_IMAGE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {

        var mainView : View= LayoutInflater.from(parent!!.context)
                .inflate(R.layout.top_appreciate_item, parent, false) //레이아웃 연결
        when(viewType){
            VIEW_TYPE_TOP_RATE->{
                mainView = LayoutInflater.from(parent!!.context)
                        .inflate(R.layout.top_appreciate_item, parent, false) //레이아웃 연결
                mainView.setOnClickListener(onItemClick)
                return TopAppreciateViewHolder(mainView)
            }
            VIEW_TYPE_IMAGE->{
                mainView = LayoutInflater.from(parent!!.context)
                        .inflate(R.layout.appreciate_item, parent, false) //레이아웃 연결
                mainView.setOnClickListener(onItemClick)
                return AppreciateViewHolder(mainView)
            }
        }
        return AppreciateViewHolder(mainView)
    }



}

