package com.example.transitapp.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.transitapp.R
import com.example.transitapp.databinding.FragmentRoutesBinding
import java.io.File

class RoutesFragment : Fragment() {

    private var _binding: FragmentRoutesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoutesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val addButton: Button = binding.addBtn;
        val routesText: TextView = binding.textViewMyRoutes



        val auto: AutoCompleteTextView = binding.autoCompleteTextView
        val routes = resources.getStringArray(R.array.routes)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, routes )
        auto.setAdapter(adapter)

        //check in file exists
        val filename = "RoutesFile"
        val file = File(context?.filesDir, filename)

        if (!file.exists()) {
            context?.openFileOutput(filename, Context.MODE_PRIVATE).use {
            }
        }

        readDisplayFile(filename);

        addButton.setOnClickListener{
            val routeSelected = auto.text.toString();

            val appendText = "$routeSelected,";
            context?.openFileOutput(filename, Context.MODE_APPEND).use {
                it?.write(appendText.toByteArray())
            }
            readDisplayFile(filename)
        }
        return root
    }

    private fun readDisplayFile(fileName:String) {
        val routesText: TextView = binding.textViewMyRoutes
        val fileText = context?.openFileInput(fileName)?.bufferedReader()?.useLines { lines ->
            lines.fold("") { some, text ->
                "$some\n$text"
            }
        }
        if (fileText != null) {
            val fileTextsplit = fileText
            routesText.text = fileText
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}