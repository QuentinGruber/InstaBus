package com.example.instabus

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class StationPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station_page)
        setSupportActionBar(findViewById(R.id.toolbar))

        val intent = intent
        val stationName = intent.getStringExtra("stationName")
        if (stationName != null) {
            Log.d("receive", stationName)
          //  val txtView = this@StationPage.findViewById<View>(R.id.)
            val t1 = findViewById<View>(R.id.StationName) as TextView
            t1.text = "RRRRRR"
        }


        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }
}