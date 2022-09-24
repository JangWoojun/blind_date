package com.example.blinddate.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.blinddate.R

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val mybtn = findViewById<Button>(R.id.myPageBtn)
        mybtn.setOnClickListener {
            val intent = Intent(this,MyPageActivity::class.java)
            startActivity(intent)
        }
    }
}