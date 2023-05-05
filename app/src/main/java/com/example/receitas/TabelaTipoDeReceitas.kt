package com.example.receitas

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

private const val NOME_TABELA = "TipoDeReceitas"

class TabelaTipoDeReceitas(db:SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA($CHAVE_TABELA,nome TEXT NOT NULL)")
    }
}