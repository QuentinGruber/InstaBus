package com.example.instabus

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.instabus.interfaces.BarcelonaBusApiService
import com.example.instabus.interfaces.BarcelonaBusResponseApi
import com.example.instabus.interfaces.StationInterface
import com.example.instabus.objects.Station
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException

class MainActivity : AppCompatActivity() {
    companion object {
        var Stations : List<Station> = listOf();
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

    private fun FetchStationsFromApi() {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://barcelonaapi.marcpous.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        val service = retrofit.create(BarcelonaBusApiService::class.java)
        val ApiRequest = service.listStations()
        ApiRequest.enqueue(object : Callback<BarcelonaBusResponseApi> {
            override fun onResponse(call: Call<BarcelonaBusResponseApi>, response: Response<BarcelonaBusResponseApi>) {
                val allStations = response.body()
                Log.d("Stations", allStations.toString())
                Stations = allStations as List<Station>;
            }
            override fun onFailure(call: Call<BarcelonaBusResponseApi>, t: Throwable) {
                Stations = GetStationsFromFile(this@MainActivity)
                error("KO")
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FetchStationsFromApi();

        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
               R.id.navigation_list, R.id.navigation_map))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}