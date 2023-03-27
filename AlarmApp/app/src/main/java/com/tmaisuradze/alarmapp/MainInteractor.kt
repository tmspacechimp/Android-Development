package com.tmaisuradze.alarmapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainInteractor(private val presenter: IMainPresenter) {
    private lateinit var alarms: SortedSet<Alarm>

    fun getAlarms(view: IMainView?, initialCall: Boolean = false) {
        if (initialCall) {
            val preferences =
                (view as AppCompatActivity)
                    .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val alarmStrings = preferences
                .getStringSet(ALARM_LIST, mutableSetOf<String>())
            val alarmSet = mutableSetOf<Alarm>()
            if (alarmStrings != null) {
                for (alarm in alarmStrings) {
                    alarmSet.add(Alarm(alarm.dropLast(4), alarm.takeLast(4).toBoolean()))
                }
            }
            alarms = alarmSet.toSortedSet()
        }
        presenter.onAlarmsFetched(alarms.toList())
    }

    fun setAlarms(view: IMainView?) {
        val preferences =
            (view as AppCompatActivity)
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val alarmSet = mutableSetOf<String>()
        for (alarm in alarms) {
            if (alarm.toggled) {
                alarmSet.add(alarm.time + "true")
            } else {
                alarmSet.add(alarm.time + "false")
            }
        }
        preferences.edit().putStringSet(ALARM_LIST, alarmSet).apply()
    }

    fun addAlarmToSet(alarm: Alarm){
        alarms.add(alarm)
        presenter.onAlarmsFetched(alarms.toList())
    }

    fun updateAlarm(alarm: Alarm, toggled: Boolean){
        alarms.add(Alarm(alarm.time, toggled))
        removeAlarmFromSet(alarm)
    }

    fun removeAlarmFromSet(alarm: Alarm){
        alarms.remove(alarm)
        presenter.onAlarmsFetched(alarms.toList())
    }

    fun getMode(view: IMainView?){
        val preferences =
            (view as AppCompatActivity)
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val mode = preferences.getInt(APP_MODE, -1)
        presenter.onModeFetched(mode)
    }

    fun setMode(view: IMainView?, mode: Int){
        val preferences =
            (view as AppCompatActivity)
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        preferences.edit().putInt(APP_MODE, mode).apply()
    }


    companion object {
        private const val PREF_NAME = "alarm-pref"
        private const val ALARM_LIST = "alarmList"
        private const val APP_MODE = "appMode"
    }

}