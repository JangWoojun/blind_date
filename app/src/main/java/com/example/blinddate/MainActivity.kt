package com.example.blinddate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.example.blinddate.auth.IntroActivity
import com.example.blinddate.auth.UserDataModel
import com.example.blinddate.slider.CardStackAdapter
import com.example.blinddate.utils.FirebaseRef
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction

class MainActivity : AppCompatActivity() {

    lateinit var cardStackAdapter: CardStackAdapter // 어뎁터 연결
    lateinit var manager: CardStackLayoutManager // View 를 어떻게 보이는 지 관리 LayoutManager 욕할
    private val usersDataList = mutableListOf<UserDataModel>()

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val setting = findViewById<ImageView>(R.id.settingIcon)

        setting.setOnClickListener {
            val auth = Firebase.auth
            auth.signOut()

            val intent = Intent(this,IntroActivity::class.java)
            startActivity(intent)
        }


        val cardStackView = findViewById<CardStackView>(R.id.cardStackView)

        manager = CardStackLayoutManager(baseContext, object : CardStackListener{
            override fun onCardDragging(direction: Direction?, ratio: Float) {

            }

            override fun onCardSwiped(direction: Direction?) {

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

        getUserDataList()
    }
    private fun getUserDataList(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dataModel in dataSnapshot.children){

                    val user = dataModel.getValue(UserDataModel::class.java)
                    usersDataList.add(user!!) // 실시간 데이터베이스에서 읽고 거기서 담은 정보를 리스트에 넣어준다
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
}