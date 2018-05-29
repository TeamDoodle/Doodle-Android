package com.doodle.doodle.Doodle_Books

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.doodle.doodle.R
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)


        var intent : Intent = intent
        var flag : Int = intent.getIntExtra("flag", 1)

        when(flag){
            1->{
                order_title.text="글적 제 1권"

            }
            2->{
                order_title.text="글적 제 2권"
            }
        }
    }
}