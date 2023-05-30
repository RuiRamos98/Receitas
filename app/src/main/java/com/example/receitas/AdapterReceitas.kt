package com.example.receitas

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class AdapterReceitas: RecyclerView.Adapter<AdapterReceitas.ViewHolderReceita>() {
    var cursor: Cursor? = null
    set(value){
        field =value
        notifyDataSetChanged()
    }

    inner class ViewHolderReceita(itemView: View) : ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderReceita {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolderReceita, position: Int) {
        TODO("Not yet implemented")
    }
}