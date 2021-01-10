package com.example.instabus.interfaces
import retrofit2.Call
import retrofit2.http.GET

interface BarcelonaBusApiService {
    @GET("/courses")
    fun listStations(): Call<List<StationInterface>>
}