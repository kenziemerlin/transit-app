package com.example.transitapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.transitapp.R
import com.example.transitapp.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val auto: AutoCompleteTextView = binding.autoCompleteTextView
        val routes = resources.getStringArray(R.array.routes)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, routes )
        auto.setAdapter(adapter)


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}