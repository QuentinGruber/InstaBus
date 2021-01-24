package com.example.instabus

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instabus.StationPage.Companion.stationsPictures
import java.io.File


class MyStationPictureRecyclerViewAdapter(

) : RecyclerView.Adapter<MyStationPictureRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_station_picture, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("on en est la", stationsPictures.toString())
        val item = stationsPictures[position]
        holder.stationPictureTitle.text = item.title
        //imagePlace.setImageBitmap(takenImage);
        val imgFile = File(item.imagePath)
        if (imgFile.exists()) {
            val imageBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath())
            holder.stationPicture.setImageBitmap(imageBitmap)
        }
    }

    override fun getItemCount(): Int = stationsPictures.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stationPictureTitle: TextView = view.findViewById(R.id.stationPictureTitle)
        val stationPicture: ImageView = view.findViewById<View>(R.id.stationPicture) as ImageView;

        override fun toString(): String {
            return super.toString() + " '" + stationPictureTitle.text + "'"
        }
    }
}