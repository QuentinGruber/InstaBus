package com.example.instabus.ui.StationsMap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.instabus.R

class StationsMapFragment : Fragment() {

    private lateinit var stationsMapViewModel: StationsMapViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        stationsMapViewModel =
                ViewModelProvider(this).get(StationsMapViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_stations_map, container, false)
        val textView: TextView = root.findViewById(R.id.text_stations_map)
        stationsMapViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}