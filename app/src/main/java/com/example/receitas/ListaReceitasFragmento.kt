package com.example.receitas

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.receitas.databinding.FragmentListaReceitasFragmentoBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaReceitasFragmentoBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var adapterReceitas: AdapterReceitas?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterReceitas=AdapterReceitas(this)
        binding.recyclerViewReceitas.adapter=adapterReceitas
        binding.recyclerViewReceitas.layoutManager=LinearLayoutManager(requireContext())

        val loader=LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_RECEITAS,null,this)
    }

    companion object {

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
        adapterReceitas?.cursor = null
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterReceitas!!.cursor=data
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}