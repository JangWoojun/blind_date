package com.example.blinddate.message

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
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

    lateinit var listViewAdapter: ListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_like_list)

        val userListView = findViewById<ListView>(R.id.userListView)

        listViewAdapter = ListViewAdapter(this,likeUserList)
        userListView.adapter = listViewAdapter


        getMyLikeList()


        userListView.setOnItemClickListener { parent, view, position, id ->
            checkMatching(likeUserList[position].uid.toString())
        }

    }
    private fun checkMatching(otherUid:String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.children.count()==0){
                    Toast.makeText(this@MyLikeListActivity,"매칭이 실패하였습니다",Toast.LENGTH_LONG).show()
                }
                else {
                    for (dataModel in dataSnapshot.children){
                        val likeUserList = dataModel.key.toString()
                        if (likeUserList==uid){
                            Toast.makeText(this@MyLikeListActivity,"매칭이 성공하였습니다",Toast.LENGTH_LONG).show()
                        }
                        else {
                            Toast.makeText(this@MyLikeListActivity,"매칭이 실패하였습니다",Toast.LENGTH_LONG).show()
                        }
                    }
                }


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FirebaseRef.userLikeRef.child(otherUid).addValueEventListener(postListener)
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
                listViewAdapter.notifyDataSetChanged()
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