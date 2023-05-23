package com.example.receitas

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class ReceitasContentProvider :ContentProvider(){

    private var bdOpenHelper : BdReceitasOpenHelper? = null
    override fun onCreate(): Boolean {
        bdOpenHelper = BdReceitasOpenHelper(context)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val bd = bdOpenHelper!!.readableDatabase

        val endereco = uriMatcher().match(uri)

        val tabela = when (endereco) {
            URI_TIPODERECEITAS, URI_TIPODERECEITAS_ID -> TabelaTipoDeReceitas(bd)
            URI_RECEITAS, URI_RECEITAS_ID -> TabelaReceitas(bd)
            else -> null
        }

        val id = uri.lastPathSegment

        val (selecao,argsSel) = when (endereco) {
            URI_TIPODERECEITAS_ID, URI_RECEITAS_ID -> Pair("${BaseColumns._ID}=?",arrayOf(id))
            else -> Pair(selection,selectionArgs)
        }

        return tabela?.consulta(
            projection as Array<String>,
            selecao,
            argsSel as Array<String>?,
            null,
            null,
            sortOrder)
    }

    override fun getType(uri: Uri): String? {
        val endereco = uriMatcher().match(uri)

        return when(endereco){
            URI_TIPODERECEITAS_ID->"vnd.android.cursor.item/$TIPODERECEITAS"
            URI_TIPODERECEITAS->"vnd.android.cursor.dir/$TIPODERECEITAS"
            URI_RECEITAS->"vnd.android.cursor.dir/$RECEITAS"
            URI_RECEITAS_ID->"vnd.android.cursor.item/$RECEITAS"
            else->null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val bd = bdOpenHelper!!.writableDatabase

        val endereco = uriMatcher().match(uri)
        val tabela = when (endereco) {
            URI_TIPODERECEITAS-> TabelaTipoDeReceitas(bd)
            URI_RECEITAS-> TabelaReceitas(bd)
            else -> return null
        }

        val id=tabela.insere(values!!)
        if (id==-1L){
            return null
        }

        return Uri.withAppendedPath(uri,id.toString())

    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val bd = bdOpenHelper!!.writableDatabase

        val endereco = uriMatcher().match(uri)
        val tabela = when (endereco) {
            URI_TIPODERECEITAS_ID-> TabelaTipoDeReceitas(bd)
            URI_RECEITAS_ID-> TabelaReceitas(bd)
            else -> return 0
        }

        val id=uri.lastPathSegment!!
        return tabela.eliminar("${BaseColumns._ID}=?", arrayOf(id))
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val bd = bdOpenHelper!!.writableDatabase

        val endereco = uriMatcher().match(uri)
        val tabela = when (endereco) {
            URI_TIPODERECEITAS_ID-> TabelaTipoDeReceitas(bd)
            URI_RECEITAS_ID-> TabelaReceitas(bd)
            else -> return 0
        }

        val id=uri.lastPathSegment!!
        return tabela.altera(values!!,"${BaseColumns._ID}=?", arrayOf(id))
    }
    companion object{
        private const val AUTORIDADE="com.example.receitas"

        const val TIPODERECEITAS="TipoDeReceitas"
        const val RECEITAS="Receitas"

        private const val URI_TIPODERECEITAS=100
        private const val URI_TIPODERECEITAS_ID=101
        private const val URI_RECEITAS=200
        private const val URI_RECEITAS_ID=201


        fun uriMatcher()=UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTORIDADE, TIPODERECEITAS, URI_TIPODERECEITAS)
            addURI(AUTORIDADE, "$TIPODERECEITAS/#", URI_TIPODERECEITAS_ID)
            addURI(AUTORIDADE, RECEITAS, URI_RECEITAS)
            addURI(AUTORIDADE, "$RECEITAS/#", URI_RECEITAS_ID)

        }
    }
}