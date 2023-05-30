package com.example.receitas

import android.database.Cursor
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.w3c.dom.Text

class AdapterReceitas(val fragment: ListaReceitasFragmento) : RecyclerView.Adapter<AdapterReceitas.ViewHolderReceita>() {
    var cursor: Cursor? = null
    set(value){
        field =value
        notifyDataSetChanged()
    }

    inner class ViewHolderReceita(contentor: View) : ViewHolder(contentor) {
        private val textViewNome=contentor.findViewById<TextView>(R.id.textViewTipo)
        private val textViewTipoDeReceita=contentor.findViewById<TextView>(R.id.textViewTipoDeReceita)

        internal var receita:Receita?=null
            set(value){
                field=value
                textViewNome.text=receita?.nome
                textViewTipoDeReceita.text=receita?.idTipoDeReceita.toString()?:""
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderReceita {
        return ViewHolderReceita(
            fragment.layoutInflater.inflate(R.layout.item_receita,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return cursor?.count ?:0
    }

    override fun onBindViewHolder(holder: ViewHolderReceita, position: Int) {
        cursor!!.move(position)
        holder.receita=Receita.fromCursor(cursor!!)
    }
}