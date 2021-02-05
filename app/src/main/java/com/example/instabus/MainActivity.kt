package com.example.instabus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.instabus.interfaces.BarcelonaBusApiService
import com.example.instabus.interfaces.BarcelonaBusResponseApi
import com.example.instabus.objects.Station
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class MainActivity : AppCompatActivity() {
    companion object {
        var Stations : List<Station> = mutableListOf()
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    private fun LoadApp() {
        val myIntent = Intent(this@MainActivity, MainActivity::class.java)
        myIntent.putExtra("REFRESH",true)
        this@MainActivity.startActivity(myIntent)
        finish()
    }

    private fun FetchStationsFromApi() {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://barcelonaapi.marcpous.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(BarcelonaBusApiService::class.java)
        val ApiRequest = service.listStations()
        ApiRequest.enqueue(object : Callback<BarcelonaBusResponseApi> {
            override fun onResponse(
                    call: Call<BarcelonaBusResponseApi>,
                    response: Response<BarcelonaBusResponseApi>
            ) {
                val allStations = response.body()
                if (allStations != null) {
                    Stations = allStations.data.nearstations
                    LoadApp()
                }
            }

            override fun onFailure(call: Call<BarcelonaBusResponseApi>, t: Throwable) {
                Stations = GetStationsFromFile(this@MainActivity)
                LoadApp()
            }
        })
    }
    private fun GetStationsFromFile(context: Context): List<Station> {
        val jsonFileString = getJsonDataFromAsset(context, "barcelonaapi.json")
        if (jsonFileString != null) {
            Log.i("data", jsonFileString)
        }

        val gson = Gson()
        val listStationsType = object : TypeToken<List<Station>>() {}.type
        return gson.fromJson(jsonFileString, listStationsType)
    }

    fun DisplayStationPage(view:View) {
        val myIntent = Intent(this@MainActivity, StationPage::class.java)
        val StationItem: TextView = view.findViewById(R.id.stationPictureTitle)
        myIntent.putExtra("stationName", StationItem.text) //Optional parameters
        this@MainActivity.startActivity(myIntent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(Stations.isNotEmpty()) {
            setTheme(R.style.Theme_InstaBus)
            setContentView(R.layout.activity_main)
            val navView: BottomNavigationView = findViewById(R.id.nav_view)

            val navController = findNavController(R.id.nav_host_fragment)
            val appBarConfiguration = AppBarConfiguration(
                    setOf(
                            R.id.navigation_list, R.id.navigation_map
                    )
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }
        else {
            FetchStationsFromApi()
            setContentView(R.layout.fragment_splash_screen)
        }
    }
}