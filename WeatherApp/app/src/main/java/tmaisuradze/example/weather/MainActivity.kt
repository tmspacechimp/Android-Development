package tmaisuradze.example.weather

import android.app.PendingIntent.getActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.weather.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    var city = "Tbilisi"
    lateinit var viewPager: ViewPager2
    var fragments = arrayListOf(CurrentWeatherFragment(), WeatherForecastFragment())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createFragments();
    }

    private fun makeCurrentWeatherCall(api: OpenWeatherMapAPI?){
        val call = api?.getCurrentWeather(city,
            getString(R.string.appid), getString(R.string.units))

        call?.enqueue(object: Callback<CurrentWeatherResponse>{
            override fun onResponse(
                call: Call<CurrentWeatherResponse>,
                response: Response<CurrentWeatherResponse>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        setBackgroundColor(it)
                        (viewPager.adapter as ViewPagerAdapter).updateCurrent(it)}
                } else {
                    Toast.makeText(applicationContext, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {
                Log.d("fail", "fail")
            }

        })
    }

    private fun makeWeatherForecastCall(api: OpenWeatherMapAPI?){
        val call = api?.getWeatherForecast(city,
            getString(R.string.appid), getString(R.string.units))

        call?.enqueue(object: Callback<WeatherForecastResponse>{
            override fun onResponse(
                call: Call<WeatherForecastResponse>,
                response: Response<WeatherForecastResponse>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        (viewPager.adapter as ViewPagerAdapter).updateForecast(it)
                    }
                } else {
                    Toast.makeText(applicationContext, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<WeatherForecastResponse>, t: Throwable) {
                Log.d("fail", "fail")
            }

        })
    }

    private fun createFragments() {
        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = ViewPagerAdapter(this, fragments)

        val navBar = findViewById<BottomNavigationView>(R.id.navBar)
        navBar.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.current_icon -> viewPager.currentItem = 0
                R.id.forecast_icon -> viewPager.currentItem = 1
            }
            true
        }

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position){
                    0 -> navBar.selectedItemId = R.id.current_icon
                    1 -> navBar.selectedItemId = R.id.forecast_icon
                }
                updateWeather()
            }
        })
    }

    private fun updateWeather() {
        val retrofit = Retrofit.Builder().baseUrl(getString(R.string.owm_base_url))
            .addConverterFactory(GsonConverterFactory.create()).build()
        val weatherMapApi = retrofit.create(OpenWeatherMapAPI:: class.java)
        when(viewPager.currentItem){
            0 -> makeCurrentWeatherCall(weatherMapApi)
            1 -> makeWeatherForecastCall(weatherMapApi)
        }
    }

    private fun setBackgroundColor(forecast: CurrentWeatherResponse) {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val timezone = forecast.timezone.toLong() * 1000
        calendar.timeInMillis += timezone
        val time = calendar.get(Calendar.HOUR_OF_DAY)
        if (time in 7..18){
            window.decorView.setBackgroundColor(ContextCompat
                .getColor(applicationContext,
                    R.color.day_color
                ))
        }else{
            window.decorView.setBackgroundColor(ContextCompat
                .getColor(applicationContext,
                    R.color.night_color
                ))
        }
    }
    

}