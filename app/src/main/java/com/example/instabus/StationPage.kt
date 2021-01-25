package com.example.instabus

import DB
import StationPhoto
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton


class StationPage : AppCompatActivity() {
    companion object {
        var stationsPictures : List<StationPhoto> = mutableListOf()
    }

    override fun onRestart() {
        super.onRestart()
        finish()
        startActivity(intent)
    }
    val REQUEST_CODE = 42;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(findViewById(R.id.toolbar))
        val intent = intent
        val stationName = intent.getStringExtra("stationName")
        if (stationName != null) {
            loadStationsPictures(stationName)
            setContentView(R.layout.activity_station_page)
            Log.d("receive", stationName)
            val t1 = findViewById<View>(R.id.StationName) as TextView
            t1.text = stationName
        };
        else {
            setContentView(R.layout.activity_station_page)
        }
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            ActivityCompat.requestPermissions(this, Array<String>(1) { Manifest.permission.CAMERA }, REQUEST_CODE);

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

    fun loadStationsPictures(stationName: String) {
        val dbHandler = DB(this, null)
        val cursor = dbHandler.getStationPictures(stationName)
        stationsPictures = emptyList();
        if (cursor != null) {
            if(cursor.getCount() > 0) {
                //do stuff
                cursor!!.moveToFirst()
                stationsPictures += StationPhoto(title = cursor.getString(
                        cursor.getColumnIndex("title")), imagePath = cursor.getString(cursor.getColumnIndex("imagePath")), stationName = cursor.getString(cursor.getColumnIndex("stationName")))

                while (cursor.moveToNext()) {
                    stationsPictures += StationPhoto(title = cursor.getString(
                            cursor.getColumnIndex("title")), imagePath = cursor.getString(cursor.getColumnIndex("imagePath")), stationName = cursor.getString(cursor.getColumnIndex("stationName")))
                }
                cursor.close()
            }
        }
    }
}