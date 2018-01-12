package com.doodle.doodle.Doodle_Books

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.doodle.doodle.R
import kotlinx.android.synthetic.main.activity_doodle_book_two.*

class DoodleBookTwoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doodle_book_two)

        book2_back.setOnClickListener{
            finish()
        }
        doodle_book_two.setOnClickListener{
            var intent = Intent(this, OrderActivity::class.java)
            intent.putExtra("flag", 2 )
            this.startActivity(intent)
        }
    }
}