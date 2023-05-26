package com.example.receitas

import android.app.LoaderManager
import android.content.Loader
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.receitas.databinding.FragmentListaReceitasFragmentoBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private const val ID_LOADER_LIVROS=0

/**
 * A simple [Fragment] subclass.
 * Use the [ListaReceitasFragmento.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaReceitasFragmento : Fragment(),androidx.loader.app.LoaderManager.LoaderCallbacks<Cursor> {
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
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_receitas_fragmento, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapterReceitas=AdapterReceitas()
        binding.recyclerViewReceitas.adapter=adapterReceitas
        binding.recyclerViewReceitas.layoutManager=LinearLayoutManager(requireContext())

        val loader=androidx.loader.app.LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_LIVROS,null,this)
    }

    companion object {

    }

    override fun onCreateLoader(id: Int, args: Bundle?): androidx.loader.content.Loader<Cursor> {
        TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: androidx.loader.content.Loader<Cursor>) {
        TODO("Not yet implemented")
    }

    override fun onLoadFinished(loader: androidx.loader.content.Loader<Cursor>, data: Cursor?) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}