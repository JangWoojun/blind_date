package com.example.blinddate.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.blinddate.MainActivity
import com.example.blinddate.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private val TAG = "JoinActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        auth = Firebase.auth

        val joinBtn = findViewById<Button>(R.id.joinBtn)
        joinBtn.setOnClickListener {
            val email = findViewById<TextInputEditText>(R.id.emailArea).text.toString()
            val password = findViewById<TextInputEditText>(R.id.passwordArea).text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,"회원가입을 성공하셨습니다",Toast.LENGTH_LONG).show()

                        val user = auth.currentUser

                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        Toast.makeText(this,"회원가입을 실패하셨습니다",Toast.LENGTH_LONG).show()
                    }

                }


        }


    }
}