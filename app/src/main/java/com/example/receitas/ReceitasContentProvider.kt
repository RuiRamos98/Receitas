package com.example.receitas

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

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
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }
    companion object{
        private const val AUTORIDADE="com.example.receitas"

        const val TIPODERECEITAS="TipoDeReceitas"
        const val RECEITAS="Receitas"

        private const val URI_TIPODERECEITAS=100
        private const val URI_RECEITAS=200


        fun uriMatcher()=UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTORIDADE, TIPODERECEITAS, URI_TIPODERECEITAS)
            addURI(AUTORIDADE, RECEITAS, URI_RECEITAS)
        }
    }
}