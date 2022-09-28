package com.example.blinddate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.blinddate.auth.IntroActivity
import com.example.blinddate.auth.UserDataModel
import com.example.blinddate.setting.MyPageActivity
import com.example.blinddate.setting.SettingActivity
import com.example.blinddate.slider.CardStackAdapter
import com.example.blinddate.utils.FirebaseAuthUtils
import com.example.blinddate.utils.FirebaseRef
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction

class MainActivity : AppCompatActivity() {

    lateinit var cardStackAdapter: CardStackAdapter // 어뎁터 연결
    lateinit var manager: CardStackLayoutManager // View 를 어떻게 보이는 지 관리 LayoutManager 욕할
    private val usersDataList = mutableListOf<UserDataModel>()

    private val TAG = "MainActivity"

    private  var userCount = 0

    private val uid = FirebaseAuthUtils.getUid()

    private lateinit var currentUserGender: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val setting = findViewById<ImageView>(R.id.settingIcon)

        setting.setOnClickListener {
            val intent = Intent(this,SettingActivity::class.java)
            startActivity(intent)

        }


        val cardStackView = findViewById<CardStackView>(R.id.cardStackView)

        manager = CardStackLayoutManager(baseContext, object : CardStackListener{
            override fun onCardDragging(direction: Direction?, ratio: Float) {

            }

            override fun onCardSwiped(direction: Direction?) {
                if(direction == Direction.Right) {
                    Toast.makeText(this@MainActivity,"Like",Toast.LENGTH_LONG).show()
                    userLikeOtherUser(uid,usersDataList[userCount].uid.toString())
                }
                if(direction == Direction.Left) {
                    Toast.makeText(this@MainActivity,"Hate",Toast.LENGTH_LONG).show()
                }
                userCount+=1
                if (userCount == usersDataList.count()){
                    getUserDataList(currentUserGender)
                    Toast.makeText(this@MainActivity,"유저를 새롭게 받아옵니다",Toast.LENGTH_LONG).show()
                }
            }

            override fun onCardRewound() {

            }

            override fun onCardCanceled() {

            }

            override fun onCardAppeared(view: View?, position: Int) {

            }

            override fun onCardDisappeared(view: View?, position: Int) {

            }

        })



        cardStackAdapter = CardStackAdapter(baseContext,usersDataList)
        cardStackView.layoutManager = manager // 매니저에 우리가 만든 CardStackLayoutManager 연결
        cardStackView.adapter = cardStackAdapter //cardStackView에 있는 어댑터에 여기서 만든 cardStackAdapter 연결


        getMyUserData()
    }
    private fun getMyUserData(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.getValue(UserDataModel::class.java)

                currentUserGender = data?.gender.toString()

                getUserDataList(currentUserGender)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FirebaseRef.userInfoRef.child(uid).addValueEventListener(postListener)
    }



    private fun getUserDataList(currentUserGender : String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dataModel in dataSnapshot.children){

                    val user = dataModel.getValue(UserDataModel::class.java)


                    if (user!!.gender.toString() == currentUserGender){

                    }
                    else {
                        usersDataList.add(user!!) // 실시간 데이터베이스에서 읽고 거기서 담은 정보를 리스트에 넣어준다
                                                    // 같은 성별이 아닐 때만
                    }

                }
                cardStackAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FirebaseRef.userInfoRef.addValueEventListener(postListener)
    }
    private fun userLikeOtherUser(myUid : String,otherUid : String){
        FirebaseRef.userLikeRef.child(myUid).child(otherUid).setValue("true")
        getOtherUserLikeList(otherUid)
    }
    private fun getOtherUserLikeList(otherUid : String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dataModel in dataSnapshot.children){
                    val  likeUserKey = dataModel.key.toString()
                    if (likeUserKey==uid){
                        Toast.makeText(this@MainActivity,"매칭 완료",Toast.LENGTH_LONG).show()
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
}