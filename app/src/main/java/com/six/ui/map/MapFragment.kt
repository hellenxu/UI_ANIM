package com.six.ui.map

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.six.ui.R

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-08-22.
 */
class MapFragment: Fragment(), OnMapReadyCallback {
    private var mapFragment: SupportMapFragment? = null
    private lateinit var map: GoogleMap

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val itemView = inflater?.inflate(R.layout.frag_map, container, false)

        if(mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance()
            mapFragment!!.getMapAsync(this)
        }

        childFragmentManager.beginTransaction().replace(R.id.mapContainer, mapFragment).commit()

        return itemView
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.uiSettings.isMapToolbarEnabled = false
        map.uiSettings.setAllGesturesEnabled(false)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(43.6434476,-79.3831327), 15f))
        map.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_marker)).position(LatLng(43.6434476,-79.3831327)))
    }
}
