package com.example.instabus

import DB
import StationPhoto
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton


class StationPage : AppCompatActivity() {
    companion object {
        var stationsPictures : MutableList <StationPhoto> = mutableListOf()
    }

    override fun onRestart() {
        super.onRestart()
        finish()
        startActivity(intent)
    }
    private val REQUEST_CODE = 42
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(findViewById(R.id.toolbar))
        val intent = intent
        val stationName = intent.getStringExtra("stationName")
        if (stationName != null) {
            loadStationsPictures(stationName)
            setContentView(R.layout.activity_station_page)
            val t1 = findViewById<View>(R.id.StationName) as TextView
            t1.text = stationName
        } else {
            setContentView(R.layout.activity_station_page)
        }
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            ActivityCompat.requestPermissions(this, Array(1) { Manifest.permission.CAMERA }, REQUEST_CODE)

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            {
                Toast.makeText(this, "Unable to open camera reason : PERMISSION_DENIED", Toast.LENGTH_SHORT).show()
            }
            else {
                val myIntent = Intent(this@StationPage, StationPhotoPreview::class.java)
                myIntent.putExtra("stationName", stationName)
                this@StationPage.startActivity(myIntent)
            }
        }
    }

    private fun loadStationsPictures(stationName: String) {
        val dbHandler = DB(this, null)
        val cursor = dbHandler.getStationPictures(stationName)
        stationsPictures = mutableListOf()
        if (cursor != null) {
            if(cursor.count > 0) {
                cursor.moveToFirst()
                stationsPictures.plusAssign(
                    StationPhoto(title = cursor.getString(
                        cursor.getColumnIndex("title")), imagePath = cursor.getString(cursor.getColumnIndex("imagePath")), stationName = cursor.getString(cursor.getColumnIndex("stationName")))
                )

                while (cursor.moveToNext()) {
                    stationsPictures.plusAssign(
                        StationPhoto(title = cursor.getString(
                            cursor.getColumnIndex("title")), imagePath = cursor.getString(cursor.getColumnIndex("imagePath")), stationName = cursor.getString(cursor.getColumnIndex("stationName")))
                    )
                }
                cursor.close()
            }
        }
    }
}