package com.example.transitapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.transitapp.R
import com.example.transitapp.databinding.AnnotationViewBinding
import com.example.transitapp.databinding.FragmentHomeBinding
import com.google.transit.realtime.GtfsRealtime.FeedMessage
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import java.net.URL


class HomeFragment : Fragment() {
    var mapView: MapView? = null
    private var _binding: FragmentHomeBinding? = null
    private lateinit var viewAnnotationManager: ViewAnnotationManager
    private lateinit var mapboxMap: MapboxMap

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
        // Create view annotation manager
        viewAnnotationManager = binding.mapView.viewAnnotationManager
        mapboxMap = binding.mapView.getMapboxMap().apply {
            // Load a map style
            loadStyleUri("mapbox://styles/baghetti/clp1evjo200uf01qodup00rgg") {
                // Get the center point of the map
                val center = mapboxMap.cameraState.center
                // Add the view annotation at the center point
                getBuses()
            }
        }
        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    private fun getBuses(){
        val url = URL("https://gtfs.halifax.ca/realtime/Vehicle/VehiclePositions.pb")

        val feed = FeedMessage.parseFrom(url.openStream())

        for (entity in feed.entityList) {
            val routeId = entity.vehicle.trip.routeId
            val latitude = entity.vehicle.position.latitude.toDouble()
            val longitude = entity.vehicle.position.longitude.toDouble()
            addViewAnnotation(longitude, latitude, routeId)
        }

    }

    private fun addViewAnnotation(longitude:Double, latitude:Double, routeId:String) {

        val point = Point.fromLngLat(longitude, latitude)
        // Define the view annotation
        val viewAnnotation = viewAnnotationManager.addViewAnnotation(
            // Specify the layout resource id
            resId = R.layout.annotation_view,
            // Set any view annotation options
            options = viewAnnotationOptions {
                geometry(point)
            }
        )
        AnnotationViewBinding.bind(viewAnnotation)
        val textView = viewAnnotation.findViewById<TextView>(R.id.annotation)
        textView.text = routeId;
        }
    }

     fun help(){

    }
