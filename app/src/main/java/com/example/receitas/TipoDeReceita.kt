package com.example.receitas

import android.content.ContentValues

data class TipoDeReceita(
    var nome:String,
    var id:Long=-1
    ) {
    fun toContentValues(): ContentValues {
        val valores=ContentValues()

        valores.put(TabelaTipoDeReceitas.CAMPO_NOME,nome)


        return valores
    }
}