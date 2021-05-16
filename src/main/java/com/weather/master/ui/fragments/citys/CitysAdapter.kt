package com.weather.master.ui.fragments.citys

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.weather.master.data.model.CityModel


class CitysAdapter(manager: FragmentManager, private val cityList: ArrayList<CityModel>) :
    FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getCount(): Int {
        return cityList.size
    }

    override fun getItem(position: Int): Fragment {
        return CityFragment(cityList[position])
    }
}