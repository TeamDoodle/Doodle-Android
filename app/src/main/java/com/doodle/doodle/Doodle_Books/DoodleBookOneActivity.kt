package com.doodle.doodle.Doodle_Books

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.doodle.doodle.R
import kotlinx.android.synthetic.main.activity_doodle_book_one.*

class DoodleBookOneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doodle_book_one)

        book1_back.setOnClickListener{
            finish()
        }
        doodle_book_one.setOnClickListener{
            var intent =Intent(this, OrderActivity::class.java)
            intent.putExtra("flag", 1 )
            this.startActivity(intent)
        }
    }
}