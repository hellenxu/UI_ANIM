package com.six.ui.map

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import ca.six.util.IAfterDo
import ca.six.util.Permission6
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.six.ui.R

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-08-10.
 */
class ActivityMap : AppCompatActivity(), OnMapReadyCallback, IAfterDo{
    private lateinit var map: GoogleMap
    private var currentLatLng: LatLng = LatLng(0.0, 0.0)

    val YORK = LatLng(43.6434476,-79.3831327)
    val RATHBURN = LatLng(43.6077226,-79.6005062)
    val CAL = LatLng(51.0522336,-114.05637)
    val ZOOM_LEVEL = 15f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_map)

        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.fagMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }


    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap ?: return

        Permission6.executeWithPermission(this, ACCESS_FINE_LOCATION, this)

    }

    fun onCheckBoxClick(view: View) {
        if((view !is RadioButton) and  !::map.isInitialized) {
            return
        }

        val isChecked = (view as RadioButton).isChecked
        if(isChecked) {
            when (view.id) {
                R.id.rbLocT -> {
                    currentLatLng = YORK
                }

                R.id.rbLocM -> {
                    currentLatLng = RATHBURN
                }

                R.id.rbLocC -> {
                    currentLatLng = CAL
                }
            }
        }

        with(map) {
            moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, ZOOM_LEVEL))

//            addMarker(MarkerOptions().position(currentLatLng)) //TODO set closest telus store position
        }
    }

    override fun userDenyPermission() {
        Toast.makeText(this@ActivityMap, "Cannot show store info since no permission", Toast.LENGTH_LONG)
                .show()
    }

    @SuppressLint("MissingPermission")
    override fun doAfterPermission() {
        map.isMyLocationEnabled = false
        map.uiSettings.setAllGesturesEnabled(false)
    }

}
