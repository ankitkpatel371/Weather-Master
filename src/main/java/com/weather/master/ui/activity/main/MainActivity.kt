package com.weather.master.ui.activity.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.weather.master.data.model.CityModel
import com.weather.master.databinding.AMainBinding
import com.weather.master.extentions.obtainViewModel
import com.weather.master.ui.base.BaseActivity
import com.weather.master.ui.fragments.citys.CitysAdapter
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : BaseActivity<MainViewModel>() {

    private lateinit var binding: AMainBinding

    private val cityList = ArrayList<CityModel>()

    override fun initializeViewModel(): MainViewModel {
        return obtainViewModel(MainViewModel::class.java)
    }

    override fun getLayoutView(): View? {
        binding = AMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setUpChildUI(savedInstanceState: Bundle?) {

        if(isInternetAvailable()) {
            if (checkPermissions()) {
                if(isLocationEnabled())
                {
                    getLocation()
                }
                else
                {
                    setUpViewPager()
                }

            } else {
                requestPermissions()
            }
        }else
        {
            showNoInternetAvailable()
        }

    }

    override fun onNetworkStatusChanged(isConnected: Boolean) {

    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val mLocationRequest = LocationRequest.create()
        mLocationRequest.interval = 60000
        mLocationRequest.fastestInterval = 5000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        showProgress()

        val mLocationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult == null) {
                    hideProgress()
                    return
                }
                for (location in locationResult.locations) {
                    location?.let {

                            val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
                            val addresses: List<Address> =
                                geocoder.getFromLocation(it.latitude, it.longitude, 1)
                            cityList.clear()
                            if (addresses.isNotEmpty()) {
                                Log.e("Address :", addresses.toString())
                                cityList.add(
                                    CityModel(
                                        it.latitude,
                                        it.longitude,
                                        addresses[0].locality
                                    )
                                )
                            }
                            LocationServices.getFusedLocationProviderClient(this@MainActivity)
                                .removeLocationUpdates(this)
                            setUpViewPager()
                            hideProgress()

                    }
                }

            }

        }

        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, mLocationCallback, null)
    }

    private fun setUpViewPager()
    {
        cityList.add(CityModel(-33.865143, 151.209900, "Sydney"))
        cityList.add(CityModel(-33.758011, 150.705444, "Perth"))
        cityList.add(CityModel(-42.880554, 147.324997, "Hobart"))
        val adp = CitysAdapter(supportFragmentManager, cityList)
        binding.viewPagerCity.adapter = adp
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(
                            this@MainActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED)
                    ) {
                        //Permission  Granted
                        setUpChildUI(null)
                    }
                } else {
                    //Permission Denied
                    requestPermissions()
                }
                return
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
