package com.example.cubconnect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), OnMapReadyCallback {

    private val Southeastern = LatLng(30.515583, -90.468443)
    private val ZOOM_LEVEL = 15.5f

    val CampusBounds = LatLngBounds(
        LatLng((30.508864), -90.471730),  // SW bounds
        LatLng((30.527527), -90.463864) // NE bounds
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
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
            addMarker(MarkerOptions().position(Southeastern))
        }
    }
}