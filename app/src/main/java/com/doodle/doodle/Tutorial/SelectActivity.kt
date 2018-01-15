package com.doodle.doodle.Tutorial

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.doodle.doodle.Login.LoginActivity
import com.doodle.doodle.R
import com.doodle.doodle.SignUp.SignUpActivity
import kotlinx.android.synthetic.main.activity_select.*

class SelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        select_signup.setOnClickListener { startActivity(Intent(applicationContext,SignUpActivity::class.java))
        finish()}
        select_login.setOnClickListener{startActivity(Intent(applicationContext,LoginActivity::class.java))
        finish()}
    }
}
