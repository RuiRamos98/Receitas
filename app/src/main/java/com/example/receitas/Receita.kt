package com.example.receitas

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Receita(
    var nome:String,
    var tipoDeReceita:TipoDeReceita,
    var descricao: String,
    var id: Long=-1
        ){
    fun toContentValues(): ContentValues {
        val valores = ContentValues()

        valores.put(TabelaReceitas.CAMPO_NOME,nome)
        valores.put(TabelaReceitas.CAMPO_DESCRICAO,descricao)
        valores.put(TabelaReceitas.CAMPO_FK_IDTIPODERECEITA,tipoDeReceita.id)

        return valores
    }
    companion object{
        fun fromCursor(cursor: Cursor):Receita{
            val posId =cursor.getColumnIndex(BaseColumns._ID)
            val posNome =cursor.getColumnIndex(TabelaReceitas.CAMPO_NOME)
            val posDescricao =cursor.getColumnIndex(TabelaReceitas.CAMPO_DESCRICAO)
            val posFkIdTipoDeReceita =cursor.getColumnIndex(TabelaReceitas.CAMPO_FK_IDTIPODERECEITA)
            val posNomeTipoDeReceita = cursor.getColumnIndex(TabelaReceitas.CAMPO_NOME_TIPODERECEITA)

            val id=cursor.getLong(posId)
            val nome=cursor.getString(posNome)
            val descricao=cursor.getString(posDescricao)
            val fkIdTipoDeReceita=cursor.getLong(posFkIdTipoDeReceita)
            val nomeTipoDeReceita = cursor.getString(posNomeTipoDeReceita)

            return Receita(nome,TipoDeReceita(nomeTipoDeReceita),descricao.toString(),fkIdTipoDeReceita)
        }
    }
}

