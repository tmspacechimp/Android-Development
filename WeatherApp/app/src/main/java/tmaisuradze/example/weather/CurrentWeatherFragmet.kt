package tmaisuradze.example.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.weather.R
import java.util.*

class CurrentWeatherFragment: Fragment() {

    lateinit var icon: ImageView
    lateinit var temperature: TextView
    lateinit var description: TextView
    lateinit var detTemperature: TextView
    lateinit var feelsLike: TextView
    lateinit var humidity: TextView
    lateinit var pressure: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.current_weather_fragment, container, false)
        icon = view.findViewById<ImageView>(R.id.bigWeatherIcon)
        temperature = view.findViewById<TextView>(R.id.temperature)
        description = view.findViewById<TextView>(R.id.description)
        detTemperature = view.findViewById<TextView>(R.id.temperature_det_data)
        feelsLike = view.findViewById<TextView>(R.id.feels_like_det_data)
        humidity = view.findViewById<TextView>(R.id.humidity_det_data)
        pressure = view.findViewById<TextView>(R.id.pressure_det_data)
        return view
    }

    fun update(data: CurrentWeatherResponse){
        val timezone = data.timezone
        val weather = data.weather
        val main = data.main
        val name = data.name
        val description = weather[0].description
        val icon = weather[0].icon
        val temperature = main.temp
        val feelsLike = main.feels_like
        val pressure = main.pressure
        val humidity = main.humidity
        val imageUrl = "https://openweathermap.org/img/wn/$icon@2x.png"
        Glide.with(this).load(imageUrl).into(this.icon)
        val temp = temperature.toString()
        this.temperature.text = "$temp°"
        this.description.text = description.uppercase(Locale.ENGLISH)
        detTemperature.text = "$temp°"
        val feels = feelsLike.toString()
        this.feelsLike.text = "$feels°"
        val hum = humidity.toString()
        this.humidity.text = "$hum%"
        this.pressure.text = pressure.toString()
    }

}



