package com.example.blinddate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.blinddate.slider.CardStackAdapter
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction

class MainActivity : AppCompatActivity() {

    lateinit var cardStackAdapter: CardStackAdapter // 어뎁터 연결
    lateinit var manager: CardStackLayoutManager // View 를 어떻게 보이는 지 관리 LayoutManager 욕할

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        val test = mutableListOf<String>()
        test.add("a")
        test.add("b")
        test.add("c")

        cardStackAdapter = CardStackAdapter(baseContext,test)
        cardStackView.layoutManager = manager // 매니저에 우리가 만든 CardStackLayoutManager 연결
        cardStackView.adapter = cardStackAdapter //cardStackView에 있는 어댑터에 여기서 만든 cardStackAdapter 연결
    }
}