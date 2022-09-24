package com.example.blinddate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.blinddate.auth.IntroActivity
import com.example.blinddate.utils.FirebaseAuthUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val uid = FirebaseAuthUtils.getUid()

        if (uid != "null"){
            Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            },3000)
        }
        else {
            Handler().postDelayed({
                startActivity(Intent(this, IntroActivity::class.java))
                finish()
            },3000)
        }


    }
}