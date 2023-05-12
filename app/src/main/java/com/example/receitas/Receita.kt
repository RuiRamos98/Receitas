package com.example.receitas

import android.content.ContentValues

class Receita (
    var idTipoDeReceita:Int,
    var descricao:String,
    var nome:String,
    var id: Long=-1
        ){
    fun toContentValues(){
        val valores = ContentValues()

        valores.put(TabelaReceitas.CAMPO_NOME,nome)
        valores.put(TabelaReceitas.CAMPO_DESCRICAO,descricao)
        valores.put(TabelaReceitas.CAMPO_FK_IDRECEITA,idTipoDeReceita)

        
    }

}

