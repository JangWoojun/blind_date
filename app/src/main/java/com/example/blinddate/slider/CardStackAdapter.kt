package com.example.blinddate.slider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.blinddate.R
import com.example.blinddate.auth.UserDataModel

class CardStackAdapter (val context : Context, val items : List<UserDataModel>) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStackAdapter.ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.item_card,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardStackAdapter.ViewHolder, position: Int) {
        holder.binding(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nickname = itemView.findViewById<TextView>(R.id.itemNickname)
        val age = itemView.findViewById<TextView>(R.id.itemAge)
        val city = itemView.findViewById<TextView>(R.id.itemCity)
        // 아이템 카드에 있는 부분을 데이터 안에 들어있는 정보로 교체 여기서 data는 items에 하나하나들이다
        fun binding(data : UserDataModel){
            nickname.text = data.nickname
            age.text = data.age
            city.text = data.city

        }
    }
}