package com.six.ui.map

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import ca.six.util.IAfterDo
import ca.six.util.Permission6
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.six.ui.R
import com.six.ui.data.*
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-08-10.
 */
class ActivityMap : AppCompatActivity(), OnMapReadyCallback, IAfterDo{
    private lateinit var map: GoogleMap
    private var currentLatLng: LatLng = LatLng(0.0, 0.0)
    private lateinit var fusedLocClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    val YORK = LatLng(43.6434476,-79.3831327)
    val RATHBURN = LatLng(43.6077226,-79.6005062)
    val CAL = LatLng(51.0522336,-114.05637)
    val ZOOM_LEVEL = 15f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_map)

        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.fagMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest()
        locationRequest.setInterval(60000)
                .setFastestInterval(5000)
                .priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val locationList = result.locations
                if(locationList.size > 0) {
                    val location = locationList.get(locationList.size - 1)
                    println("xxl-latest-location: ${location.latitude}, ${location.longitude}")
                }
            }
        }
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

//        searchPlaces()

//        searchNearby()

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
                                                println("xxl-loc: ${it.latitude}, ${it.longitude}")

//                                                val gmmIntentUri =
//                                                        Uri.parse("geo:${currentLatLng.latitude},${currentLatLng.longitude}?q=${response.result.formatted_address}")

//                                                val gmmIntentUri =
//                                                        Uri.parse("google.navigation:q=${response.result.formatted_address}")

//                                                val gmmIntentUri =
//                                                        Uri.parse("http://plus.codes/${response.result.plus_code.global_code}")

                                                val gmmIntentUri =
                                                        Uri.parse("geo:0,0?q=${currentLatLng.latitude}, ${currentLatLng.longitude}(${response.result.formatted_address})")

                                                val gmmIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                                gmmIntent.setPackage("com.google.android.apps.maps")
                                                if (gmmIntent.resolveActivity(packageManager) != null) {
                                                    startActivity(gmmIntent)
                                                }
                                            }

                                            setOnMarkerClickListener {
                                                val gmmIntentUri =
                                                        Uri.parse("geo:0,0?q=${currentLatLng.latitude}, ${currentLatLng.longitude}(${response.result.formatted_address})")

                                                val gmmIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                                gmmIntent.setPackage("com.google.android.apps.maps")
                                                if (gmmIntent.resolveActivity(packageManager) != null) {
                                                    startActivity(gmmIntent)
                                                }
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

    private fun searchNearby() {
        val retrofit = Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(GoogleMapService::class.java)

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
                println("xxl-Nearby-onFailure")
            }
        })
    }


    private fun searchPlaces() {
        val retrofit = Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(GoogleMapService::class.java)
        val options = HashMap<String, String>()
        options.put("input", "TELUS Store")
        options.put("inputtype", "textquery")
        options.put("fields", "name,place_id")
        options.put("locationbias", "circle:5000@${currentLatLng.latitude}, ${currentLatLng.longitude}")
        options.put("key", getString(R.string.google_maps_key))
        val searchCall = service.searchPlaces(options)

        searchCall.enqueue(object : Callback<SearchPlaceResponse> {
            override fun onFailure(call: Call<SearchPlaceResponse>?, t: Throwable?) {
                println("xxl-SearchPlace-onFailure")
            }

            override fun onResponse(call: Call<SearchPlaceResponse>?, response: Response<SearchPlaceResponse>?) {
                println("xxl: ${response?.body()?.status}")
                for (item in response?.body()?.candidates!!) {
                    println("xxl: ${item.place_id}")

                    //get place info
                    val params = HashMap<String, String>()
                    params.put("placeid", item.place_id)
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

        fusedLocClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())

        fusedLocClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if(location != null) {
                        println("xxl-currentLoc=(${location.latitude}, ${location.longitude})")
                    }
                }



    }

}
