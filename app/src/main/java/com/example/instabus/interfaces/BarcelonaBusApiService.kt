package com.example.instabus.interfaces
import retrofit2.Call
import retrofit2.http.GET

interface BarcelonaBusResponseApi {
    val code : Int
    val data : BarcelonaBusApiResponseData
}

data class BarcelonaBusApiResponseData (
    val nearstations: Array<StationInterface> ,
    val transport: String
    )

interface BarcelonaBusApiService {
    @GET("/bus/nearstation/latlon/41.3985182/2.1917991/1.json")
    fun listStations(): Call<BarcelonaBusResponseApi>
}