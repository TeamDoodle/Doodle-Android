package com.doodle.doodle.Doodle_Write

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import android.widget.Toast
import com.bumptech.glide.Glide
import com.doodle.doodle.R
import kotlinx.android.synthetic.main.activity_write.*
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentActivity
import android.widget.ImageView
import com.doodle.doodle.Doodle_Me.MyfeedActivity
import java.io.*
import java.util.*


class WriteActivity  : AppCompatActivity(),View.OnClickListener,View.OnLongClickListener{

    private var animShow:Animation?=null
    private var animHide:Animation?=null
    var params:ViewGroup.LayoutParams?=null
    var params_filter:ViewGroup.LayoutParams?=null
    private var color:Color?=null
    private val REQ_CODE_SELECT_IMAGE=100
    private var data: Uri?=null

//    var params_bottom:ViewGroup.LayoutParams?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
        initAnimation()
        var flag:Boolean?=null
        flag=true

        write_menu_content!!.startAnimation(animHide)
        write_menu_content!!.visibility=View.GONE
        write_menu_top!!.animate().translationY(565F)
//
////        사진 정방형으로
//        params=write_image_layout!!.layoutParams
//        params!!.width=resources.displayMetrics.widthPixels
//        params!!.height=params!!.width
//
////        필터 사진 정방형으로
//        params_filter=filter!!.layoutParams
//        params_filter!!.width=resources.displayMetrics.widthPixels
//        params_filter!!.height=params_filter!!.width

//        params_bottom=write_menu_top!!.layoutParams

//      메뉴 이동 및 화면 보기 조정
        write_change_image_layout!!.visibility=View.GONE
        write_change_font_layout!!.visibility=View.VISIBLE
        write_indicator.setOnClickListener {


            if(flag==true){
                write_menu_content!!.startAnimation(animHide)
                write_menu_content!!.visibility=View.GONE
                write_menu_top!!.animate().translationY(565F)
                flag=false
            }else{
                write_menu_content!!.startAnimation(animShow)
                write_menu_content!!.visibility=View.VISIBLE
                write_menu_top!!.animate().translationY(0F)
                flag=true
            }

        }
//      글씨체 수정 버튼
        write_edit_font.setOnClickListener {
//            text와 edittext visibility
            write_change_font_layout!!.visibility=View.VISIBLE
            write_filter_layout!!.visibility=View.GONE
            write_change_image_layout!!.visibility=View.GONE


        }

//        글씨 폰트
        write_font_button1.setOnClickListener(this)

//        글씨 크기
        write_seekbar_fontsize!!.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                write_edit!!.setTextSize(p1.toFloat())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

//        줄 간격
        write_seekbar_linespace!!.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                write_edit.setLineSpacing(0F,(p1/200+1).toFloat())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0y: SeekBar?) {
            }
        })

//            color=Color(resources.getColor(R.color.color1))

//          글씨 색깔
            color1.setOnClickListener(this)
            color1.setOnLongClickListener(this)
            color2.setOnClickListener(this)
            color3.setOnClickListener(this)
            color4.setOnClickListener(this)
            color5.setOnClickListener(this)

//          사진 버튼 눌렀을때
        write_pick_image.setOnClickListener {
            //text와 edittext visibility
            write_change_font_layout!!.visibility=View.GONE
            write_filter_layout!!.visibility=View.GONE
            write_change_image_layout!!.visibility=View.VISIBLE
        }

//        이미지 있는 부분을 눌렀을때 텍스트 수정 가능
        write_image_layout.setOnClickListener{
            write_edit!!.isFocusable
        }

//         앨범 접근 해서 사진 가져오기
        goto_album.setOnClickListener {
            changeImage()
            write_filter_layout.visibility=View.VISIBLE
            write_change_image_layout.visibility=View.GONE

//          사진 눌렀을 때 다시 write_font_change_layout 보이게
        }

