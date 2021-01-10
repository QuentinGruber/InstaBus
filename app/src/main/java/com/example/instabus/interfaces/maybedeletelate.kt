package com.example.instabus.interfaces

interface BarcelonaBusApi {
    val code : Int
    val data : BarcelonaBusApiData
}

interface BarcelonaBusApiData {
    val nearstations: Array<StationInterface>
    val transport: String
}