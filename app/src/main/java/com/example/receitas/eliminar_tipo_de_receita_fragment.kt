package com.example.receitas

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.receitas.databinding.FragmentEliminarReceitaBinding
import com.google.android.material.snackbar.Snackbar

class eliminar_tipo_de_receita_fragment: Fragment() {

    private lateinit var tipoDeReceita: TipoDeReceita
    private var _binding: FragmentEliminarReceitaBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEliminarReceitaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eliminar

        tipoDeReceita =
            eliminar_tipo_de_receita_fragmentArgs.fromBundle(requireArguments()).tipoDeReceitas

        binding.textViewNomeTipoDeReceita.text = tipoDeReceita.nome
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_eliminar -> {
                eliminarTipoDeReceita()
                true
            }

            R.id.action_cancelar -> {
                voltaListaTipoDeReceitas()
                true
            }

            else -> false
        }
    }

    private fun voltaListaTipoDeReceitas() {
        findNavController().navigate(R.id.action_fragment_eliminar_tipo_de_receita_to_listaTipoDeReceita)
    }

    private fun eliminarTipoDeReceita() {
        val enderecoTipoDeReceitas = Uri.withAppendedPath(
            ReceitasContentProvider.ENDERECO_RECEITA,
            tipoDeReceita.id.toString()
        )
        val numTipoDeReceitasSelecionadas =
            requireActivity().contentResolver.delete(enderecoTipoDeReceitas, null, null)

        if (numTipoDeReceitasSelecionadas == 1) {
            Toast.makeText(
                requireContext(),
                getString(R.string.tipo_de_receita_eliminada_com_sucesso),
                Toast.LENGTH_LONG
            ).show()
            voltaListaTipoDeReceitas()
        } else {
            Snackbar.make(
                binding.textViewNomeTipoDeReceita,
                getString(R.string.erro_ao_eliminar_tipo_de_receita),
                Snackbar.LENGTH_INDEFINITE
            )

        }
    }
}