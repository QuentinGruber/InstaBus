package com.example.instabus

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StationPhotoPreview : AppCompatActivity() {
    private val REQUEST_CODE = 42;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station_photo_preview)

        // val f = File(Environment.getExternalStorageDirectory(), "temp.jpg")

        val takePictureintent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // takePictureintent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this@StationPage, AUTHORITY, f));
        //takePictureintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(takePictureintent, REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK ){
            val takenImage = data?.extras?.get("data") as Bitmap
            val imagePlace = findViewById<View>(R.id.imageView2) as ImageView;
            imagePlace.setImageBitmap(takenImage);
        }
        else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}