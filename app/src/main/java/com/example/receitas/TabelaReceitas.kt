package com.example.receitas

import android.database.sqlite.SQLiteDatabase
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns


class TabelaReceitas(db: SQLiteDatabase):TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_NOME TEXT NOT NULL,$CAMPO_DESCRICAO TEXT NOT NULL, $CAMPO_FK_IDTIPODERECEITA INTEGER NOT NULL, FOREIGN KEY ($CAMPO_FK_IDTIPODERECEITA) REFERENCES ${TabelaTipoDeReceitas.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    override fun consulta(
        colunas: Array<String>,
        selecao: String?,
        argsSelecao: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor {
        val sql = SQLiteQueryBuilder()
        sql.tables = "$NOME_TABELA INNER JOIN ${TabelaTipoDeReceitas.NOME_TABELA} ON ${TabelaTipoDeReceitas.CAMPO_ID} = $CAMPO_FK_IDTIPODERECEITA"

        return sql.query(db, colunas, selecao, argsSelecao, groupBy, having, orderBy)
    }
    companion object{
        private const val NOME_TABELA = "Receitas"
        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"
        const val CAMPO_NOME="NomeReceita"
        const val CAMPO_DESCRICAO="descricao"
        const val CAMPO_FK_IDTIPODERECEITA="id_TipoDeReceita"
        const val CAMPO_NOME_TIPODERECEITA = TabelaTipoDeReceitas.CAMPO_ID
        const val CAMPO_DESC_TIPODERECEITA = TabelaTipoDeReceitas.CAMPO_NOME

        val CAMPOS = arrayOf(CAMPO_ID, CAMPO_NOME,CAMPO_DESCRICAO, CAMPO_FK_IDTIPODERECEITA, CAMPO_NOME_TIPODERECEITA, CAMPO_DESC_TIPODERECEITA)
    }
}