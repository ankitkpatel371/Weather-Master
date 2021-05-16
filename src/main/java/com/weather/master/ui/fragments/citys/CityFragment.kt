package com.weather.master.ui.fragments.citys

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.weather.master.R
import com.weather.master.data.model.CityModel
import com.weather.master.data.model.daily
import com.weather.master.data.model.temp
import com.weather.master.databinding.FCityBinding
import com.weather.master.extentions.logLargeString
import com.weather.master.extentions.obtainViewModel
import com.weather.master.ui.activity.main.FutureForecast
import com.weather.master.ui.base.BaseFragment
import com.weather.master.utils.Status
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class CityFragment(private val city: CityModel) : BaseFragment<CityViewModel>() {

    private lateinit var binding: FCityBinding

    override fun initializeViewModel(): CityViewModel {
        return obtainViewModel(CityViewModel::class.java)
    }

    override fun getLayoutView(inflater: LayoutInflater): View {
        binding = FCityBinding.inflate(inflater)
        return binding.root
    }

    override fun setUpUI() {

        binding.txtCityName.text = city.name

        if (city.lat == null && city.lat == null) {
            setupObservers(null, null)
        } else {
            setupObservers(city.lat, city.lon)
        }

        binding.txtfutureForecast.setOnClickListener {
            val intent = Intent(activity, FutureForecast::class.java)
            startActivity(intent)
        }
    }

    private fun setupObservers(lat: Double?, lon: Double?) {

        val request = if (lat == null || lon == null) {
            viewModel.fetchWeatherInformation()
        } else {
            viewModel.fetchWeatherInformation(lat.toString(), lon.toString())
        }

        request.observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        viewModel.hideProgressDialog()
                        resource.data?.let { response ->

                            binding.txtCityTemp.text = convertStringtoInt(response.current.temp).toString() + "\u00B0" + "C"
                            response.current.weather.let {
                                binding.txtCityTempStatus.text = it[0].main
                                setupWeatherIcon(it[0].icon, binding.imvWeatherCondition)
                            }
                            binding.txtCityTempLowMax.text = convertStringtoInt(response.daily[0].temp.min).toString()+"\u00B0C" + "/"+ convertStringtoInt(response.daily[0].temp.max).toString()+"\u00B0C"


                            val tomorrow = Calendar.getInstance() //current date and time
                            tomorrow.add(Calendar.DAY_OF_MONTH, 1) //add a day
                            tomorrow.set(Calendar.HOUR_OF_DAY, 23) //set hour to last hour
                            tomorrow.set(Calendar.MINUTE, 59) //set minutes to last minute
                            tomorrow.set(Calendar.SECOND, 59) //set seconds to last second
                            tomorrow.set(Calendar.MILLISECOND, 999) //set milliseconds to last

                            val formatter: DateFormat = SimpleDateFormat("EEE, d MMM yyyy")
                            formatter.timeZone = TimeZone.getTimeZone("UTC")
                            val date = Date()


                            response.daily[1].let { daily ->
                                setupWeatherIcon(daily.weather.get(0).icon, binding.dayOne.imvWeatherView)
                                date.time = daily.dt.toLong() * 1000
                                if (date.time < tomorrow.time.time && date.time > Calendar.getInstance().time.time) {
                                    binding.dayOne.txtCityTempDate.text = "Tomorrow"
                                } else {
                                    binding.dayOne.txtCityTempDate.text = formatter.format(date)
                                }
                                binding.dayOne.txtCityTempLowMax.text = convertStringtoInt(daily.temp.max).toString()+ "\u00B0C" + " / " + convertStringtoInt(daily.temp.min).toString() + "\u00B0C"
                            }

                            response.daily[2].let { daily ->
                                setupWeatherIcon(daily.weather.get(0).icon, binding.dayTwo.imvWeatherView)
                                date.time = daily.dt.toLong() * 1000
                                binding.dayTwo.txtCityTempDate.text = formatter.format(date)
                                binding.dayTwo.txtCityTempLowMax.text = convertStringtoInt(daily.temp.max).toString()+ "\u00B0C" + " / " + convertStringtoInt(daily.temp.min).toString() + "\u00B0C"
                            }

                            response.daily[3].let { daily ->
                                setupWeatherIcon(daily.weather.get(0).icon, binding.dayThree.imvWeatherView)
                                date.time = daily.dt.toLong() * 1000
                                binding.dayThree.txtCityTempDate.text = formatter.format(date)
                                binding.dayThree.txtCityTempLowMax.text = convertStringtoInt(daily.temp.max).toString()+ "\u00B0C" + " / " + convertStringtoInt(daily.temp.min).toString() + "\u00B0C"
                            }
                        }


                    }
                    Status.ERROR -> {
                        viewModel.hideProgressDialog()
                        resource.message!!.logLargeString("message")
                    }
                    Status.LOADING -> {
                        viewModel.showProgressDialog()
                    }
                }
            }
        }
    }
    // set up weather Icon accoring to response in weather. Using "icon".
    private fun setupWeatherIcon(weatherCondition: String, imageView: ImageView)
    {
        when(weatherCondition)
        {
            "01d" -> {imageView.setImageResource(R.drawable.d01)
                       binding.aBaseLayout.setBackgroundResource(R.drawable.gradient_bg_clear)}
            "02d" -> {imageView.setImageResource(R.drawable.d02)
                binding.aBaseLayout.setBackgroundResource(R.drawable.gradient_bg_cloud)}
            "03d" -> {
                imageView.setImageResource(R.drawable.d03)
                binding.aBaseLayout.setBackgroundResource(R.drawable.gradient_bg_cloud)
            }
            "04d" -> {
                imageView.setImageResource(R.drawable.d04)
                binding.aBaseLayout.setBackgroundResource(R.drawable.gradient_bg_cloud)
            }
            "09d" -> {
                imageView.setImageResource(R.drawable.d09)
                binding.aBaseLayout.setBackgroundResource(R.drawable.gradient_bg_rain)
            }
            "10d" -> {imageView.setImageResource(R.drawable.d10)
                binding.aBaseLayout.setBackgroundResource(R.drawable.gradient_bg_rain)
            }
            "11d" -> {imageView.setImageResource(R.drawable.d11)
            binding.aBaseLayout.setBackgroundResource(R.drawable.gradient_bg_rain)
        }
            "50d" -> {imageView.setImageResource(R.drawable.d50)
        binding.aBaseLayout.setBackgroundResource(R.drawable.gradient_bg_rain)
    }
        }
    }

    private fun convertStringtoInt(inputString: String): Int
    {
        var temp: Float = inputString.toFloat()
        return temp.roundToInt()
    }
}