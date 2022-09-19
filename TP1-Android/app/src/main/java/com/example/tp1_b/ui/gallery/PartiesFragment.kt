package com.example.tp1_b.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp1_b.API.PartieService
import com.example.tp1_b.Models.Partie
import com.example.tp1_b.databinding.FragmentGalleryBinding

class PartiesFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val parties: ArrayList<Partie> = ArrayList()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        PartieService(requireContext()).getParties(
            { p ->
                parties.clear()
                parties.addAll(p)
                activity?.runOnUiThread {
                    val recyclerView: RecyclerView = binding.partiesReyclerView
                    recyclerView.adapter?.notifyItemRangeInserted(0, p.size)
                    recyclerView.scrollToPosition(0)
                }
            },
            { err -> activity?.runOnUiThread {
                Toast.makeText(requireContext(), err.message, Toast.LENGTH_LONG).show()
                System.err.println(err.message)
            }}
        )

        val recyclerView: RecyclerView = binding.partiesReyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = PartiesAdapter(requireContext(), parties)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}