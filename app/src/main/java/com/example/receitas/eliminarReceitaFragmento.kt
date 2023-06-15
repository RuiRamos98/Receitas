package com.example.receitas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.receitas.databinding.FragmentEliminarReceitaBinding


class eliminarReceitaFragmento : Fragment() {

    private lateinit var receitas:Receita
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

        receitas = eliminarReceitaFragmentoArgs.fromBundle(requireArguments()).noticias

        binding.textViewNomeReceita.text = receitas.nome
        binding.textViewNomeTipoDeReceita.text = receitas.tipoDeReceita.nome

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_eliminar -> {
                eliminar()
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
        findNavController().navigate(R.id.action_eliminarReceita_to_listaReceitasFragmento)
    }

    private fun eliminar() {

    }
}