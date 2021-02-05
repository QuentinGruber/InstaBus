package com.example.instabus

import DB
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
        val item = stationsPictures[position]
        holder.stationPictureTitle.text = item.title
        holder.stationPictureButtonDelete.setOnClickListener { v ->
            val dbHandler = DB(v.context , null)

            if(item.imagePath?.let { dbHandler.deleteStationPicture(it) } == true){
                removeItem(position)
            }
        }
        //imagePlace.setImageBitmap(takenImage);
        val imgFile = File(item.imagePath)
        if (imgFile.exists()) {
            val imageBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath())
            holder.stationPicture.setImageBitmap(imageBitmap)
        }
    }
    fun removeItem(position: Int) {
        stationsPictures.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = stationsPictures.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stationPictureTitle: TextView = view.findViewById(R.id.stationPictureTitle)
        val stationPicture: ImageView = view.findViewById(R.id.stationPicture);
        val stationPictureButtonDelete: Button = view.findViewById(R.id.StationPictureButtonDelete);
        override fun toString(): String {
            return super.toString() + " '" + stationPictureTitle.text + "'"
        }
    }
}