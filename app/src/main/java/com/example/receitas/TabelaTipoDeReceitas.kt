package com.example.receitas

import android.database.sqlite.SQLiteDatabase
class TabelaTipoDeReceitas(db:SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA($CHAVE_TABELA,$CAMPO_NOME TEXT NOT NULL)")
    }

    companion object{
        const val NOME_TABELA = "TipoDeReceitas"
        const val CAMPO_NOME="nome"
    }
}