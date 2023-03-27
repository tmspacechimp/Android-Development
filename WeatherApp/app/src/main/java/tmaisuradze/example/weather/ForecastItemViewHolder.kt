package tmaisuradze.example.weather

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R

class ForecastItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var date = itemView.findViewById<TextView>(R.id.forecast_date)
    var icon = itemView.findViewById<ImageView>(R.id.forecast_image)
    var temperature = itemView.findViewById<TextView>(R.id.forecast_temperature)
    var description = itemView.findViewById<TextView>(R.id.forecast_description)
}