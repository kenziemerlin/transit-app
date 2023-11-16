package com.example.transitapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.transitapp.databinding.FragmentHomeBinding
import com.google.transit.realtime.GtfsRealtime.FeedMessage
import com.mapbox.maps.MapView
import java.net.URL


class HomeFragment : Fragment() {
    var mapView: MapView? = null
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mapView = (binding.mapView)
        mapView?.getMapboxMap()?.loadStyleUri("mapbox://styles/baghetti/clp1evjo200uf01qodup00rgg")

        help()

        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

     fun help(){
        val url = URL("https://gtfs.halifax.ca/realtime/Vehicle/VehiclePositions.pb")

        val feed = FeedMessage.parseFrom(url.openStream())

        for (entity in feed.entityList) {
            if (entity.hasTripUpdate()) {
                Log.i("testing", entity.tripUpdate.toString())
            }
        }
    }
}