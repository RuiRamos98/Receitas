package com.example.receitas

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
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
                voltaListaReceitas()
                true
            }
            else -> false
        }

    }

    private fun voltaListaReceitas() {
        findNavController().navigate(R.id.action_novaReceita_Fragment_to_listaReceitasFragmento)
    }

    private fun guardar() {
        val nome=binding.textViewNomeReceita.text.toString()
        if(nome.isBlank()){
            binding.textViewNomeReceita.error=getString(R.string.nome_obrigatorio)
            binding.textViewNomeReceita.requestFocus()
            return
        }

        val tipoReceita=binding.spinnerTipoReceita.selectedItemId
        if(tipoReceita==Spinner.INVALID_ROW_ID){
            binding.textViewTipoReceita.error=getString(R.string.tipo_de_receita_obrigatorio)
            binding.spinnerTipoReceita.requestFocus()
            return
        }

        val receita=Receita(
            nome,
            TipoDeReceita("?",tipoReceita),
            "?"
        )

        val id = requireActivity().contentResolver.insert(
            ReceitasContentProvider.ENDERECO_RECEITA,
            receita.toContentValues()
        )
        if (id == null) {
            binding.textViewNomeReceita.error = getString(R.string.n_o_foi_possivel_guardar_receita)
            return
        }

        Toast.makeText(requireContext(), getString(R.string.receita_guardada_com_seucesso), Toast.LENGTH_SHORT).show()
        voltaListaReceitas()
    }
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ReceitasContentProvider.ENDERECO_TIPODERECEITA,
            TabelaTipoDeReceitas.CAMPOS,
            null,null,
            TabelaTipoDeReceitas.CAMPO_NOME
        )
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        binding.spinnerTipoReceita.adapter=null
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if(data==null){
            binding.spinnerTipoReceita.adapter=null
            return
        }
        binding.spinnerTipoReceita.adapter=SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaTipoDeReceitas.CAMPO_NOME),
            intArrayOf(android.R.id.text1),
            0
        )

    }
}