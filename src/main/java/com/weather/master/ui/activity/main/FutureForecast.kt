package com.weather.master.ui.activity.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.LinearLayout.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.master.R
import com.weather.master.data.model.daily
import com.weather.master.data.model.temp
import com.weather.master.data.model.weather
import com.weather.master.extentions.obtainViewModel
import com.weather.master.ui.base.BaseActivity
import com.weather.master.databinding.AFutureForecastBinding
import com.weather.master.ui.base.BaseRecycleViewAdapter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FutureForecast : BaseActivity<MainViewModel>() {
    private lateinit var binding: AFutureForecastBinding
    private var dailyList: ArrayList<daily> = ArrayList()
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_future_forecast)
        recyclerView = findViewById(R.id.recyclerView)

        val tomorrow = Calendar.getInstance() //current date and time
        tomorrow.set(Calendar.HOUR_OF_DAY, 23) //set hour to last hour
        tomorrow.set(Calendar.MINUTE, 59) //set minutes to last minute
        tomorrow.set(Calendar.SECOND, 59) //set seconds to last second
        tomorrow.set(Calendar.MILLISECOND, 999) //set milliseconds to last
        val formatter: DateFormat = SimpleDateFormat("EEE, d MMM yyyy")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        var date = Date()
        var weather = weather("","09d", "","")
        var weatherarray: ArrayList<weather> = ArrayList()
        var temp = temp("","","15","12","","")
        weatherarray.add(weather)

        for(i in 0..15)
        {
            tomorrow.add(Calendar.DAY_OF_MONTH, 1)
            dailyList.add(daily(temp, weatherarray,formatter.format(tomorrow.time)))
        }

        var baseRecycleViewAdapter = BaseRecycleViewAdapter(dailyList)
        recyclerView.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        recyclerView.adapter = baseRecycleViewAdapter

    }

    override fun initializeViewModel(): MainViewModel {
        return obtainViewModel(MainViewModel::class.java)
    }

    override fun getLayoutView(): View? {
        binding = AFutureForecastBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setUpChildUI(savedInstanceState: Bundle?) {
    }

    override fun onNetworkStatusChanged(isConnected: Boolean) {

    }
}