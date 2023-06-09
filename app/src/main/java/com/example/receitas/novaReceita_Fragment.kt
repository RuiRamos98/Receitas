package com.example.receitas

import android.database.Cursor
import android.opengl.GLES30
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.receitas.databinding.FragmentListaReceitasFragmentoBinding
import com.example.receitas.databinding.FragmentNovaReceitaBinding
private const val ID_LOADER_TIPODERECEITAS=0
class novaReceita_Fragment : Fragment(),LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentNovaReceitaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNovaReceitaBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spinnerTipoReceita.adapter=

        val loader=LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_TIPODERECEITAS,null,this)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_guardar_cancelar
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                cancelar()
                true
            }
            else -> false
        }

    }

    private fun cancelar() {
        findNavController().navigate(R.id.action_novaReceita_Fragment_to_listaReceitasFragmento)
    }

    private fun guardar() {
        TODO("Not yet implemented")
    }
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ReceitasContentProvider.ENDERECO_TIPODERECEITA,
            TabelaReceitas.CAMPOS,
            null,null,
            TabelaTipoDeReceitas.CAMPO_NOME
        )
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        binding.
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if(data==null){
            binding.spinnerTipoReceita.adapter=null
        }
        binding.spinnerTipoReceita.adapter=SimpleCursorAdapter{
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaTipoDeReceitas.CAMPO_NOME),
            intArrayOf(android.R.id.text1),
            0
        }
    }
}