package com.example.blinddate.message

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.blinddate.R
import com.example.blinddate.auth.UserDataModel
import com.example.blinddate.utils.FirebaseAuthUtils
import com.example.blinddate.utils.FirebaseRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MyLikeListActivity : AppCompatActivity() {

    private val TAG = "MyLikeListActivity"
    private val uid = FirebaseAuthUtils.getUid()

    private var likeUserListUid = mutableListOf<String>()
    private var likeUserList = mutableListOf<UserDataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_like_list)
        getUserDataList()
        getMyLikeList()
    }
    private fun getMyLikeList(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dataModel in dataSnapshot.children){
                    likeUserListUid.add(dataModel.key.toString())
                }
                getUserDataList()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FirebaseRef.userLikeRef.child(uid).addValueEventListener(postListener)
    }
    private fun getUserDataList(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dataModel in dataSnapshot.children){

                    val user = dataModel.getValue(UserDataModel::class.java)
                    if (likeUserListUid.contains(user?.uid)){
                        likeUserList.add(user!!)
                    }

                }
                Log.d(TAG,likeUserList.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FirebaseRef.userInfoRef.addValueEventListener(postListener)
    }
}