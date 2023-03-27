package tmaisuradze.example.weather

import android.view.VelocityTracker
import java.util.*

data class CurrentWeatherResponse(
    val name: String,
    val weather: List<WeatherDescription>,
    val main: MainWeatherInfo,
    val timezone: Int
    )

data class WeatherDescription(
    val description: String,
    val icon: String
    )

data class MainWeatherInfo(
    val temp: Double,
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int
    )

data class WeatherForecastResponse(
    val city: City,
    val list: List<WeatherForecastItemInfo>,
)

data class City(
    val name: String
)

data class WeatherForecastItemInfo(
    val dt_txt: String,
    val weather: List<WeatherDescription>,
    val main: MainWeatherInfo
)

data class WeatherForecastViewItem(
    val date: String,
    val icon: String,
    val temperature: String,
    val description: String
)


