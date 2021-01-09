package com.example.instabus.ui.StationsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.instabus.R

class StationsListFragment : Fragment() {

    private lateinit var stationsListViewModel: StationsListViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        stationsListViewModel =
                ViewModelProvider(this).get(StationsListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_stations_list, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        return root
    }
}