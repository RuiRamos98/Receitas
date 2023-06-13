package com.example.receitas

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterTipoDeReceitas(val fragment:ListaTipoDeReceitaFragmento): RecyclerView.Adapter<AdapterTipoDeReceitas.ViewHolderTipoDeReceitas>() {

        var cursor: Cursor? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

        inner class ViewHolderTipoDeReceitas(contentor:View) : RecyclerView.ViewHolder(contentor) {
            private val textViewNomeTipoDeReceita = contentor.findViewById<TextView>(R.id.textView_NomeTipoDeReceita)

            init {
                contentor.setOnClickListener{
                    viewHolderSeleccionado?.desSeleciona()
                    seleciona()
                }
            }

            internal var tipoDeReceitas: TipoDeReceita? = null
                set(value){
                    field = value
                    textViewNomeTipoDeReceita.text = tipoDeReceitas?.nome ?: ""
                }

            fun seleciona(){
                viewHolderSeleccionado = this
                fragment.tipoDeReceitaSelecionado = tipoDeReceitas
                itemView.setBackgroundResource(R.color.item_selecionado)
            }

            fun desSeleciona(){
                itemView.setBackgroundResource(android.R.color.white)
            }
        }

        private var viewHolderSeleccionado : AdapterTipoDeReceitas.ViewHolderTipoDeReceitas? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterTipoDeReceitas.ViewHolderTipoDeReceitas {
            return ViewHolderTipoDeReceitas(
                fragment.layoutInflater.inflate(R.layout.item_tipodereceita, parent, false)
            )
        }

        override fun getItemCount(): Int {
            return cursor?.count ?: 0
        }

        override fun onBindViewHolder(holder: AdapterTipoDeReceitas.ViewHolderTipoDeReceitas, position: Int) {
            cursor!!.moveToPosition(position)
            holder.tipoDeReceitas=TipoDeReceita.fromCursor(cursor!!)
        }
}