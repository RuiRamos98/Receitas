package com.example.receitas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BdInstrumentedTest {

    private fun getAppContext (): Context =
        InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun apagaBaseDados(){
        //
        getAppContext().deleteDatabase(BdReceitasOpenHelper.NOME_BASE_DADOS)
    }
    @Test
    fun consegueInserirReceita(){
        val bd=getWritableDataBase()

        val tipoDeReceita=TipoDeReceita("Entrada")
        insereTipoDeReceita(bd,tipoDeReceita)

        val receita=Receita("Bacalhau com natas",tipoDeReceita.id, "sobremesa")
        insereReceita(bd,receita)
    }

    private fun insereReceita(bd:SQLiteDatabase,receita: Receita){
        receita.id=TabelaReceitas(bd).insere(receita.toContentValues())
        assertNotEquals(-1,receita.id)
    }


    fun consegueInserirTipoDeReceita(){
        val bd = getWritableDataBase()

        val tipoDeReceita = TipoDeReceita("Sobremesa")
        insereTipoDeReceita(bd, tipoDeReceita)
    }

    private fun insereTipoDeReceita(
        bd: SQLiteDatabase,
        tipoDeReceita: TipoDeReceita
    ) {
        tipoDeReceita.id = TabelaTipoDeReceitas(bd).insere(tipoDeReceita.toContentValues())
        assertNotEquals(-1, tipoDeReceita.id)
    }

    private fun getWritableDataBase(): SQLiteDatabase {
        val openHelper = BdReceitasOpenHelper(getAppContext())
        return openHelper.writableDatabase
    }

    fun consegueAbrirBaseDados(){
        val openHelper=BdReceitasOpenHelper(getAppContext())
        val bd=openHelper.readableDatabase
        assert(bd.isOpen)
    }
    fun useAppContext() {
        // Context of the app under test.
        val appContext = getAppContext()
        assertEquals("com.example.receitas", appContext.packageName)
    }

    @Test
    fun consegueLerTipoDeReceita(){
        val bd=getWritableDataBase()

        val tipoDeReceitaEntrada=TipoDeReceita("Entrada")
        insereTipoDeReceita(bd,tipoDeReceitaEntrada)

        val tipoDeReceitaSobremesa=TipoDeReceita("Sobremesa")
        insereTipoDeReceita(bd,tipoDeReceitaSobremesa)

        val cursor=TabelaReceitas(bd).consulta(TabelaTipoDeReceitas.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(tipoDeReceitaSobremesa.id.toString()),
            null,
            null,
            null
        )
        assert(cursor.moveToNext())

        val tipoReceitaBD=TipoDeReceita.fromCursor(cursor)

        assertEquals(tipoDeReceitaSobremesa,tipoReceitaBD)

        val cursorTodosTiposDeReceitas=TabelaTipoDeReceita.consulta(
            TabelaTipoDeReceitas.CAMPOS,
            null,null,null,null,
            TabelaTipoDeReceitas.CAMPO_NOME
        )

        assert(cursorTodosTiposDeReceitas.count > 1)
    }
}