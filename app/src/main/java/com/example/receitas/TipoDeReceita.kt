package com.example.receitas

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class TipoDeReceita(
    var nome:String,
    var id:Long=-1
    ) {
    fun toContentValues(): ContentValues {
        val valores=ContentValues()

        valores.put(TabelaTipoDeReceitas.CAMPO_NOME,nome)


        return valores
    }
    companion object{
        fun fromCursor(cursor: Cursor):TipoDeReceita{
            val posId =cursor.getColumnIndex(BaseColumns._ID)
            val posNome =cursor.getColumnIndex(TabelaTipoDeReceitas.CAMPO_NOME)

            val id=cursor.getLong(posId)
            val nome=cursor.getString(posNome)

            return TipoDeReceita(nome,id)
        }
    }
}