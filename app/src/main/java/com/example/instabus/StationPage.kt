package com.example.instabus

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.tv.TvContract.AUTHORITY
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File


class StationPage : AppCompatActivity() {
    val REQUEST_CODE = 42;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station_page)
        setSupportActionBar(findViewById(R.id.toolbar))

        val intent = intent
        val stationName = intent.getStringExtra("stationName")
        if (stationName != null) {
            Log.d("receive", stationName)
            val t1 = findViewById<View>(R.id.StationName) as TextView
            t1.text = stationName
        }
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            ActivityCompat.requestPermissions(this, Array<String>(1) { Manifest.permission.CAMERA }, REQUEST_CODE);

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            {
                Toast.makeText(this, "Unable to open camera reason : PERMISSION_DENIED", Toast.LENGTH_SHORT).show()
            }
            else {
                val myIntent = Intent(this@StationPage, StationPhotoPreview::class.java)
                this@StationPage.startActivity(myIntent)
            }
        }
    }
}