package com.tmaisuradze.alarmapp

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity(), IMainView,
    AlarmsListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var rv: RecyclerView
    private lateinit var presenter: MainPresenter
    private var adapter = AlarmAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this)
        presenter.getMode()
        rv = findViewById(R.id.alarms_rv)
        rv.adapter = adapter

        val modeSwitcher = findViewById<TextView>(R.id.theme_switch)
        if (AppCompatDelegate.getDefaultNightMode() == MODE_NIGHT_YES) {
            modeSwitcher.text = getString(R.string.switch_to_light)
        } else {
            modeSwitcher.text = getString(R.string.switch_to_dark)
        }
        presenter.getAlarms(true)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun onAlarmClicked(alarm: Alarm) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Are you sure you want to delete item?")
            .setPositiveButton("Yes"
            ) { dialog, _ ->
                presenter.removeAlarm(alarm)
                presenter.deactivate(alarm, this)
                presenter.saveAlarms()
                dialog?.dismiss()
            }
            .setNegativeButton("No") { dialog, _ -> dialog?.dismiss() }
            .create()
        dialog.show()
    }

    override fun onAlarmToggled(alarm: Alarm, toggled: Boolean) {
        presenter.toggleAlarm(alarm, toggled, this)
    }

    override fun showAlarms(alarms: List<Alarm>) {
        adapter.alarms = alarms
        adapter.notifyDataSetChanged()
    }

    override fun setMode(mode: Int) {
        if (mode == 0) AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        else AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
        presenter.addAlarm(hour, minute, this)
        presenter.saveAlarms()
    }

    fun addAlarm(view: View) {
        val calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            this,
            this,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()
    }

    fun switchTheme(view: View) {
        if (AppCompatDelegate.getDefaultNightMode() == MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            presenter.saveMode(0)
        } else {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            presenter.saveMode(1)
        }
    }
}