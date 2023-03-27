package tmaisuradze.example.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R

class WeatherForecastFragment: Fragment() {
    lateinit var rv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.weather_forecast_fragment, container, false)
        rv = view.findViewById(R.id.forecastRecycler)
        rv.adapter = HourlyListAdapter(emptyList())
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(view.context, R.drawable.devider)?.let { decoration.setDrawable(it) }
        rv.addItemDecoration(decoration)
        return view
    }

    fun update(data: WeatherForecastResponse){
        val items = mutableListOf<WeatherForecastViewItem>()
        for (item in data.list){
            val date = item.dt_txt
            val icon = item.weather[0].icon
            val temperature = item.main.temp.toInt().toString() + "°"
            val description = item.weather[0].description
            val item = WeatherForecastViewItem(date, icon, temperature, description)
            items.add(item)
        }
        (rv.adapter as HourlyListAdapter).updateData(items)
    }

}