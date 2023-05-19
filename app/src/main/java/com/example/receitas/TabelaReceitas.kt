package com.example.receitas

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


class TabelaReceitas(db: SQLiteDatabase):TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA,$CAMPO_NOME TEXT NOT NULL, $CAMPO_DESCRICAO TEXT, $CAMPO_FK_IDTIPODERECEITA INTEGER NOT NULL , FOREIGN KEY(id_TipoDeReceita) REFERENCES ${TabelaTipoDeReceitas.NOME_TABELA}(${BaseColumns._ID}))")
    }
    companion object{
        private const val NOME_TABELA = "Receitas"
        const val CAMPO_NOME="nome"
        const val CAMPO_DESCRICAO="descricao"
        const val CAMPO_FK_IDTIPODERECEITA="id_TipoDeReceita"

        val CAMPOS= arrayOf(BaseColumns._ID,CAMPO_NOME, CAMPO_DESCRICAO, CAMPO_FK_IDTIPODERECEITA)
    }
}