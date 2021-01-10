package com.example.instabus.interfaces
import retrofit2.Call
import retrofit2.http.GET

data class BarcelonaBusResponseApi (
        val code: String ,
        val data: BarcelonaBusApiResponseData
)


interface BarcelonaBusApiResponseData {
    val nearstations: Array<StationInterface>
    val transport: String
}

interface BarcelonaBusApiService {
    @GET("/bus/nearstation/latlon/41.3985182/2.1917991/1.json")
    fun listStations(): Call<BarcelonaBusResponseApi>
}