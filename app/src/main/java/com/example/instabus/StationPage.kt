package com.example.instabus

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.tv.TvContract.AUTHORITY
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File


class StationPage : AppCompatActivity() {

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

        val REQUEST_CODE = 42;
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            ActivityCompat.requestPermissions(this, Array<String>(1) { Manifest.permission.CAMERA }, REQUEST_CODE);
           // val f = File(Environment.getExternalStorageDirectory(), "temp.jpg")

            val takePictureintent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
           // takePictureintent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this@StationPage, AUTHORITY, f));
            //takePictureintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            {
                Toast.makeText(this, "Unable to open camera reason : PERMISSION_DENIED", Toast.LENGTH_SHORT).show()
            }
            else {
                startActivityForResult(takePictureintent, REQUEST_CODE)
            }
        }
    }
}