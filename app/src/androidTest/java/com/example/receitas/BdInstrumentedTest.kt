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
         //getAppContext().deleteDatabase(BdReceitasOpenHelper.NOME_BASE_DADOS)
    }
    @Test
    fun consegueInserirReceita(){
        val bd=getWritableDataBase()

        val tipoDeReceita=TipoDeReceita("Entrada")
        insereTipoDeReceita(bd,tipoDeReceita)

        val receita1=Receita("Bacalhau com natas",tipoDeReceita, "sobremesa")
        insereReceita(bd,receita1)

        val receita2=Receita("Bacalhau com natas1",tipoDeReceita, "sobremesa1")
        insereReceita(bd,receita2)
    }

    private fun insereReceita(bd:SQLiteDatabase,receita: Receita){
        receita.id=TabelaReceitas(bd).insere(receita.toContentValues())
        assertNotEquals(-1,receita.id)
    }

    @Test
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
    @Test
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

        val cursor=TabelaTipoDeReceitas(bd).consulta(
            TabelaTipoDeReceitas.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(tipoDeReceitaSobremesa.id.toString()),
            null,
            null,
            null
        )
        assert(cursor.moveToNext())

        val tipoDeReceitaBD=TipoDeReceita.fromCursor(cursor)

        assertEquals(tipoDeReceitaSobremesa,tipoDeReceitaBD)

        val cursorTodosTiposDeReceitas=TabelaTipoDeReceitas(bd).consulta(
            TabelaTipoDeReceitas.CAMPOS,
            null,
            null,
            null,
            null,
            TabelaTipoDeReceitas.CAMPO_NOME
        )

        assert(cursorTodosTiposDeReceitas.count > 1)
    }
    @Test
    fun consegueLerReceita(){
        val bd = getWritableDataBase()

        val tipoDeReceita = TipoDeReceita("Pequeno-Almoço")
        insereTipoDeReceita(bd, tipoDeReceita)

        val receita=Receita("Ovos mexidos",tipoDeReceita,"Ovos,sal")
        insereReceita(bd,receita)

        val receita2=Receita("Ovo estrelado",tipoDeReceita,"Ovos,sal,oleo")
        insereReceita(bd,receita2)

            val cursor = TabelaReceitas(bd).consulta(
                TabelaReceitas.CAMPOS,
            "${TabelaReceitas.CAMPO_ID}=?",
                arrayOf(receita2.id.toString()),
            null,
            null,
            null)

        assert(cursor.moveToNext())

        val receitaBD = Receita.fromCursor(cursor)

        assertEquals(receita2, receitaBD)

        val cursorTodasReceitas = TabelaReceitas(bd).consulta(
            TabelaReceitas.CAMPOS,
            null,
            null,
            null,
            null,
            TabelaReceitas.CAMPO_NOME
        )

        assert(cursorTodasReceitas.count > 1)
    }
    @Test
    fun consegueAlterarTipoDeReceita(){
        val bd=getWritableDataBase()

        val tipoDeReceita=TipoDeReceita("...")
        insereTipoDeReceita(bd,tipoDeReceita)

        tipoDeReceita.nome="Aperitivo"

        val registosAlterados=TabelaTipoDeReceitas(bd).altera(
            tipoDeReceita.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(tipoDeReceita.id.toString()))

        assertEquals(1,registosAlterados)
    }
    @Test
    fun consegueAlterarReceita(){
        val bd=getWritableDataBase()

        val tipoDeReceita=TipoDeReceita("ceia")
        insereTipoDeReceita(bd,tipoDeReceita)

        val tipoDeReceitaSobremesa=TipoDeReceita("Sobremesa")
        insereTipoDeReceita(bd,tipoDeReceitaSobremesa)

        val receita= Receita("novoNome",tipoDeReceitaSobremesa,"novaDescricao")
        insereReceita(bd,receita)

        receita.tipoDeReceita=tipoDeReceitaSobremesa
        receita.nome="Bacalhau"

        val registosAlterados=TabelaReceitas(bd).altera(
            receita.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(receita.id.toString()))

        assertEquals(1,registosAlterados)

    }
    @Test
    fun consegueApagarTipoDeReceita(){
        val bd=getWritableDataBase()

        val tipoDeReceita=TipoDeReceita("...")
        insereTipoDeReceita(bd,tipoDeReceita)


        val registosEliminados=TabelaTipoDeReceitas(bd).eliminar(
            "${BaseColumns._ID}=?",
            arrayOf(tipoDeReceita.id.toString()))

        assertEquals(1,registosEliminados)
    }

    @Test
    fun consegueApagarReceita(){
        val bd=getWritableDataBase()

        val tipoDeReceita=TipoDeReceita("ceia")
        insereTipoDeReceita(bd,tipoDeReceita)

        val receita= Receita("...",tipoDeReceita,"descrição")
        insereReceita(bd,receita)

        val registosEliminados=TabelaReceitas(bd).eliminar(
            "${BaseColumns._ID}=?",
            arrayOf(receita.id.toString()))

        assertEquals(1,registosEliminados)
    }
}