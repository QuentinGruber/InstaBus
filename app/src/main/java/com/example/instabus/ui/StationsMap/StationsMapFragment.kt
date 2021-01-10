package com.example.instabus.ui.StationsMap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instabus.MainActivity.Companion.Stations
import com.example.instabus.R
import com.example.instabus.objects.Station
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class StationsMapFragment : Fragment() {
    var lastMarkerId = "";
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        Stations.forEach { station: Station ->
            val stationPostion = LatLng(station.lat, station.lon);
            googleMap.addMarker(
                MarkerOptions()
                    .position(stationPostion)
                    .title(station.street_name)
                    .snippet("Buses : "+station.buses)
            );
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(41.3985182, 2.1917991), 14.0f))
        googleMap.setOnMarkerClickListener { marker ->
            if (lastMarkerId == marker.id) {
                // TODO: load station page here
                marker.hideInfoWindow()
            } else {
                lastMarkerId = marker.id;
                marker.showInfoWindow()
            }
            true
        }

        googleMap.setOnInfoWindowClickListener { marker ->
            if (lastMarkerId == marker.id) {
                // TODO: load station page here
                marker.hideInfoWindow()
            } else {
                lastMarkerId = marker.id;
                marker.showInfoWindow()
            }
            true
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stations_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}