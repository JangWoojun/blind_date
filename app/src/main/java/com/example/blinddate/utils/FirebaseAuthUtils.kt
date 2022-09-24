package com.example.blinddate.utils

import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthUtils {
    companion object {
        private lateinit var auth: FirebaseAuth

        fun getUid():String {
            auth = FirebaseAuth.getInstance()
            return auth.currentUser?.uid.toString() // 현재 유저의 uid르 돌려줌
         }


    }
}