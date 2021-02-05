package com.example.instabus

import DB
import StationPhoto
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.instabus.objects.Station
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class StationPhotoPreview : AppCompatActivity() {
    private val REQUEST_CODE = 42
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station_photo_preview)


        val takePictureintent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        startActivityForResult(takePictureintent, REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK ){
            val takenImage = data?.extras?.get("data") as Bitmap
            val imagePlace = findViewById<View>(R.id.imageView2) as ImageView
            imagePlace.setImageBitmap(takenImage)
            findViewById<Button>(R.id.saveButton).setOnClickListener {
                save(takenImage)
            }
        }
        else{
            finish()
        }
    }
    fun save(Image: Bitmap) {
        // Get the context wrapper instance
        val wrapper = ContextWrapper(applicationContext)

        // Initializing a new file
        // The bellow line return a directory in internal storage
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)


        // Create a file to save the image
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)

            // Compress bitmap
            Image.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            // Flush the stream
            stream.flush()

            // Close stream
            stream.close()
        } catch (e: IOException){ // Catch the exception
            e.printStackTrace()
        }

        Log.d("dd", Uri.parse(file.absolutePath).toString())

        val t1 = findViewById<View>(R.id.editPictureTitle) as TextView
        val dbHandler = DB(this, null)
        val intent = intent
        var stationName = intent.getStringExtra("stationName")
        if (stationName == null) {
            stationName = ""
        }
        val user = StationPhoto(t1.text.toString(), stationName, Uri.parse(file.absolutePath).toString())
        dbHandler.addStationPicture(user)
        finish()
    }
}