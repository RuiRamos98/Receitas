package com.example.receitas

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


class TabelaReceitas(db: SQLiteDatabase):TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA,nome TEXT NOT NULL, descrição TEXT, id_TipoDeReceita INTEGER NOT NULL , FOREIGN KEY(id_TipoDeReceita) REFERENCES ${TabelaTipoDeReceitas.NOME_TABELA}(${BaseColumns._ID}))")
    }
    companion object{
        private const val NOME_TABELA = "Receitas"
    }
}
