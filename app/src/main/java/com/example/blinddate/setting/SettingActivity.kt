package com.example.blinddate.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.blinddate.R
import com.example.blinddate.auth.IntroActivity
import com.example.blinddate.message.MyLikeListActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val myLikeBtn = findViewById<Button>(R.id.myLikeListBtn)
        myLikeBtn.setOnClickListener {
            val intent = Intent(this,MyLikeListActivity::class.java)
            startActivity(intent)
        }

        val myBtn = findViewById<Button>(R.id.myPageBtn)
        myBtn.setOnClickListener {
            val intent = Intent(this,MyPageActivity::class.java)
            startActivity(intent)
        }
        val logoutBtn = findViewById<Button>(R.id.logoutBtn)
        logoutBtn.setOnClickListener {

            val auth = Firebase.auth
            auth.signOut()

            val intent = Intent(this,IntroActivity::class.java)
            startActivity(intent)

        }
    }
}