//       완료 버튼
        write_commit.setOnClickListener {

            //        사진 정방형으로
            params=write_image_layout!!.layoutParams
            params!!.width=resources.displayMetrics.widthPixels
            params!!.height=params!!.width

            //        필터 사진 정방형으로
            params_filter=filter!!.layoutParams
            params_filter!!.width=resources.displayMetrics.widthPixels
            params_filter!!.height=params_filter!!.width

            write_edit.bringToFront()
            write_edit.clearFocus()
            Toast.makeText(applicationContext,"완료",Toast.LENGTH_SHORT).show()
            write_image_layout.isDrawingCacheEnabled = true
            write_image_layout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
            write_image_layout.layout(0, 0, write_image_layout.measuredWidth, write_image_layout.measuredHeight)
            write_image_layout.buildDrawingCache(true)
            val b = Bitmap.createBitmap(write_image_layout.getDrawingCache())
            write_image_layout.setDrawingCacheEnabled(false)
            val bytes = ByteArrayOutputStream()
            b.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

            val f = File(Environment.getExternalStorageDirectory().toString() +
                    File.separator + "doodle.jpg"+System.currentTimeMillis().toString())
            try {
                Log.d("글적","글적글적")
                f.createNewFile()
                val fo = FileOutputStream(f)
                fo.write(bytes.toByteArray())
                fo.close()
            } catch (e: Exception) {
            }
            val intent:Intent= Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            intent!!.setData(Uri.fromFile(f))
            sendBroadcast(intent)

            startActivity(Intent(applicationContext,MyfeedActivity::class.java))
        }

//        필터 seekbar
        filter_seekbar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if(p1>1){
                    filter.setAlpha(100-p1)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

    }//End of onCreate
//          슬라이드 애니메이션 초기화
    fun initAnimation(){
        animShow=AnimationUtils.loadAnimation(this, R.anim.view_show)
        animHide=AnimationUtils.loadAnimation(this, R.anim.view_hide)
    }
//           이미지 바꾸기
    fun changeImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE)
        write_edit.bringToFront()
}

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    //if(ApplicationController.getInstance().is)
                    this.data = data!!.data
                    Log.v("이미지", this.data.toString())

                    val options = BitmapFactory.Options()

                    var input: InputStream? = null
                    try {
                        input = contentResolver.openInputStream(this.data)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }

                    val bitmap = BitmapFactory.decodeStream(input, null, options) // InputStream 으로부터 Bitmap 으로!!
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
//                    val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray()) //서버 연동 후에 주석 풀ㅈr...★
                    val photo = File(this.data.toString()) // 가져온 파일의 이름을 알아Lㅐ7l 우lㅎN...☆

//
                    DressImage(this,data.data,write_image)
//                  기본 필터 이미지에 적용
                    DressImage(this,data.data,filter_basic)
                    DressImage(this,data.data,filter1)
                    DressImage(this,data.data,filter2)
                    DressImage(this,data.data,filter3)
                    DressImage(this,data.data,filter4)
                    DressImage(this,data.data,filter5)


//                   이미지 필터 적용
                    filter_basic.setOnClickListener(this)
                    filter1.setOnClickListener(this)
                    filter2.setOnClickListener(this)
                    filter3.setOnClickListener(this)
                    filter4.setOnClickListener(this)
                    filter5.setOnClickListener(this)

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

//    이미지 입히기
    fun DressImage(activity:FragmentActivity,uri:Uri?,image:ImageView){
        Glide.with(activity)
                .load(uri)
                .centerCrop()
                .into(image)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("WrongConstant")

//    onClick
    override fun onClick(v: View?) {
        when(v){
            color1->{
                Toast.makeText(applicationContext,"안녕",Toast.LENGTH_SHORT).show()
                write_edit.setTextColor(resources.getColor(R.color.color1))
            }
            color2->{
                write_edit.setTextColor(resources.getColor(R.color.color2))
            }
            color3->{
                write_edit.setTextColor(resources.getColor(R.color.color3))
            }
            color4->{
                write_edit.setTextColor(resources.getColor(R.color.color4))
            }
            color5->{
                write_edit.setTextColor(resources.getColor(R.color.color5))
            }
            write_font_button1->{
//                    val font1=resources.getFont(R.font.nanum_barun_gothic)
//                write_edit.typeface=font1
            }
            filter_basic->{
                filter.visibility=View.GONE
            }
            filter1->{
                filter.visibility=View.VISIBLE
                filter.setImageResource(R.drawable.angae)
            }
            filter2->{
                filter.visibility=View.VISIBLE
                filter.setImageResource(R.drawable.dalbit)
            }
            filter3->{
                filter.visibility=View.VISIBLE
                filter.setImageResource(R.drawable.saebyuck)
            }
            filter4->{
                filter.visibility=View.VISIBLE
                filter.setImageResource(R.drawable.latte)
            }
            filter5->{
                filter.visibility=View.VISIBLE
                filter.setImageResource(R.drawable.noel)
            }
        }
    }


//    롱클릭 했을 때
override fun onLongClick(v: View?): Boolean {
    Log.d("longClick","LongClick")
    Toast.makeText(applicationContext,"롱클릭",Toast.LENGTH_SHORT).show()


    return true
}

}
