package com.tmaisuradze.alarmapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView

class AlarmAdapter(private val listener: AlarmsListener) :
    RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {

    var alarms = listOf<Alarm>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val time: TextView = view.findViewById(R.id.alarm_time)
        val switch: SwitchCompat = view.findViewById(R.id.alarm_switch)

        fun bindAlarm(alarm: Alarm) {
            time.text = alarm.time
            switch.setOnCheckedChangeListener(null)
            switch.isChecked = alarm.toggled
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlarmAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.alarm_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AlarmAdapter.ViewHolder, position: Int) {
        val alarm = alarms[position]
        holder.bindAlarm(alarm)
        holder.switch.setOnCheckedChangeListener { _, isChecked ->
            listener.onAlarmToggled(alarm, isChecked)
        }
        holder.itemView.setOnLongClickListener {
            listener.onAlarmClicked(alarm)
            true
        }
    }

    override fun getItemCount(): Int {
        return alarms.size
    }

}