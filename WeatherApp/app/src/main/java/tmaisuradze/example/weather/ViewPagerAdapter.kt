package tmaisuradze.example.weather

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity, private val fragments: List<Fragment>) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun updateCurrent(data: CurrentWeatherResponse){
        (fragments[0] as CurrentWeatherFragment).update(data)
    }

    fun updateForecast(data: WeatherForecastResponse){
        (fragments[0] as WeatherForecastFragment).update(data)
    }

}