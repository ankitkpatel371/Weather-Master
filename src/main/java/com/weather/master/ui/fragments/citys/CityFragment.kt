package com.weather.master.ui.fragments.citys

import android.view.LayoutInflater
import android.view.View
import com.weather.master.data.model.CityModel
import com.weather.master.databinding.FCityBinding
import com.weather.master.extentions.logLargeString
import com.weather.master.extentions.obtainViewModel
import com.weather.master.ui.base.BaseFragment
import com.weather.master.utils.Status
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


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

                            binding.txtCityTemp.text = response.current.temp + "\u00B0" + "C"
                            response.current.weather.let {
                                binding.txtCityTempStatus.text = it[0].description
                            }


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

                                date.time = daily.dt.toLong() * 1000
                                if (date.time < tomorrow.time.time && date.time > Calendar.getInstance().time.time) {
                                    binding.dayOne.txtCityTempDate.text = "Tomorrow"
                                } else {
                                    binding.dayOne.txtCityTempDate.text = formatter.format(date)
                                }
                                binding.dayOne.txtCityTempLowMax.text = daily.temp.max + "\u00B0C" + " / " + daily.temp.min + "\u00B0C"
                            }

                            response.daily[2].let { daily ->

                                date.time = daily.dt.toLong() * 1000
                                binding.dayTwo.txtCityTempDate.text = formatter.format(date)
                                binding.dayTwo.txtCityTempLowMax.text = daily.temp.max + "\u00B0C" + " / " + daily.temp.min + "\u00B0C"
                            }

                            response.daily[3].let { daily ->
                                date.time = daily.dt.toLong() * 1000
                                binding.dayThree.txtCityTempDate.text = formatter.format(date)
                                binding.dayThree.txtCityTempLowMax.text = daily.temp.max + "\u00B0C" + " / " + daily.temp.min + "\u00B0C"
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
}