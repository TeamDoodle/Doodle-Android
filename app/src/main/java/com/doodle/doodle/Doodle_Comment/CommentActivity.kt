package com.doodle.doodle.Doodle_Comment

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.doodle.doodle.Network.ApplicationController
import com.doodle.doodle.Network.NetworkService
import com.doodle.doodle.R
import kotlinx.android.synthetic.main.activity_comment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentActivity : AppCompatActivity() ,SwipeRefreshLayout.OnRefreshListener{
    override fun onRefresh() {
        swipe!!.isRefreshing=false
        init(idx!!)
    }

    private lateinit var commentDatas : ArrayList<CommentData>
    private var networkService: NetworkService? = null
    private var requestManager : RequestManager? = null
    private var commentAdapter : CommentAdapter? = null
    private var myCommentList : ArrayList<CommentList>? = null
    private var idx:Int?=null
    private var swipe : SwipeRefreshLayout? = null
    // private var commentRecyclerview : RecyclerView? = null
    //private var requestManager : RequestManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        swipe=findViewById(R.id.comment_swipe)
        swipe!!.setOnRefreshListener(this)
        mainLayout.setOnClickListener {
            val hide = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hide.hideSoftInputFromWindow(mainLayout.windowToken, 0)
        }
        comment_recycler.setOnClickListener{
            val hide = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hide.hideSoftInputFromWindow(mainLayout.windowToken, 0)
        }

        networkService = ApplicationController.instance!!.networkService
        requestManager = Glide.with(this)

        comment_recycler.layoutManager = LinearLayoutManager(this)
        //리사이클러뷰의 패턴 지정
        comment_recycler.adapter = commentAdapter

        idx = intent.getIntExtra("idx", 0)

        Log.d("idx",idx.toString())
        init(idx!!)


        comment_postBtn.setOnClickListener{
            val commentPostResponse = networkService!!.postComment(getToken("token"), idx!!, PostCommentPost(comment_commentEt.text.toString()))
            Log.d("onclickListener",idx.toString())
            Log.d("success2",getToken("token"))
            commentPostResponse.enqueue(object : Callback<CommentPostResponse> {

                override fun onResponse(call: Call<CommentPostResponse>?, response: Response<CommentPostResponse>?) {
                    Log.d("success2",idx.toString())

                    if (response!!.isSuccessful) {
                        Log.d("success",idx.toString())
                        if (response!!.body().message.equals("success")) {
                            val hide = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            hide.hideSoftInputFromWindow(mainLayout.windowToken, 0)
                            comment_commentEt.setText("")
                            ApplicationController.instance!!.makeToast("작성 완료")
                            onRefresh()


                        } else {
                            ApplicationController.instance!!.makeToast("작성 실패")
                        }

                    }
                    else
                        ApplicationController.instance!!.makeToast("작성 실패2")
                }

                override fun onFailure(call: Call<CommentPostResponse>?, t: Throwable?) {
                    ApplicationController.instance!!.makeToast("서버 상태를 확인해 주세요")
                }

            })
        }
    }

    fun getToken(key : String) : String{
        val prefs = this.getSharedPreferences("token", Context.MODE_PRIVATE)
        return prefs.getString(key, "")
    }
    fun init(idx : Int) {
        val commentResponse = networkService!!.getComment(getToken("token"), idx)
        Log.d("init",idx.toString())
        Log.d("success1",getToken("token"))
        commentResponse.enqueue(object : Callback<CommentResponse> {

            override fun onResponse(call: Call<CommentResponse>?, response: Response<CommentResponse>?) {
                Log.d("success1",idx.toString())

                if (response!!.isSuccessful) {
                    Log.d("success",idx.toString())
                    if (response!!.body().message.equals("success")) {
                        ApplicationController.instance!!.makeToast("작성 완료")
                        comment_like_hit.text = response.body().result.doodle.like_count.toString()
                        comment_comment_hit.text = response.body().result.doodle.comment_count.toString()
                        comment_share_hit.text = response.body().result.doodle.scrap_count.toString()
//                        requestManager!!.load(response.body().result.doodle.profile!!).into(comment_profile)
                        myCommentList = response.body().result.comments
                        commentAdapter = CommentAdapter(myCommentList!!,requestManager!!)
                        comment_recycler!!.adapter = commentAdapter

                    } else {
                        ApplicationController.instance!!.makeToast("작성 실패")
                    }

                    //detail_image.setImageURI(Uri.parse(response!!.body().data.content.board_image))
                }
                else
                    ApplicationController.instance!!.makeToast("작성 실패")
            }
            override fun onFailure(call: Call<CommentResponse>?, t: Throwable?) {
                ApplicationController.instance!!.makeToast("서버 상태를 확인해 주세요")
            }

        })
    }

    override fun onResume() {
        super.onResume()
    }
}




