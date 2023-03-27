package com.tmaisuradze.alarmapp

interface AlarmsListener {
    fun onAlarmClicked(alarm: Alarm)
    fun onAlarmToggled(alarm: Alarm, toggled: Boolean)
}