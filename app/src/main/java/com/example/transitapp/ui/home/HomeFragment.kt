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





        //mapView = (binding.mapView)
        mapView = root.findViewById(R.id.mapView)
        viewAnnotationManager =  mapView!!.viewAnnotationManager

        val url = URL("https://gtfs.halifax.ca/realtime/Vehicle/VehiclePositions.pb")

        val feed = FeedMessage.parseFrom(url.openStream())

        for (entity in feed.entityList) {
            val routeId = entity.vehicle.trip.routeId
            val longitude = entity.vehicle.position.latitude.toDouble()
            val latitude = entity.vehicle.position.longitude.toDouble()
            val point = Point.fromLngLat(longitude, latitude)

            addViewAnnotation(routeId, point)
        }
        mapView!!.getMapboxMap().loadStyleUri("mapbox://styles/baghetti/clp1evjo200uf01qodup00rgg") {

        }

        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    private fun addViewAnnotation(routeId: String, point: Point) {
        // Define the view annotation
        val viewAnnotation = viewAnnotationManager.addViewAnnotation(
            // Specify the layout resource id
            resId = R.layout.annotation_view,
            // Set any view annotation options
            options = viewAnnotationOptions {
                geometry(point)
            }
        )
        val textView = viewAnnotation.findViewById<TextView>(R.id.annotation)
        val binding = AnnotationViewBinding.bind(viewAnnotation)

        textView.setText(routeId)

        }
    }

     fun help(){
        val url = URL("https://gtfs.halifax.ca/realtime/Vehicle/VehiclePositions.pb")

        val feed = FeedMessage.parseFrom(url.openStream())

        for (entity in feed.entityList) {
            Log.v("Route ID: ", entity.vehicle.trip.routeId)
            Log.v("Latitude: ",entity.vehicle.position.latitude.toString())
            Log.v("Longitude: ",entity.vehicle.position.longitude.toString())
            if (entity.hasTripUpdate()) {
                Log.i("testing", entity.tripUpdate.toString())
            }
        }
    }
