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
import com.six.ui.core.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

        val retrofit = Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(GoogleMapService::class.java)

//        val options = HashMap<String, String>()
//        options.put("input", "TELUS Store")
//        options.put("inputtype", "textquery")
//        options.put("fields", "name,place_id")
//        options.put("locationbias", "circle:5000@${currentLatLng.latitude}, ${currentLatLng.longitude}")
//        options.put("key", getString(R.string.google_maps_key))
//        val searchCall = service.searchPlaces(options)

//        searchCall.enqueue(object : Callback<SearchPlaceResponse> {
//            override fun onResponse(call: Call<SearchPlaceResponse>?, response: Response<SearchPlaceResponse>?) {
//                println("xxl: ${response?.body()?.status}")
//                for(item in response?.body()?.candidates!!) {
//                    println("xxl: ${item.place_id}")
//
//                    //get place info
//                    val params= HashMap<String, String>()
//                    params.put("placeid", item.place_id)
//                    params.put("key", getString(R.string.google_maps_key))
//
//                    val placeCall = service.getPlaceInfo(params)
//                    placeCall.enqueue(object: Callback<PlaceInfoResponse>{
//                        override fun onFailure(call: Call<PlaceInfoResponse>?, t: Throwable?) {
//                            println("xxl-PlaceInfo-onFailure")
//                        }
//
//                        override fun onResponse(call: Call<PlaceInfoResponse>?, response: Response<PlaceInfoResponse>?) {
//                            println("xxl-PlaceInfo-onResponse")
//                            val location = response?.body()?.result?.geometry?.location
//                            if (location != null){
//                                currentLatLng = LatLng(location.lat, location.lng)
//
//                                with(map) {
//                                    moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, ZOOM_LEVEL))
//                                    addMarker(MarkerOptions().position(currentLatLng))
//                                }
//                            }
//                        }
//
//                    })
//                }
//            }

        val options = HashMap<String, String>()
        options.put("name", "TELUS Store")
        options.put("rankby", "distance")
        options.put("location", "${currentLatLng.latitude}, ${currentLatLng.longitude}")
        options.put("key", getString(R.string.google_maps_key))

        val searchCall = service.searchNearby(options)

        searchCall.enqueue(object : Callback<NearbyResponse> {
            override fun onResponse(call: Call<NearbyResponse>?, response: Response<NearbyResponse>?) {
                println("xxl: ${response?.body()?.status}")
                val results = response?.body()?.results
                if (results != null) {
                    val target = results[0]

                    println("xxl: ${target.place_id}")

                    //get place info
                    val params = HashMap<String, String>()
                    params.put("placeid", target.place_id)
                    params.put("key", getString(R.string.google_maps_key))

                    val placeCall = service.getPlaceInfo(params)
                    placeCall.enqueue(object : Callback<PlaceInfoResponse> {
                        override fun onFailure(call: Call<PlaceInfoResponse>?, t: Throwable?) {
                            println("xxl-PlaceInfo-onFailure")
                        }

                        override fun onResponse(call: Call<PlaceInfoResponse>?, response: Response<PlaceInfoResponse>?) {
                            println("xxl-PlaceInfo-onResponse")
                            val location = response?.body()?.result?.geometry?.location
                            if (location != null) {
                                currentLatLng = LatLng(location.lat, location.lng)

                                with(map) {
                                    moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, ZOOM_LEVEL))
                                    addMarker(MarkerOptions().position(currentLatLng))
                                }
                            }
                        }

                    })
                }
            }

            override fun onFailure(call: Call<NearbyResponse>?, t: Throwable?) {
                println("xxl-SearchPlace-onFailure")
            }
        })
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
