package com.example.blinddate.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.blinddate.R
import com.google.android.material.textfield.TextInputEditText

class JoinActivity : AppCompatActivity() {

    private val TAG = "JoinActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        val joinBtn = findViewById<Button>(R.id.joinBtn)
        joinBtn.setOnClickListener {
            val email = findViewById<TextInputEditText>(R.id.emailArea)
            val password = findViewById<TextInputEditText>(R.id.passwordArea)

            Log.d(TAG,email.text.toString())
            Log.d(TAG,password.text.toString())
        }
    }
}