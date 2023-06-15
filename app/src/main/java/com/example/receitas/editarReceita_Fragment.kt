package com.example.receitas

import android.database.Cursor
import android.net.Uri
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
import com.example.receitas.databinding.FragmentEditarReceitaBinding
private const val ID_LOADER_TIPODERECEITAS=0
class editarReceita_Fragment : Fragment(),LoaderManager.LoaderCallbacks<Cursor> {

    private var receitas: Receita? = null
    private var _binding: FragmentEditarReceitaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditarReceitaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_TIPODERECEITAS, null, this)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_guardar_cancelar

        val receita = editarReceita_FragmentArgs.fromBundle(requireArguments()).receitas

        if (receita != null) {
            binding.textViewNomeReceita.setText(receita.nome)
        }

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
        findNavController().navigate(R.id.action_editarReceita_Fragment_to_listaReceitasFragmento)
    }

    private fun guardar() {
        val nome = binding.textViewNomeReceita.text.toString()
        if (nome.isBlank()) {
            binding.textViewNomeReceita.error = getString(R.string.nome_obrigatorio)
            binding.textViewNomeReceita.requestFocus()
            return
        }

        val tipoReceita = binding.spinnerTipoReceita.selectedItemId

        if (tipoReceita == Spinner.INVALID_ROW_ID) {
            binding.textViewTipoReceita.error = getString(R.string.tipo_de_receita_obrigatorio)
            binding.spinnerTipoReceita.requestFocus()
            return
        }
        if (receitas == null) {
            val receita = Receita(
                "",
                TipoDeReceita("",tipoReceita),
                ""
            )
            insereReceita(receita)
        }else{
            val receita = receitas!!
            receita.nome = nome
            receita.tipoDeReceita = TipoDeReceita("?",tipoReceita)

            alteraReceita(receita)
        }
    }
    private fun alteraReceita(
        receita: Receita
    ) {
        val enderecoReceita = Uri.withAppendedPath(ReceitasContentProvider.ENDERECO_RECEITA, receita.id.toString())
        val receitasAlteradas = requireActivity().contentResolver.update(enderecoReceita, receita.toContentValues(), null,null)

        if(receitasAlteradas == 1){
            Toast.makeText(requireContext(), R.string.receita_guardada_com_seucesso, Toast.LENGTH_LONG).show()
            voltaListaReceitas()
        }else{
            binding.textViewNomeReceita.error = getString(R.string.n_o_foi_possivel_guardar_receita)
        }
    }
    private fun insereReceita(
        receita: Receita
    ){

        val id = requireActivity().contentResolver.insert(
            ReceitasContentProvider.ENDERECO_RECEITA,
            receita.toContentValues()
        )

        Toast.makeText(requireContext(), getString(R.string.receita_guardada_com_seucesso), Toast.LENGTH_LONG).show()
        if (id == null) {
            binding.textViewNomeReceita.error =
                getString(R.string.n_o_foi_possivel_guardar_receita)
            return
        }

        Toast.makeText(
            requireContext(),
            getString(R.string.receita_guardada_com_seucesso),
            Toast.LENGTH_LONG
        ).show()
        voltaListaReceitas()
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ReceitasContentProvider.ENDERECO_TIPODERECEITA,
            TabelaTipoDeReceitas.CAMPOS,
            null, null,
            TabelaTipoDeReceitas.CAMPO_NOME
        )
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        if (_binding != null) {
            binding.spinnerTipoReceita.adapter = null
        }
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if (data == null) {
            binding.spinnerTipoReceita.adapter = null
            return
        }
        binding.spinnerTipoReceita.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaTipoDeReceitas.CAMPO_NOME),
            intArrayOf(android.R.id.text1),
            0
        )
        mostraTipoDeReceitaSelecionadaSpinner()
    }

    private fun mostraTipoDeReceitaSelecionadaSpinner() {
        if (receitas == null) return

        val idTipoDeReceita = receitas!!.tipoDeReceita.id

        val ultimoTipoDeReceita = binding.spinnerTipoReceita.count - 1
        for (i in 0..ultimoTipoDeReceita) {
            if (idTipoDeReceita == binding.spinnerTipoReceita.getItemIdAtPosition(i)) {
                binding.spinnerTipoReceita.setSelection(i)
                return
            }
        }
    }
}