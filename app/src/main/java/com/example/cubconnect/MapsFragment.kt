package com.example.cubconnect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), OnMapReadyCallback {

    private val Southeastern = LatLng(30.515583, -90.468443)
    private val ZOOM_LEVEL = 15.5f

    val CampusBounds = LatLngBounds(
        LatLng((30.508864), -90.471730),  // SW bounds
        LatLng((30.527527), -90.463864) // NE bounds
    )

    private var SELUMarker: Marker? = null
    private var CSTBMarker: Marker? = null
    private var FayardMarker: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_maps, container, false)
        val markerSpinner = rootView.findViewById<Spinner>(R.id.marker_spinner)

        // Populate the Spinner with marker options
        val markerOptions =
            arrayOf("SELU", "CSTB", "Fayard") // Replace with your marker titles
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, markerOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        markerSpinner.adapter = adapter

        markerSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedMarkerTitle = markerOptions[position]

                // Hide all markers initially
                SELUMarker?.isVisible = false
                CSTBMarker?.isVisible = false
                FayardMarker?.isVisible = false

                // Show the selected marker
                when (selectedMarkerTitle) {
                    "SELU" -> SELUMarker?.isVisible = true
                    "CSTB" -> CSTBMarker?.isVisible = true
                    "Fayard" -> FayardMarker?.isVisible = true
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case when nothing is selected (optional)
            }
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment: SupportMapFragment? =
            childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap ?: return

        // Set the LatLngBounds for camera targeting
        googleMap.setLatLngBoundsForCameraTarget(CampusBounds)

        googleMap ?: return
        with(googleMap) {
            moveCamera(
                com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(
                    Southeastern,
                    ZOOM_LEVEL
                )
            )
            SELUMarker = addMarker(
                MarkerOptions()
                .position(Southeastern)
                .title("SELU") // Set a title for the marker (optional)
                .snippet("College Southeastern Louisiana University")
                .visible(false)
            )

            CSTBMarker = addMarker(
                MarkerOptions()
                .position(LatLng(30.51068, -90.46487))
                .title("CSTB")
                .snippet("Computer Science and Technology Building")
                .visible(false)
            )

            FayardMarker = addMarker(
                MarkerOptions()
                .position(LatLng(30.51484, -90.46650))
                .title("Fayard")
                .snippet("The building with a lot of classes")
                .visible(false)
            )

        }
    }
}