package com.example.receitas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.receitas.databinding.FragmentListaReceitasFragmentoBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [ListaReceitasFragmento.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaReceitasFragmento : Fragment() {
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
    }

    companion object {

    }
}