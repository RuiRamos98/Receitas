package com.example.receitas

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.receitas.databinding.FragmentListaReceitasFragmentoBinding

private const val ID_LOADER_RECEITAS=0

/**
 * A simple [Fragment] subclass.
 * Use the [ListaReceitasFragmento.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaReceitasFragmento : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentListaReceitasFragmentoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaReceitasFragmentoBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    private var adapterReceitas: AdapterReceitas?=null
    var receitaSelecionado:Receita?=null
        set(value){
            field = value

            val mostrarEliminarAlterar = (value != null)
            val activity = activity as MainActivity
            activity.mostraBotaoMenu(R.id.action_editar,mostrarEliminarAlterar)
            activity.mostraBotaoMenu(R.id.action_eliminar,mostrarEliminarAlterar)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterReceitas=AdapterReceitas(this)
        binding.recyclerViewReceitas.adapter=adapterReceitas
        binding.recyclerViewReceitas.layoutManager=LinearLayoutManager(requireContext())

        val loader=LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_RECEITAS,null,this)
        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_lista_receitas
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ReceitasContentProvider.ENDERECO_RECEITA,
            TabelaReceitas.CAMPOS,
            null,null,
            TabelaReceitas.CAMPO_NOME
        )
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterReceitas!!.cursor = null
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterReceitas!!.cursor=data
    }
    fun processaOpcaoMenu(item: MenuItem) : Boolean{
        return when (item.itemId) {
            R.id.action_adicionar -> {
                adicionaReceita()
                true
            }
            R.id.action_editar -> {
                editarReceita()
                true
            }
            R.id.action_eliminar -> {
                eliminarReceita()
                true
            }
            else -> false
        }
    }



    private fun adicionaReceita() {
        findNavController().navigate(R.id.action_listaReceitasFragmento_to_editarReceita_Fragment)
    }
    private fun editarReceita() {
        TODO("Not yet implemented")
    }

    private fun eliminarReceita() {
        val acao =
            ListaReceitasFragmentoDirections.actionListaReceitasFragmentoToEliminarReceita(receitaSelecionado!!)
        findNavController().navigate(acao)
    }
}