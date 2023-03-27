package tmaisuradze.example.weather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapAPI {

    @GET("weather")
    fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Call<CurrentWeatherResponse>

    @GET("forecast")
    fun getWeatherForecast(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Call<WeatherForecastResponse>


}