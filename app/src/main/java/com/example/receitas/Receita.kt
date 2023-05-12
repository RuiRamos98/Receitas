package com.example.receitas

import android.content.ContentValues

class Receita (
    var nome:String,
    var idTipoDeReceita:Long,
    var descricao:String,
    var id: Long=-1
        ){
    fun toContentValues(): ContentValues {
        val valores = ContentValues()

        valores.put(TabelaReceitas.CAMPO_NOME,nome)
        valores.put(TabelaReceitas.CAMPO_DESCRICAO,descricao)
        valores.put(TabelaReceitas.CAMPO_FK_IDRECEITA,idTipoDeReceita)

        return valores
    }

}

