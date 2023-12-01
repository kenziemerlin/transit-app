package com.example.transitapp.ui.dashboard

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.transitapp.R
import com.example.transitapp.databinding.FragmentRoutesBinding
import org.w3c.dom.Text
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



        val auto: AutoCompleteTextView = binding.autoCompleteTextView
        val routes = resources.getStringArray(R.array.routes)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, routes )
        auto.setAdapter(adapter)

        //check in file exists
        val filename = "RoutesFile"
        val file = File(context?.filesDir, filename)


        if (file.exists()) {
            context?.openFileOutput(filename, Context.MODE_PRIVATE).use {
            }
        }

        readDisplayFile(filename);

        addButton.setOnClickListener {
            val routeSelected = auto.text.toString();
            val fileText = context?.openFileInput(filename)?.bufferedReader()?.useLines { lines ->
                lines.fold("") { some, text ->
                    "$some\n$text"
                }
            }

            if (fileText == "") {
                var appendText = routeSelected;
                context?.openFileOutput(filename, Context.MODE_APPEND).use {
                    it?.write(appendText.toByteArray())
                    readDisplayFile(filename)
                }
            } else {
                val routesArray: List<String>? = fileText?.split(",")
                var inRoutes = false;
                if (routesArray != null) {
                    for (route in routesArray) {
                        if (route == routeSelected) {
                            inRoutes = true;
                            break;
                        }
                    }
                    if (!inRoutes) {
                        var appendText = ",$routeSelected";
                        context?.openFileOutput(filename, Context.MODE_APPEND).use {
                            it?.write(appendText.toByteArray())
                        }
                        readDisplayFile(filename)
                    }
                }
            }
        }
        return root
    }

    private fun readDisplayFile(fileName:String) {

        val fileText = context?.openFileInput(fileName)?.bufferedReader()?.useLines { lines ->
            lines.fold("") { some, text ->
                "$some\n$text"
            }
        }

        val routeLayout: LinearLayout = binding.routeLayout;
        routeLayout.removeAllViews()

        if (fileText != null) {
            if (fileText != "") {
                val splitFileText = fileText.split(",")

                for (item in splitFileText) {
                    val routeText = TextView(requireContext())
                    routeText.text = item
                    routeText.textSize = 28f // Set the text size in scaled pixels
                    routeText.setTextColor(Color.BLACK)
                    routeLayout.addView(routeText)
                }
                Log.i("testing", fileText.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}