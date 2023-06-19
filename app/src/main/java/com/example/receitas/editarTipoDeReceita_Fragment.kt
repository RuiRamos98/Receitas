package com.example.receitas

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.receitas.databinding.FragmentEditarTipoDeReceitaBinding

class editarTipoDeReceita_Fragment : Fragment() {

    private var tipoDeReceitas: TipoDeReceita? = null
    private var _binding: FragmentEditarTipoDeReceitaBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditarTipoDeReceitaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_guardar_cancelar

        val tipoDeReceita = editarTipoDeReceita_FragmentArgs.fromBundle(requireArguments()).tipoDeReceitas

        if (tipoDeReceita != null) {
            activity.atualizaNome(R.string.editar_tipo_de_receita)

            binding.editTextTipoDeReceita.setText(tipoDeReceita.nome)
        } else {
            activity.atualizaNome(R.string.editar_label)
        }

        this.tipoDeReceitas = tipoDeReceitas
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun voltarlistaTipoDeReceitas() {
        findNavController().navigate(R.id.action_editarTipoDeReceita_to_listaTipoDeReceita)
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_guardar -> {
                guardarTipoDeReceita()
                true
            }

            R.id.action_cancelar -> {
                voltarlistaTipoDeReceitas()
                true
            }

            else -> false
        }
    }

    private fun guardarTipoDeReceita() {
        val nome = binding.editTextTipoDeReceita.text.toString()
        if (nome.isBlank()) {
            binding.editTextTipoDeReceita.error = getString(R.string.nomeTR_obrigatorio)
            binding.editTextTipoDeReceita.requestFocus()
            return
        }
        if (tipoDeReceitas == null) {
            val tipoDeReceita = TipoDeReceita(nome)

            insereTipoDeReceita(tipoDeReceita)
        } else {
            val tipoDeReceita = tipoDeReceitas!!
            tipoDeReceita.nome = nome

            alteraTipoDeReceita(tipoDeReceita)
        }

    }

    private fun alteraTipoDeReceita(tipoDeReceita: TipoDeReceita) {
        val enderecoTipoDeReceita = Uri.withAppendedPath(
            ReceitasContentProvider.ENDERECO_TIPODERECEITA,
            tipoDeReceita.id.toString()
        )
        val TipoDeReceitasAlteradas = requireActivity().contentResolver.update(
            enderecoTipoDeReceita,
            tipoDeReceita.toContentValues(),
            null,
            null
        )

        if (TipoDeReceitasAlteradas == 1) {
            Toast.makeText(
                requireContext(),
                getString(R.string.tipo_de_receita_alterada_com_sucesso),
                Toast.LENGTH_LONG
            ).show()
            voltarlistaTipoDeReceitas()
        } else {
            binding.editTextTipoDeReceita.error = getString(R.string.imposivel_guardar_tipo_de_receita)
        }
    }

    private fun insereTipoDeReceita(tipoDeReceita: TipoDeReceita) {
        val id = requireActivity().contentResolver.insert(
            ReceitasContentProvider.ENDERECO_TIPODERECEITA,
            tipoDeReceita.toContentValues()
        )

        if (id == null) {
            binding.editTextTipoDeReceita.error =
                getString(R.string.imposivel_guardar_tipo_de_receita)
            return
        }


        Toast.makeText(requireContext(), getString(R.string.tipo_de_receita_saved), Toast.LENGTH_LONG)
            .show()
        Toast.makeText(
            requireContext(),
            getString(R.string.tipo_de_receita_saved),
            Toast.LENGTH_LONG
        ).show()
        voltarlistaTipoDeReceitas()
    }
}