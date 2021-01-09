package com.example.instabus
import com.example.instabus.interfaces.StationInterface


data class Station (override val id: Int,
                             override val street_name: String,
                             override val city: String,
                             override val utm_x: String,
                             override val utm_y: String,
                             override val lat: Double,
                             override val lon: Double,
                             override val furniture: String,
                             override val buses: String,
                             override val distance: String
): StationInterface