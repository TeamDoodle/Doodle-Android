package com.doodle.doodle.Doodle_Me

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.net.Uri
import android.os.Environment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.doodle.doodle.Doodle_Comment.CommentActivity
import com.doodle.doodle.Doodle_Others.OtherActivity
import com.doodle.doodle.Doodle_Read.FeedList
import com.doodle.doodle.Like.LikePost
import com.doodle.doodle.Like.LikeResponse
import com.doodle.doodle.Network.ApplicationController
import com.doodle.doodle.Network.NetworkService
import com.doodle.doodle.R
import com.doodle.doodle.Scrap.ScrapPost
import com.doodle.doodle.Scrap.ScrapResponse
import kotlinx.android.synthetic.main.activity_write.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class FeedBigAdapter(var con: Context,
                     var postList: ArrayList<FeedList>,
                     var requestManager: RequestManager
) : RecyclerView.Adapter<BigFeedViewHolder>() {
    private var networkService: NetworkService? = null

    private var feed_or_scrap: Int? = null  //나의 글 인지 담은 글 인지 확인하는 flag
    private var flag: Int? = null   //setAdapter에서 쓰는 flag
    private var click:Boolean?=null

    fun setAdapter(flag: Int, feedList: ArrayList<FeedList>) {
        this.postList = feedList
        this.flag = flag
    }

    //    onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BigFeedViewHolder? {

        val mainView: View = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.feed_big_item, parent, false) //레이아웃 연결
        mainView.setOnClickListener(onItemClick)
        if (flag == 3) {
            feed_or_scrap = CommonFeedData.flag!!
        } else if (flag == 4) {
            feed_or_scrap = CommonScrapData.flag!!
        }
        return BigFeedViewHolder(mainView)

    }

    //  onBindViewHolder
    override fun onBindViewHolder(holder: BigFeedViewHolder?, position: Int) {
        networkService = ApplicationController.instance!!.networkService
        var likeString: String? = null
//        사진
        requestManager= Glide.with(con!!)
        if (postList!!.get(position).image == null) {
            requestManager!!.load(R.drawable.mytext1).into(holder!!.postBigImage)
        } else {
            requestManager!!.load(postList!!.get(position).image).into(holder!!.postBigImage)
        }
        holder!!.PostDate.text = postList!!.get(position).created
        holder!!.WritingName.text = postList!!.get(position).nickname
        holder!!.bigScrap_count.text = postList!!.get(position).scrap_count.toString()
        holder!!.bigNickname.text = postList!!.get(position).nickname
//        닉네임 눌렀을 때 그사람 피드로 감
        holder!!.bigNickname.setOnClickListener {
            val intent:Intent= Intent(con!!, OtherActivity::class.java)
            intent.putExtra("user_idx",postList!!.get(position).user_idx)
            con.startActivity(intent)
        }
        click=false
//        이미지 세팅 눌렀을 때
        holder!!.imageSetting.setOnClickListener {
            if(click==false){
                if(flag==3){
                    holder!!.image_save.visibility=View.VISIBLE
                    holder!!.image_delete.visibility=View.VISIBLE
                }
                else if(flag==4){
                    holder!!.image_save.visibility=View.VISIBLE
                }
            click=true
            }else{
                if(flag==3){
                    holder!!.image_save.visibility=View.GONE
                    holder!!.image_delete.visibility=View.GONE
                }
                else if(flag==4){
                    holder!!.image_save.visibility=View.GONE
                }
                click=false
            }

        }
//        이미지 저장
        holder!!.image_save.setOnClickListener {
            holder!!.postBigImage.isDrawingCacheEnabled = true
            holder!!.postBigImage.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
            holder!!.postBigImage.layout(0, 0, holder!!.postBigImage.measuredWidth, holder!!.postBigImage.measuredHeight)
            holder!!.postBigImage.buildDrawingCache(true)
            val b = Bitmap.createBitmap(holder!!.postBigImage.getDrawingCache())
            holder!!.postBigImage.setDrawingCacheEnabled(false)
            val bytes = ByteArrayOutputStream()
            b.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

            val f = File(Environment.getExternalStorageDirectory().toString() +
                    File.separator + System.currentTimeMillis().toString() + ".jpg")

            try {
                f.createNewFile()
                val fo = FileOutputStream(f)
                fo.write(bytes.toByteArray())
                fo.close()
            } catch (e: Exception) {
            }
            val intent: Intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            intent!!.data = Uri.fromFile(f)
            con!!.sendBroadcast(intent)
            holder!!.image_save.visibility=View.GONE
            holder!!.image_delete.visibility=View.GONE
            Toast.makeText(con!!,"이미지 저장",Toast.LENGTH_SHORT).show()
        }
//        이미지 저장 끝

//        이미지 삭제
        holder!!.image_delete.setOnClickListener {
            holder!!.image_save.visibility=View.GONE
            holder!!.image_delete.visibility=View.GONE

            var deleteImage=networkService!!.delete(getToken("token"),postList!![position].idx!!)
            deleteImage.enqueue(object: Callback<DeleteResponse>{
                override fun onResponse(call: Call<DeleteResponse>?, response: Response<DeleteResponse>?) {
                    notifyItemRemoved(position)
                }
                override fun onFailure(call: Call<DeleteResponse>?, t: Throwable?) {
                    ApplicationController.instance!!.makeToast("통신상태를 확인해주세요")
                }
            })

            Toast.makeText(con!!,"이미지 삭제",Toast.LENGTH_SHORT).show()
        }

        //좋아요 된거 굵은 글씨로 표시할 때 (Feed 불러올 때)
        if (postList!![position].like !== 0) {
            holder!!.bigLike.typeface =
                    Typeface.createFromAsset(con!!.assets, "fonts/nanum_myeongjo_extra_bold.ttf")
        } else {
            holder!!.bigLike.typeface =
                    Typeface.createFromAsset(con!!.assets, "fonts/nanum_myeongjo.ttf")
        }

//       좋아요
        holder!!.bigLike.setOnClickListener {
            //통신 시작
            var likeString: String? = null
            if (postList!![position].like != 0) {
                likeString = "unlike"
            } else {
                likeString = "like"
            }
            var getLike = networkService!!.like(getToken("token"), postList!![position].idx!!, LikePost(likeString!!))
            getLike.enqueue(object : Callback<LikeResponse> {
                override fun onResponse(call: Call<LikeResponse>?, response: Response<LikeResponse>?) {
                    if (response!!.isSuccessful) {
                        if (postList!![position].like != 0) {
                            postList!![position].like = 0
                        } else {
                            postList!![position].like = postList!![position].idx
                        }
                        postList!![position].like_count = response.body().result.count
                        notifyItemChanged(position)
                    }
                }

                override fun onFailure(call: Call<LikeResponse>?, t: Throwable?) {
                    ApplicationController.instance!!.makeToast("서버상태를 확인해 주세요")
                }

            })
            //          통신 끝

        }
//      좋아요 끝

//        댓글
        holder!!.bigComment.setOnClickListener {

            var intent = Intent(con, CommentActivity::class.java)
            intent.putExtra("idx", postList!!.get(position).idx!!)
            con.startActivity(intent)
        }
        holder!!.bigComment_count.text = postList!!.get(position).comment_count.toString()
//        댓글 끝


    }


    private var onItemClick: View.OnClickListener? = null

    fun setOnItemClickListener(l: View.OnClickListener) {
        onItemClick = l
    }

    override fun getItemCount(): Int = postList!!.size

    //  토큰 받아오는 함수
    fun getToken(key: String): String {
        val prefs = con.getSharedPreferences("token", Context.MODE_PRIVATE)
        return prefs.getString(key, "")
    }


}