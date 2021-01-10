package com.example.instabus.interfaces
import retrofit2.Call
import retrofit2.http.GET

interface BarcelonaBusApiService {
    @GET("/bus/nearstation/latlon/41.3985182/2.1917991/1.json")
    fun listStations(): Call<List<StationInterface>>
}