package com.real.doodle.Tutorial

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.real.doodle.Login.LoginActivity
import com.real.doodle.R
import com.real.doodle.SignUp.SignUpActivity
import kotlinx.android.synthetic.main.activity_tutorial6.*

class Tutorial6 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial6)
        select_login.setOnClickListener {
            startActivity(Intent(applicationContext,LoginActivity::class.java))
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
            finish()
        }
        select_signup.setOnClickListener{
            startActivity(Intent(applicationContext,SignUpActivity::class.java))
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
            finish()
        }
    }
}
