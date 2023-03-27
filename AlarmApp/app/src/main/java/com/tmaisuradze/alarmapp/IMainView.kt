package com.tmaisuradze.alarmapp

interface IMainView {
    fun showAlarms(alarms: List<Alarm>)
    fun setMode(mode: Int)
}