package com.doodle.doodle.Doodle_Me

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import android.support.v7.widget.StaggeredGridLayoutManager



/**
 * Created by Jinyoung on 2018-01-09.
 */

class RecyclerViewItemDeco
(private val spanCount: Int, private val spacing: Float, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        /* if(view instanceof ViewModel == false){ //홈 그리드 아이템이 아닌경우 패스
return;
}*/
        val position = parent.getChildAdapterPosition(view) // item position

        val spanIndex = (view.layoutParams as StaggeredGridLayoutManager.LayoutParams).spanIndex

        if (includeEdge) {


            if (spanIndex == 0) {
                //좌측 아이템이며 우측 패딩을 설정한 패딩의 1/2로 설정
                outRect.left = spacing.toInt()
                outRect.right = (spacing / spanCount).toInt()
            } else {//if you just have 2 span . Or you can use (staggeredGridLayoutManager.getSpanCount()-1) as last span
                //우측 아이템이며 좌측 패딩을 설정한 패딩의 1/2로 설정
                outRect.left = (spacing / spanCount).toInt()
                outRect.right = spacing.toInt()
            }

            //상단 패딩
            if (position < spanCount) { // top edge
                outRect.top = spacing.toInt()
            }

            //하단 패딩
            outRect.bottom = spacing.toInt() // item bottom
        }
    }
}