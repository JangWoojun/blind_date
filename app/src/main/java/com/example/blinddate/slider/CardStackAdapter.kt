package com.example.blinddate.slider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blinddate.R

class CardStackAdapter (val context : Context, val items : List<String>) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>(){
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
        fun binding(data : String){

        }
    }
}