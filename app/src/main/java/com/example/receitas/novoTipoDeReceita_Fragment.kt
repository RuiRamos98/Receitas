package com.example.receitas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.receitas.databinding.FragmentNovoTipoDeReceitaBinding

/**
 * A simple [Fragment] subclass.
 * Use the [novoTipoDeReceita_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class novoTipoDeReceita_Fragment : Fragment() {
    private var _binding: FragmentNovoTipoDeReceitaBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNovoTipoDeReceitaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_guardar_cancelar
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

        val tipoDeReceita = TipoDeReceita(nome)

        requireActivity().contentResolver.insert(ReceitasContentProvider.ENDERECO_TIPODERECEITA,tipoDeReceita.toContentValues())

        if (id == null){
            binding.editTextTipoDeReceita.error = getString(R.string.imposivel_guardar_tipo_de_receita)
            return
        }


        Toast.makeText(requireContext(), getString(R.string.tipo_de_receita_saved), Toast.LENGTH_LONG).show()
        voltarlistaTipoDeReceitas()
        }

    private fun voltarlistaTipoDeReceitas(){
        findNavController().navigate(R.id.action_novoTipoDeReceita_to_listaTipoDeReceita)
    }
}