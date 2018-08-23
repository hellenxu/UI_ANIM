package com.six.ui.map

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.six.ui.R
import com.six.ui.data.GoogleMapService
import com.six.ui.data.NearbyResponse
import com.six.ui.data.PlaceInfoResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.frag_map.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-08-22.
 */
class MapFragment: Fragment(), OnMapReadyCallback, View.OnClickListener {
    override fun onClick(v: View?) {
        onCheckBoxClick(v!!)
    }

    private var mapFragment: SupportMapFragment? = null
    private lateinit var map: GoogleMap
    private var currentLatLng: LatLng = LatLng(0.0, 0.0)
    val YORK = LatLng(43.6434476,-79.3831327)
    val RATHBURN = LatLng(43.6077226,-79.6005062)
    val CAL = LatLng(51.0522336,-114.05637)
    val ZOOM_LEVEL = 15f


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val itemView = inflater?.inflate(R.layout.frag_map, container, false)

        if(mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance()
            mapFragment!!.getMapAsync(this)
        }

        childFragmentManager.beginTransaction().replace(R.id.fagMap, mapFragment).commit()

        return itemView
    }

    override fun onResume() {
        super.onResume()
        rbLocT.setOnClickListener(this)
        rbLocM.setOnClickListener(this)
        rbLocC.setOnClickListener(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.uiSettings.isMapToolbarEnabled = false
        map.uiSettings.setAllGesturesEnabled(false)
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(43.6434476,-79.3831327), 15f))
//        map.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_marker)).position(LatLng(43.6434476,-79.3831327)))
    }

    private fun onCheckBoxClick(view: View) {
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

        searchNearbyRx()

    }

    private fun searchNearbyRx(){
        val retrofit = Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build()
        val service = retrofit.create(GoogleMapService::class.java)

        //prepare params
        val options = HashMap<String, String>()
        options.put("name", "TELUS Store")
        options.put("rankby", "distance")
        options.put("location", "${currentLatLng.latitude}, ${currentLatLng.longitude}")
        options.put("key", getString(R.string.google_maps_key))

        val observable = service.searchNearbyRx(options)
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<NearbyResponse> {
                    override fun onComplete() {
                        println("xxl-nearby-onComplete")
                    }

                    override fun onSubscribe(d: Disposable) {
                        println("xxl-nearby-onSubscribe")
                    }

                    override fun onNext(nearbyResponse: NearbyResponse) {
                        val target = nearbyResponse.results[0]
                        println("xxl: ${target.place_id}")

                        //get place info
                        val params = HashMap<String, String>()
                        params.put("placeid", target.place_id)
                        params.put("key", getString(R.string.google_maps_key))

                        val placeObservable = service.getPlaceInfoRx(params)
                        placeObservable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object : Observer<PlaceInfoResponse> {
                                    override fun onComplete() {
                                        println("xxl-placeInfo-onComplete")
                                    }

                                    override fun onSubscribe(d: Disposable) {
                                        println("xxl-placeInfo-onSubscribe")
                                    }

                                    override fun onNext(response: PlaceInfoResponse) {
                                        val location = response.result.geometry.location
                                        currentLatLng = LatLng(location.lat, location.lng)

                                        with(map) {
                                            moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, ZOOM_LEVEL))
                                            addMarker(MarkerOptions().position(currentLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_marker)))
                                            setOnMapClickListener {
                                                println("xxl-onMapClick000")

                                            }

                                            setOnMarkerClickListener {
                                                println("xxl-onMarkerClick000")

                                                true
                                            }

                                        }
                                    }

                                    override fun onError(e: Throwable) {
                                        println("xxl-placeInfo-onError")                                    }

                                })

                    }

                    override fun onError(e: Throwable) {
                        println("xxl-nearby-onError")
                    }

                })
    }
}
