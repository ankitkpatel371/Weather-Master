package com.weather.master.ui.base

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.master.data.model.daily
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.weather.master.R

class BaseRecycleViewAdapter(val dataSet: ArrayList<daily>) :
    RecyclerView.Adapter<BaseRecycleViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val dateView: TextView = view.findViewById(R.id.txtCityTempDate)
            val hiLoTemperatureView: TextView = view.findViewById(R.id.txtCityTempLowMax)
            val weatherImageView: ImageView = view.findViewById(R.id.imvWeatherView)
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.v_i_temp_day, viewGroup, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.dateView.text = dataSet.get(position).dt
        viewHolder.hiLoTemperatureView.text = dataSet.get(position).temp.max.toString()+ "\u00B0C" + " / " + dataSet.get(position).temp.min + "\u00B0C"
        setupWeatherIcon(dataSet.get(position).weather.get(0).icon, viewHolder.weatherImageView)
    }
    override fun getItemCount() = dataSet.size

    private fun setupWeatherIcon(weatherCondition: String, imageView: ImageView)
    {
        when(weatherCondition)
        {
            "01d" -> imageView.setImageResource(R.drawable.d01)
            "02d" -> imageView.setImageResource(R.drawable.d02)
            "03d" -> imageView.setImageResource(R.drawable.d03)
            "04d" -> imageView.setImageResource(R.drawable.d04)
            "09d" -> imageView.setImageResource(R.drawable.d09)
            "10d" -> imageView.setImageResource(R.drawable.d10)
            "11d" -> imageView.setImageResource(R.drawable.d11)
            "50d" -> imageView.setImageResource(R.drawable.d50)
        }
    }
}
