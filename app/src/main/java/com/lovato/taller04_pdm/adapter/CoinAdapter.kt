package com.lovato.taller04_pdm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lovato.taller04_pdm.R
import com.lovato.taller04_pdm.models.Coin
import kotlinx.android.synthetic.main.item_list_coin.view.*

class CoinAdapter(var items: List<Coin>,var listener: (Coin)-> Unit):
    RecyclerView.Adapter<CoinAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_coin,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bin(items[position],listener)
    }


    fun setData(items: List<Coin>){
        this.items = items
        notifyDataSetChanged()
    }

    class ViewHolder(var item: View): RecyclerView.ViewHolder(item){
        fun bin(coin: Coin, listener: (Coin) -> Unit){
            with(item){
                title_list_item.text = coin.name
                country_list_item.text = coin.country
                setOnClickListener {
                    listener(coin)
                }
            }
        }
    }



}