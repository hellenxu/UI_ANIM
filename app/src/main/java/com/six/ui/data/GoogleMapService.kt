package com.six.ui.data

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.HashMap

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-08-10.
 */
interface GoogleMapService {

    @GET("place/findplacefromtext/json")
//    fun searchPlaces(@QueryMap params: HashMap<String, String>): Observable<SearchPlaceResponse>
    fun searchPlaces(@QueryMap params: HashMap<String, String>): Call<SearchPlaceResponse>

    @GET("place/details/json")
    fun getPlaceInfo(@QueryMap params: HashMap<String, String>): Call<PlaceInfoResponse>

    @GET("place/details/json")
    fun getPlaceInfoRx(@QueryMap params: HashMap<String, String>): Observable<PlaceInfoResponse>


    @GET("place/nearbysearch/json")
    fun searchNearby(@QueryMap params: HashMap<String, String>): Call<NearbyResponse>

    @GET("place/nearbysearch/json")
    fun searchNearbyRx(@QueryMap params: HashMap<String, String>): Observable<NearbyResponse>
}