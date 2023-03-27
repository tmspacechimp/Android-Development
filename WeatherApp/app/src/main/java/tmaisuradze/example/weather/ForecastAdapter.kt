package tmaisuradze.example.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather.R

class HourlyListAdapter(private var items: List<WeatherForecastViewItem>): RecyclerView.Adapter<ForecastItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastItemViewHolder {
        val forecastItem = LayoutInflater.from(parent.context).inflate(R.layout.forecast_item, parent, false)
        return ForecastItemViewHolder(forecastItem)
    }

    override fun onBindViewHolder(viewHolder: ForecastItemViewHolder, position: Int) {
        viewHolder.date.text = items[position].date
        val icon = items[position].icon
        val url = "https://openweathermap.org/img/wn/$icon@2x.png"
        Glide.with(viewHolder.itemView).load(url).into(viewHolder.icon)
        viewHolder.temperature.text = items[position].temperature
        viewHolder.description.text = items[position].description
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(data: List<WeatherForecastViewItem>){
        items = data
        notifyDataSetChanged()
    }
}

