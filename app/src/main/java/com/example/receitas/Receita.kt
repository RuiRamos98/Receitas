package com.example.receitas

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Receita(
    var nome:String,
    var idTipoDeReceita:Long,
    var descricao: String,
    var id: Long=-1
        ){
    fun toContentValues(): ContentValues {
        val valores = ContentValues()

        valores.put(TabelaReceitas.CAMPO_NOME,nome)
        valores.put(TabelaReceitas.CAMPO_DESCRICAO,descricao)
        valores.put(TabelaReceitas.CAMPO_FK_IDTIPODERECEITA,idTipoDeReceita)

        return valores
    }
    companion object{
        fun fromCursor(cursor: Cursor):Receita{
            val posId =cursor.getColumnIndex(BaseColumns._ID)
            val posNome =cursor.getColumnIndex(TabelaReceitas.CAMPO_NOME)
            val posDescricao =cursor.getColumnIndex(TabelaReceitas.CAMPO_DESCRICAO)
            val posFkIdTipoDeReceita =cursor.getColumnIndex(TabelaReceitas.CAMPO_FK_IDTIPODERECEITA)

            val id=cursor.getLong(posId)
            val nome=cursor.getString(posNome)
            val descricao=cursor.getLong(posDescricao)
            val fkIdTipoDeReceita=cursor.getLong(posFkIdTipoDeReceita)

            return Receita(nome,id,descricao.toString(),fkIdTipoDeReceita)
        }
    }
}

